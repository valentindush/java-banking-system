package com.java_ne.controllers;

import com.java_ne.dtos.customer.CreateSavingDTO;
import com.java_ne.dtos.customer.CreateTransferDTO;
import com.java_ne.dtos.customer.CreateWithdrawDTO;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.models.Saving;
import com.java_ne.models.Transfer;
import com.java_ne.models.Withdraw;
import com.java_ne.services.interfaces.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.With;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/customer")
@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('USER')")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/savings")
    public ResponseEntity<ApiResponse<List<Saving>>> getSavings(){
        return customerService.getSavings();
    }

    @GetMapping("/withdrawals")
    public ResponseEntity<ApiResponse<List<Withdraw>>> getWithdrawals(){
        return customerService.getWithdraws();
    }

    @GetMapping("/transfers")
    public ResponseEntity<ApiResponse<List<Transfer>>> getTransfers(){
        return customerService.getTransfers();
    }

    @PostMapping("/saving")
    public ResponseEntity<ApiResponse<Saving>> createSaving(@Valid @RequestBody CreateSavingDTO savingDTO) {
        System.out.println("about to create a saving");
        return customerService.createSaving(savingDTO);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<ApiResponse<Withdraw>> createWithdraw(@Valid @RequestBody CreateWithdrawDTO withdrawDTO) {
        System.out.println("about to create a withdraw");
        return customerService.createWithdraw(withdrawDTO);
    }

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<Transfer>> createWithdraw(@Valid @RequestBody CreateTransferDTO transferDTO) {
        System.out.println("about to create a transfer");
        return customerService.createTransfer(transferDTO);
    }
}
