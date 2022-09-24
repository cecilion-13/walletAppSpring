package com.harsh.walletApp.service;

import com.harsh.walletApp.entity.Transaction;
import com.harsh.walletApp.entity.Wallet;
import com.harsh.walletApp.exception.WalletException;
import com.harsh.walletApp.repo.TransactionRepository;
import com.harsh.walletApp.repo.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private WalletRepository walletRepository;

    public List<Transaction> getAll(Long walletId){
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()){
            return transactionRepository.findByWallet(wallet.get());
        }
        return null;
    }
    public Transaction getById(Long wallet_id, Long id){
        Optional<Wallet> wallet = walletRepository.findById(wallet_id);
        if(wallet.isPresent()) {
            Optional<Transaction> transaction = transactionRepository.findById(id);
            if (transaction.isPresent()) {
                return transaction.get();
            }
        }
        throw new WalletException("Transaction with id: " + id + " doesn't exists!");
    }
    public Transaction createOrUpdate(Long walletId, Transaction transaction){
        Optional<Wallet> wallet = walletRepository.findById(walletId);
        if(wallet.isPresent()){
            Double currentBalance = wallet.get().getCurrentBalance();
            if(transaction.getType() == 1){
                currentBalance += transaction.getAmount();
            }
            if(transaction.getType() == 2){
                if(currentBalance >= transaction.getAmount()){
                    currentBalance = currentBalance-transaction.getAmount();
                }
                else{
                    throw new WalletException("Amount is not sufficient!");
                }
            }
            wallet.get().setCurrentBalance(currentBalance);
            transaction.setWallet(wallet.get());
            transactionRepository.save(transaction);
            return transaction;
        }
        return null;
    }

    public boolean delete(Long wallet_id,Long id){
        Optional<Wallet> wallet = walletRepository.findById(wallet_id);
        if(wallet.isPresent()) {
            Optional<Transaction> transaction = transactionRepository.findById(id);
            if (transaction.isPresent()) {
                transactionRepository.delete(transaction.get());
                return true;
            }
        }
        throw new WalletException("Transaction with id: " + id + " doesn't exists!");
    }
}
