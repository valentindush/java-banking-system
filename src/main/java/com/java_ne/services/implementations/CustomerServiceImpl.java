package com.java_ne.services.implementations;

import com.java_ne.dtos.customer.CreateSavingDTO;
import com.java_ne.dtos.customer.CreateTransferDTO;
import com.java_ne.dtos.customer.CreateWithdrawDTO;
import com.java_ne.dtos.response.ApiResponse;
import com.java_ne.exceptions.BadRequestException;
import com.java_ne.exceptions.CustomException;
import com.java_ne.exceptions.NotFoundException;
import com.java_ne.models.Saving;
import com.java_ne.models.Transfer;
import com.java_ne.models.User;
import com.java_ne.models.Withdraw;
import com.java_ne.repositories.*;
import com.java_ne.services.interfaces.CustomerService;
import com.java_ne.services.interfaces.EmailService;
import com.java_ne.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final ISavingRepository savingRepository;
    private final UserService userService;
    private final IUserRepository userRepository;
    private final IWithdrawRepository withdrawRepository;
    private final ITransferRepository transferRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<ApiResponse<List<Saving>>> getSavings() {
        try {
            User user = userService.getLoggedInUser();
            List<Saving> savings = user.getSavings();
            return ResponseEntity.ok(ApiResponse.success("OK", HttpStatus.OK, savings).getBody());
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Withdraw>>> getWithdraws() {
        try {
            User user = userService.getLoggedInUser();
            List<Withdraw> withdrawals = user.getWithdrawals();
            return ResponseEntity.ok(ApiResponse.success("OK", HttpStatus.OK, withdrawals).getBody());
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<List<Transfer>>> getTransfers() {
        try {
            User user = userService.getLoggedInUser();
            List<Transfer> transfers = user.getTransfers();
            return ResponseEntity.ok(ApiResponse.success("OK", HttpStatus.OK, transfers).getBody());
        } catch (Exception e) {
            throw new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Saving>> createSaving(CreateSavingDTO data) {
        try {

            //Save amount
            User user = userService.getLoggedInUser();
            user.setAmount(user.getAmount() + data.getAmount());
            userRepository.save(user);

            //Save a saving
            Saving saving = new Saving();
            saving.setCustomer(user);
            saving.setAmount(data.getAmount());
            saving.setAccountNumber(user.getAccountNumber());

            Context context = new Context();
            context.setVariable("title", "BNR - Savings");
            context.setVariable("name", user.getFullName());
            context.setVariable("message",
                    "Dear " + user.getFullName() + ", your saving of "+data.getAmount().toString() +
                            " on your account "+user.getAccountNumber() + " has been completed successfully");
            emailService.sendTemplatedHtmlEmail(user.getEmail(),
                    "BNR - Savings", "success-email", context);

            return ApiResponse.success("Saving created", HttpStatus.CREATED, savingRepository.save(saving));
        }catch (Exception e){
            throw  new CustomException(e);
        }
    }

    @Override
    public ResponseEntity<ApiResponse<Withdraw>> createWithdraw(CreateWithdrawDTO data) {
        try {
            User user = userService.getLoggedInUser();
            user.setAmount(user.getAmount() - data.getAmount());
            userRepository.save(user);

            //Save a saving

            if(user.getAmount() < data.getAmount()){
                throw new BadRequestException("Not enough amount to perform this action");
            }

            Withdraw withdraw = new Withdraw();
            withdraw.setCustomer(user);
            withdraw.setAmount(data.getAmount());
            withdraw.setAccountNumber(user.getAccountNumber());

            Context context = new Context();
            context.setVariable("title", "BNR - Savings");
            context.setVariable("name", user.getFullName());
            context.setVariable("message",
                    "Dear " + user.getFullName() + ", your withdraw of "+data.getAmount().toString() +
                            " on your account "+user.getAccountNumber() + " has been completed successfully");
            emailService.sendTemplatedHtmlEmail(user.getEmail(),
                    "BNR - Withdraw", "success-email", context);

            return ApiResponse.success("withdraw created", HttpStatus.CREATED, withdrawRepository.save(withdraw));
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

            Context context = new Context();
            context.setVariable("title", "BNR - Transfer");
            context.setVariable("name", user.getFullName());
            context.setVariable("message",
                    "Dear " + user.getFullName() + ", your transfer of "+data.getAmount().toString() +
                            " to  account "+receiver.get().getAccountNumber() + " has been completed successfully");
            emailService.sendTemplatedHtmlEmail(user.getEmail(),
                    "BNR - Transfer", "success-email", context);

            context.setVariable("title", "BNR - Savings");
            context.setVariable("name", receiver.get().getFullName());
            context.setVariable("message",
                    "Dear " + receiver.get().getFullName() + ", your have received amount of "+data.getAmount().toString() +
                            " on your account from account"+user.getAccountNumber());
            emailService.sendTemplatedHtmlEmail(user.getEmail(),
                    "BNR - Transfer", "success-email", context);

            return ApiResponse.success("transfer created", HttpStatus.CREATED, transferRepository.save(transfer));
        }catch (Exception e){
            throw  new CustomException(e);
        }
    }
}
