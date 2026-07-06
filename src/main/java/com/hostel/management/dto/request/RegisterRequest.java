package com.hostel.management.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50, message = "maximum 3 and max 50")
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, message = "password must be at least 8")
    private String password;

    // Optional: link registration directly to a student profile
    private String fullName;
    private String phone;
}
