package com.globant.service.loggedInServices;

import com.globant.model.Transaction;
import com.globant.model.User;
import com.globant.model.wallets.UserWallet;

import java.util.List;

public class GetTransactions {

    private GetTransactions(){}

    public static String execute(User user) {
        UserWallet userWallet = user.getWallet();
        List<Transaction> transactions = userWallet.getTransactions();
        if(!transactions.isEmpty()) {
            StringBuilder result = new StringBuilder("Transactions:\n");
            int index = 1;
            for (Transaction transaction : transactions) {
                result.append(index).append(". ").append(transaction).append("\n");
                index++;
            }
            return result.toString();
        }else {
            return "No Transactions";
        }    }
}