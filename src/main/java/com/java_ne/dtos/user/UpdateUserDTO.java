package com.java_ne.dtos.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserDTO {
    @Schema(example = "John")
    private String firstName;

    @Schema(example = "Doe")
    private String lastName;

    @Schema(example = "johndoe")
    private String username;

    @Schema(example = "example@gmail.com")
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
}

