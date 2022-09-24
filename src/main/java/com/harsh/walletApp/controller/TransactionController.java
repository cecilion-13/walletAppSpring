package com.harsh.walletApp.controller;


import com.harsh.walletApp.entity.Transaction;
import com.harsh.walletApp.entity.Wallet;
import com.harsh.walletApp.service.TransactionService;
import com.harsh.walletApp.service.ValidationErrorService;
import com.harsh.walletApp.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ValidationErrorService validationErrorService;

    @GetMapping("/{wallet_id}")
    public ResponseEntity<?> getAll(@PathVariable Long wallet_id){
        return new ResponseEntity<>(transactionService.getAll(wallet_id), HttpStatus.OK);
    }

    @GetMapping("/{wallet_id}/{id}")
    public ResponseEntity<?> getById(@PathVariable Long wallet_id,@PathVariable Long id){
        return new ResponseEntity<>(transactionService.getById(wallet_id, id), HttpStatus.OK);
    }


    @PostMapping("/{wallet_id}")
    public ResponseEntity<?> create(@PathVariable Long wallet_id,@Valid @RequestBody Transaction transaction, BindingResult result){
        //BindingResult result is used for receiving errors
        ResponseEntity errors = validationErrorService.validate(result);
        if(errors != null){
            return errors;
        }
        Transaction transactionSaved = transactionService.createOrUpdate(wallet_id, transaction);
        return new ResponseEntity<Transaction>(transaction, HttpStatus.CREATED);
    }

    @PutMapping("/{wallet_id}/{id}")
    public ResponseEntity<?> update(@PathVariable Long wallet_id,@PathVariable Long id, @Valid @RequestBody Transaction transaction, BindingResult result){
        //BindingResult result is used for receiving errors
        ResponseEntity errors = validationErrorService.validate(result);
        if(errors != null){
            return errors;
        }
        transaction.setId(id);
        Transaction transactionSaved = transactionService.createOrUpdate(wallet_id, transaction);
        return new ResponseEntity<Transaction>(transactionSaved, HttpStatus.OK);
    }

    @DeleteMapping("/{wallet_id}/{id}")
    public ResponseEntity<?> delete(@PathVariable Long wallet_id,@PathVariable Long id){
        transactionService.delete(wallet_id,id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
