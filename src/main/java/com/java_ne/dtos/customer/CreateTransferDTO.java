package com.java_ne.dtos.customer;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateTransferDTO {
    @NotNull
    @Min(value = 100, message = "Minimum amount for transfer is 100")
    @Max(value = 100000000, message = "The maximum amount to transfer is 100000000")
    @Positive(message = "Amount must be positive")
    private Double amount;

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Account number must be 10 digits")
    private String accountNumber;
}
