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
public class CreateWithdrawDTO {
    @NotNull
    @Min(value = 100, message = "Minimum amount to be saved is 100")
    @Max(value = 100000000, message = "The maximum amount to withdraw is 100000000")
    @Positive(message = "Amount must be positive")
    private Double amount;
}
