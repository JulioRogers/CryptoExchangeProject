package com.globant.service;

import com.globant.model.Transaction;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.UserWallet;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class GetTransactions {

    private GetTransactions(){}

    public static String execute(User user) {
        UserWallet userWallet = user.getWallet();
        List<Transaction> transactions = userWallet.getTransactions();
        if(!transactions.isEmpty()) {
            StringBuilder result = new StringBuilder("Transactions:\n");
            int index = 1;
            for (Transaction transaction : transactions) {
                result.append(index).append(". ")
                      .append("CryptoCurrency: ").append(transaction.getCryptoCurrency().getName())
                      .append(", Amount: ").append(transaction.getAmount())
                      .append(", Price: ").append(transaction.getPrice())
                      .append(", Order Type: ").append(transaction.getOrderType())
                      .append("\n");
                index++;
            }
            return result.toString();
        }else {
            return "No Transactions";
        }    }
}