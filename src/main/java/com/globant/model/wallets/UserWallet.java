package com.globant.model.wallets;

import com.globant.model.Transaction;
import com.globant.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserWallet extends Wallet {
    private final List<Transaction> transactions = new ArrayList<>();
    private final Integer userId;
    public UserWallet(Integer userId) {
        this.userId = userId;
    }

    public void saveTransaction(){}

    public Integer getUserId() {
        return userId;
    }
}