package com.doantotnghiep.DoAnTotNghiep.service.auth;

import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.entity.VerificationCode;
import com.doantotnghiep.DoAnTotNghiep.exception.AppException;
import com.doantotnghiep.DoAnTotNghiep.exception.ErrorCode;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.VerificationCodeRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.VerificationCodeRepository;
import com.doantotnghiep.DoAnTotNghiep.service.mail.ISendEmailService;
import com.doantotnghiep.DoAnTotNghiep.service.users.UserDetailsImpl;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import com.doantotnghiep.DoAnTotNghiep.utils.ServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;
    private final ISendEmailService sendEmailService;

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
                .isActive(false)
                .build();

        // Lưu vào database nhưng chưa kích hoạt
        user = userRepository.save(user);

        // Gửi mã OTP
        sendOTP(user);

        return user;
    }

    private void sendOTP(User user) {
        String otp = ServiceUtils.generateRandomNumber(6);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(5);

        VerificationCode verificationCode = verificationCodeRepository.findByUser(user)
                .orElse(new VerificationCode());

        verificationCode.setUser(user);
        verificationCode.setVerificationCode(otp);
        verificationCode.setExpiresAt(expiresAt);
        verificationCode.setVerified(false);

        verificationCodeRepository.save(verificationCode);

        try {
            sendEmailService.sendEmailWithOTP(user.getEmail(), otp);
        } catch (Exception e) {
            throw new AppException(ErrorCode.DURING_REGISTRATION_ERROR);
        }
    }

    @Override
    public String verifyOTP(VerificationCodeRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new AppException(ErrorCode.INVALID_USERNAME_OR_PASSWORD);
        }

        User user = userOptional.get();
        Optional<VerificationCode> codeOptional = verificationCodeRepository.findByUser(user);

        if (codeOptional.isEmpty() || !codeOptional.get().getVerificationCode().equals(request.getVerificationCode())
                || codeOptional.get().getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.INVALID_OTP);
        }

        VerificationCode verificationCode = codeOptional.get();
        verificationCode.setVerified(true);
        verificationCodeRepository.save(verificationCode);

        // Kích hoạt tài khoản
        user.setActive(true);
        userRepository.save(user);

        return "OTP verified successfully! Account activated.";
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("email", userDetails.getEmail());
        additionalClaims.put("firstName", userDetails.getFirstName());
        additionalClaims.put("lastName", userDetails.getLastName());

        String jwt = jwtUtils.generateToken(additionalClaims, userDetails.getEmail());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles);
    }
}
