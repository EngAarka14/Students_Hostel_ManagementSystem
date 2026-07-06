package com.hostel.management.config;

import com.hostel.management.entity.User;
import com.hostel.management.entity.enums.Role;
import com.hostel.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Seeds a default administrator account on first startup so the
 * application is usable immediately after setup.
 *
 * Default credentials: admin / Admin@123  (CHANGE AFTER FIRST LOGIN)
 */
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@hostel.local");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ROLE_ADMIN);
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println(">>> Default admin created: admin / Admin@123");
        }
    }
}
