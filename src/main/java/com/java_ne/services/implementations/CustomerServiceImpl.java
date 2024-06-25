package com.java_ne.services.implementations;

import com.java_ne.dtos.customer.CreateSavingDTO;
import com.java_ne.dtos.customer.CreateTransferDTO;
import com.java_ne.dtos.customer.CreateWithdrawDTO;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.exceptions.BadRequestException;
import com.java_ne.exceptions.CustomException;
import com.java_ne.exceptions.NotFoundException;
import com.java_ne.models.SavingManagement;
import com.java_ne.models.Transfer;
import com.java_ne.models.User;
import com.java_ne.models.WithdrawManagement;
import com.java_ne.repositories.*;
import com.java_ne.services.interfaces.CustomerService;
import com.java_ne.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ISavingRepository savingRepository;
    private final UserService userService;
    private final IUserRepository userRepository;
    private final IWithdrawRepository withdrawRepository;
    private final ITransferRepository transferRepository;

    @Override
    public ResponseEntity<ApiResponse<SavingManagement>> createSaving(CreateSavingDTO data) {
        try {

            //Save amount
            User user = userService.getLoggedInUser();
            user.setAmount(user.getAmount() + data.getAmount());
            userRepository.save(user);

            //Save a saving
            SavingManagement saving = new SavingManagement();
            saving.setCustomer(user);
            saving.setAmount(data.getAmount());
            saving.setAccountNumber(user.getAccountNumber());

            return ApiResponse.success("Saving create", HttpStatus.CREATED, savingRepository.save(saving));
        }catch (Exception e){
            throw  new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<WithdrawManagement>> createWithdraw(CreateWithdrawDTO data) {
        try {
            User user = userService.getLoggedInUser();
            user.setAmount(user.getAmount() - data.getAmount());
            userRepository.save(user);

            //Save a saving

            if(user.getAmount() < data.getAmount()){
                throw new BadRequestException("Not enough amount to perform this action");
            }

            WithdrawManagement withdraw = new WithdrawManagement();
            withdraw.setCustomer(user);
            withdraw.setAmount(data.getAmount());
            withdraw.setAccountNumber(user.getAccountNumber());

            return ApiResponse.success("Saving create", HttpStatus.CREATED, withdrawRepository.save(withdraw));
        }catch (Exception e){
            throw  new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Transfer>> createTransfer(CreateTransferDTO data) {
        try{
            User user = userService.getLoggedInUser();
            user.setAmount(user.getAmount() - data.getAmount());
            userRepository.save(user);

            //find Customer by accountNumber
            Optional<User> receiver = userRepository.findUserByAccountNumber(data.getAccountNumber());

            if(receiver.isEmpty()){
                throw new NotFoundException("Customer with account number " + data.getAccountNumber() + " not found");
            }

            if(user.getAmount() > receiver.get().getAmount()){
                throw new BadRequestException("Not enough amount to perform this action");
            }

            Transfer transfer = new Transfer();
            transfer.setCustomer(user);
            transfer.setAmount(data.getAmount());
            transfer.setAccountNumber(receiver.get().getAccountNumber());

            return ApiResponse.success("Saving create", HttpStatus.CREATED, transferRepository.save(transfer));
        }catch (Exception e){
            throw  new CustomException(e);
        }
    }
}
