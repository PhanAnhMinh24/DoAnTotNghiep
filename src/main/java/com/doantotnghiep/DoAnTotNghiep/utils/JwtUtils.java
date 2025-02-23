package com.doantotnghiep.DoAnTotNghiep.utils;

import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    // Tạo token có chứa thêm thông tin (claims)
    public String generateToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims) // Thêm thông tin vào JWT
                .setSubject(username) // Đặt username làm subject
                .setIssuedAt(new Date()) // Thời gian phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs)) // Thời gian hết hạn
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Ký bằng thuật toán HS256
                .compact();
    }

    // Tạo token đơn giản chỉ từ username
    public String generateToken(String username) {
        return generateToken(Map.of(), username);
    }

    // Trích xuất tất cả thông tin từ JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Lấy username từ JWT
    public String getUserNameFromJwtToken(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Xác minh JWT token có hợp lệ hay không
    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            System.out.println("JWT validation error: " + e.getMessage());
        }
        return false;
    }

    // Tạo key bí mật cho JWT
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public static String getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (ObjectUtils.isEmpty(authentication)) {
            throw new AppException(ErrorCode.ERROR_JWT_IS_NOT_VALID);
        }
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new AppException(ErrorCode.ERROR_ANONYMOUS_AUTHENTICATION_TOKEN);
        }
        Object principal = authentication.getPrincipal();
        return principal.toString();
    }
}
