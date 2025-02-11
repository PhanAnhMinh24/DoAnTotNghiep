package com.doantotnghiep.DoAnTotNghiep.service.auth;
import com.doantotnghiep.DoAnTotNghiep.entity.Role;
import com.doantotnghiep.DoAnTotNghiep.entity.User;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.LoginRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.request.SignupRequest;
import com.doantotnghiep.DoAnTotNghiep.pojo.response.JwtResponse;
import com.doantotnghiep.DoAnTotNghiep.repository.RoleRepository;
import com.doantotnghiep.DoAnTotNghiep.repository.UserRepository;
import com.doantotnghiep.DoAnTotNghiep.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AuthService implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, UserRepository userRepository,
                       RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // Đăng ký người dùng
    @Override
    public User registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUserName(signupRequest.getUserName())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        if (userRepository.existsByUserName(signupRequest.getEmail())) {
            throw new RuntimeException("Tên đăng nhập đã tồn tại!");
        }

        Optional<Role> userRole = roleRepository.findByName("USER");
        if (userRole.isEmpty()) {
            throw new RuntimeException("Vai trò mặc định không tồn tại!");
        }

        User newUser = new User();
        newUser.setUserName(signupRequest.getUserName());
        newUser.setFirstName(signupRequest.getFirstName());
        newUser.setLastName(signupRequest.getLastName());
        newUser.setEmail(signupRequest.getEmail());
        newUser.setPhoneNumber(signupRequest.getPhoneNumber());
        newUser.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        newUser.setRole(userRole.get());

        return userRepository.save(newUser);
    }

    // Xử lý đăng nhập
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // ✅ Lấy username từ authentication
        String username = authentication.getName();
        String jwt = jwtUtils.generateToken(username);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
                userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority) // Chuyển đổi từ GrantedAuthority -> String
                        .toList());

    }
}
