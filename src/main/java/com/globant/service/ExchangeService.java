package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.NegativeAmountException;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.model.wallets.UserWallet;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;

import java.math.BigDecimal;
import java.util.Map;

public class ExchangeService {
    public final FiatCurrency fiatCurrency;
    private final ExchangeWallet exchangeWallet;

    public ExchangeService() {
        exchangeWallet = new ExchangeWallet();
        CryptoCurrency bitcoin = new CryptoCurrency("Bitcoin","BTC", new BigDecimal("50000"));
        CryptoCurrency eth = new CryptoCurrency("Ether","ETH", new BigDecimal("3000"));
        FiatCurrency usd = new FiatCurrency("Dolar","USD", BigDecimal.ONE);
        exchangeWallet.receiveCurrency(bitcoin, new BigDecimal(100));
        exchangeWallet.receiveCurrency(eth, new BigDecimal(100));
        this.fiatCurrency = usd;
    }

    public void depositFiat(User user, BigDecimal amount){
        amountValidation(amount);
        user.getWallet().receiveCurrency(fiatCurrency, amount);
    }

    public void buyCrypto(String cryptoString, BigDecimal amount, User user) {
        amountValidation(amount);
        CryptoCurrency crypto = findCrypto(cryptoString);
        checkCryptoFunds(crypto, amount);
        BigDecimal price = totalPrice(crypto, amount);
        UserWallet userWallet = user.getWallet();
        userWallet.deliverCurrency(fiatCurrency, price);
        sendCryptoToWallet(crypto, amount, userWallet, price);
        exchangeWallet.deliverCurrency(crypto, amount);
    }

    public String printUserBalance(User user) {
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
        }
    }

    private static void balanceToString(Map<Currency, BigDecimal> balances, StringBuilder result) {
        for (Map.Entry<Currency, BigDecimal> entry : balances.entrySet()) {
            Currency currency = entry.getKey();
            BigDecimal balance = entry.getValue();
            result.append(currency.getName()).append(": ").append(balance).append("\n");
        }
    }

    private void sendCryptoToWallet(CryptoCurrency crypto, BigDecimal amount, UserWallet userWallet, BigDecimal price) {
        try {
            userWallet.receiveCurrency(crypto, amount);
        } catch (Exception e) {
            userWallet.receiveCurrency(fiatCurrency, price);
            throw new RuntimeException("There was a error buying the crypto");
        }
    }

    private BigDecimal totalPrice(CryptoCurrency crypto, BigDecimal amount) {
        return crypto.getPrice().multiply(amount);
    }

    private void checkCryptoFunds(CryptoCurrency crypto, BigDecimal amount) {
        if (exchangeWallet.getWalletBalance(crypto).compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough cryptos to sell.");
        }
    }

    private void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
    }


    public CryptoCurrency findCrypto(String cryptoString) {
        cryptoString = cryptoString.toUpperCase();
        for (Currency crypto : exchangeWallet.getCurrencies().keySet()) {
            if (crypto.getSymbol().equals(cryptoString) || crypto.getName().equals(cryptoString)) {
                return (CryptoCurrency) crypto;
            }
        }
        throw new InvalidCryptoException("Crypto not found: " + cryptoString);
    }
}