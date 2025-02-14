package com.doantotnghiep.DoAnTotNghiep.service.auth;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
//import com.doantotnghiep.DoAnTotNghiep.service.role.IRoleService;
import com.doantotnghiep.DoAnTotNghiep.service.users.UserDetailsImpl;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
//    private final IRoleService roleService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXIST);
        }

        User user = User.builder()
                .firstName(signupRequest.getFirstName())
                .lastName(signupRequest.getLastName())
                .email(signupRequest.getEmail())
                .phoneNumber(signupRequest.getPhoneNumber())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .isActive(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Thêm thông tin vào claims
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("email", userDetails.getEmail());
        additionalClaims.put("firstName", userDetails.getFirstName());
        additionalClaims.put("lastName", userDetails.getLastName());

        // Tạo token với claims
        String jwt = jwtUtils.generateToken(additionalClaims, userDetails.getEmail());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles);
    }


}
