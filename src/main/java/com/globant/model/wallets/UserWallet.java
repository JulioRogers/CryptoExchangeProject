package com.globant.model.wallets;

import com.globant.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class UserWallet extends Wallet {
    private final List<Transaction> transactions = new ArrayList<>();
    public UserWallet(Integer userId) {
    }

    public void saveTransaction(){}
}