package com.hostel.management.service;

import com.hostel.management.dto.request.LoginRequest;
import com.hostel.management.dto.request.RegisterRequest;
import com.hostel.management.dto.response.JwtResponse;
import com.hostel.management.entity.User;
import com.hostel.management.entity.enums.Role;
import com.hostel.management.exception.BadRequestException;
import com.hostel.management.repository.UserRepository;
import com.hostel.management.security.JwtUtil;
import com.hostel.management.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public JwtResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email is already in use");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setEnabled(true);
        userRepository.save(user);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                UserPrincipal.create(user), null, UserPrincipal.create(user).getAuthorities());
        String token = jwtUtil.generateToken(authentication);

        return new JwtResponse(token, user.getId(), user.getUsername(), user.getEmail(), user.getRole().name());
    }

    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtUtil.generateToken(authentication);

        return new JwtResponse(token, principal.getId(), principal.getUsername(), principal.getEmail(),
                principal.getAuthorities().iterator().next().getAuthority());
    }
}
