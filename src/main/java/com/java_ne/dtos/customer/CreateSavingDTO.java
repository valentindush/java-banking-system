package com.java_ne.dtos.customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateSavingDTO {
    @NotBlank(message = "amount is required")
    @Min(value = 1, message = "Minimum amount to be saved is 1")
    @Positive(message = "Amount must be positive")
    private Double amount;
}
