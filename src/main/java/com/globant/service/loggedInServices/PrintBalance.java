package com.globant.service.loggedInServices;

import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.UserWallet;

import java.math.BigDecimal;
import java.util.Map;

public class PrintBalance {
    public static String execute(User user) {
        UserWallet userWallet = user.getWallet();
        Map<Currency, BigDecimal> balances = userWallet.getCurrencies();
        if(!balances.isEmpty()) {
            StringBuilder result = new StringBuilder("Balances:\n");
            balanceToString(balances, result);
            Map<Currency, BigDecimal> frozenBalances = userWallet.getFrozenCurrencies();
            if (!frozenBalances.isEmpty()) {
                result.append("Frozen Balances:\n");
                balanceToString(frozenBalances, result);
            }
            return result.toString();
        } else {
            return "No Balances";
        }    }

    private static void balanceToString(Map<Currency, BigDecimal> balances, StringBuilder result) {
        for (Map.Entry<Currency, BigDecimal> entry : balances.entrySet()) {
            Currency currency = entry.getKey();
            BigDecimal balance = entry.getValue();
            result.append(currency.getName()).append(": ").append(balance).append("\n");
        }
    }
}