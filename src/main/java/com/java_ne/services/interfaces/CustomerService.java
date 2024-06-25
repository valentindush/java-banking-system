package com.java_ne.services.interfaces;

import com.java_ne.dtos.customer.CreateSavingDTO;
import com.java_ne.dtos.customer.CreateTransferDTO;
import com.java_ne.dtos.customer.CreateWithdrawDTO;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.models.Saving;
import com.java_ne.models.Transfer;
import com.java_ne.models.Withdraw;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<ApiResponse<List<Saving>>> getSavings();
    ResponseEntity<ApiResponse<List<Withdraw>>> getWithdraws();
    ResponseEntity<ApiResponse<List<Transfer>>> getTransfers();
    public ResponseEntity<ApiResponse<Saving>> createSaving(CreateSavingDTO saving);
    public ResponseEntity<ApiResponse<Withdraw>> createWithdraw(CreateWithdrawDTO withdraw);
    public ResponseEntity<ApiResponse<Transfer>> createTransfer(CreateTransferDTO transfer);
}
