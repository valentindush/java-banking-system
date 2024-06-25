package com.java_ne.dtos.auth;

import com.mysql.cj.util.DnsSrv;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterUserDTO {
    @Schema(example = "John")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;

    @Schema(example = "Doe")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;

    @Schema(example = "johndoe")
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @Schema(example = "example@gmail.com")
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(example = "+250-788-401-749")
    @Pattern(regexp = "^\\+?[\\d\\s\\-()]{7,}$|^(\\(\\d{3}\\)\\s?\\d{3}-?\\d{4})$", message = "Invalid mobile number")
    private String mobile;

    @NotNull(message = "DoB is required")
    @Past(message = "DoB must be in the past")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    private LocalDate dob;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Account number is ten digits")
    private String accountNumber;

    @Schema(example = "password@123")
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
}
