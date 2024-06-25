package com.java_ne.controllers;

import com.java_ne.dtos.customer.CreateSavingDTO;
import com.java_ne.dtos.customer.CreateTransferDTO;
import com.java_ne.dtos.customer.CreateWithdrawDTO;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.models.SavingManagement;
import com.java_ne.models.Transfer;
import com.java_ne.models.WithdrawManagement;
import com.java_ne.services.interfaces.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole()")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/saving/")
    public ResponseEntity<ApiResponse<SavingManagement>> createSaving(@RequestBody CreateSavingDTO savingDTO) {
        System.out.println("about to create a saving");
        return customerService.createSaving(savingDTO);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<WithdrawManagement>> createWithdraw(@RequestBody CreateWithdrawDTO withdrawDTO) {
        System.out.println("about to create a withdraw");
        return customerService.createWithdraw(withdrawDTO);
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Transfer>> createWithdraw(@RequestBody CreateTransferDTO transferDTO) {
        System.out.println("about to create a transfer");
        return customerService.createTransfer(transferDTO);
    }
}
