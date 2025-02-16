package com.doantotnghiep.DoAnTotNghiep.config;

import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.service.users.UserDetailsServiceImpl;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.CommonConstants;
import com.doantotnghiep.DoAnTotNghiep.utils.constants.EndpointConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@Component
public class AuthTokenFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
//            if (!shouldSkipFilter(request)) { // Đổi tên cho dễ hiểu
                String jwt = parseJwt(request);
                if (StringUtils.isBlank(jwt)) {
                    setErrorMessage(response, new AppException(ErrorCode.UNAUTHORIZED));
                    return;
                }
                if (jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
//            }
            filterChain.doFilter(request, response);
        } catch (AppException e) {
            log.error("Cannot set user authentication: {}", e.getMessage());
            setErrorMessage(response, e);
        } catch (Exception ex) {  // Bắt lỗi chung
            log.error("Unexpected error in authentication filter: {}", ex.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    private void setErrorMessage(HttpServletResponse response, AppException e) throws IOException {
        response.setStatus(e.getErrorCode().getStatus().value());
        response.setContentType(CommonConstants.APPLICATION_JSON_UTF8);
        response.getWriter().write(objectMapper.writeValueAsString(
                Map.of(CommonConstants.MESSAGE, e.getErrorCode().getMessage(),
                        CommonConstants.STATUS, e.getErrorCode().getStatus().value())));
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.isNotBlank(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        String method = request.getMethod();

        String signUp = EndpointConstants.AUTH + EndpointConstants.SIGN_UP;
        String signIn = EndpointConstants.AUTH + EndpointConstants.SIGN_IN;
        String email = "/api/email/send-random-code";
        String actuator = EndpointConstants.ACTUATOR;
        String favicon = EndpointConstants.SWAGGER_ICO;
        return signIn.equals(path) || signUp.equals(path) || path.startsWith(actuator) || path.startsWith(favicon) ||
                email.equals(path)||
                (path.startsWith(EndpointConstants.SWAGGER_UI) && HttpMethod.GET.matches(method)) ||
                (path.startsWith(EndpointConstants.SWAGGER_API_DOCS) && HttpMethod.GET.matches(method)) ||
                (path.startsWith(EndpointConstants.SWAGGER_API_DOCS + EndpointConstants.SWAGGER_CONFIG) && HttpMethod.GET.matches(method));
    }
}
