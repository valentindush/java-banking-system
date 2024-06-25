package com.java_ne.dtos.user;

import com.java_ne.dtos.auth.RegisterUserDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class CreateUserDTO extends RegisterUserDTO {
    private List<UUID> roles;

    public CreateUserDTO(RegisterUserDTO registerUserDTO) {
        this.setEmail(registerUserDTO.getEmail());
        this.setPassword(registerUserDTO.getPassword());
        this.setUsername(registerUserDTO.getUsername());
        this.setFirstName(registerUserDTO.getFirstName());
        this.setLastName(registerUserDTO.getLastName());
        this.setDob(registerUserDTO.getDob());
        this.setMobile(registerUserDTO.getMobile());
        this.setAccountNumber(registerUserDTO.getAccountNumber());

    }

    public CreateUserDTO(String email, String username, String password, String firstName, String lastName, LocalDate dob, String mobile, String accountNumber) {
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setDob(dob);
        this.setMobile(mobile);
        this.setAccountNumber(accountNumber);

    }
}
