package com.java_ne.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterUserDTO {
    @Schema(example = "John")
    @NotBlank(message = "First name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s-']{2,50}$", message = "First name must be 2-50 characters and can only contain letters, spaces, hyphens, and apostrophes")
    private String firstName;

    @Schema(example = "Doe")
    @NotBlank(message = "Last name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z\\s-']{2,50}$", message = "Last name must be 2-50 characters and can only contain letters, spaces, hyphens, and apostrophes")
    private String lastName;

    @Schema(example = "johndoe")
    @NotBlank(message = "Username cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,20}$", message = "Username must be 3-20 characters and can only contain letters, numbers, underscores, and hyphens")
    private String username;

    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "+250788401749")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid mobile number. It should be 10-15 digits, optionally starting with +")
    private String mobile;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    @AssertTrue(message = "Must be at least 18 years old")
    private boolean isAtLeast18() {
        return dob != null && LocalDate.now().minusYears(18).isAfter(dob);
    }

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be exactly 10 digits")
    private String accountNumber;

    @Schema(example = "password@123")
    @NotBlank(message = "Password cannot be blank")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Password must be at least 8 characters long and include at least one digit, one lowercase letter, one uppercase letter, and one special character")
    private String password;
}