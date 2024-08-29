package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.NegativeAmountException;
import com.globant.model.User;
import com.globant.model.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExchangeService {
    private final Map<String, BigDecimal> cryptoInitialPrices;
    private final SessionService sessionService;
    private final Map<String, BigDecimal> cryptoBalances;

    public ExchangeService(SessionService sessionService) {
        this.cryptoInitialPrices = new HashMap<>();
        this.cryptoInitialPrices.put("BTC", new BigDecimal("50000"));
        this.cryptoInitialPrices.put("ETH", new BigDecimal("3000"));
        this.cryptoBalances = new HashMap<>();
        this.cryptoBalances.put("BTC", new BigDecimal("100"));
        this.cryptoBalances.put("ETH", new BigDecimal("100"));
        this.sessionService = sessionService;
    }

    public void deposit(User user, BigDecimal amount){
        amountValidation(amount);
        user.getWallet().receiveFiat(amount);
    }

    public void buyCrypto(String crypto, BigDecimal amount) {
        amountValidation(amount);
        checkCryptoAvailability(crypto);
        checkCryptoFunds(crypto, amount);
        BigDecimal price = totalPrice(crypto, amount);
        Wallet wallet = sessionService.getCurrentUser().getWallet();
        wallet.deliverFiat(price);
        sendCryptoToWallet(crypto, amount, wallet, price);
        cryptoBalances.put(crypto, cryptoBalances.get(crypto).subtract(amount));
    }

    private void sendCryptoToWallet(String crypto, BigDecimal amount, Wallet wallet, BigDecimal price) {
        try {
            wallet.receiveCrypto(crypto, amount);
        } catch (Exception e) {
            wallet.receiveFiat(price);
            throw new RuntimeException("There was a error buying the crypto");
        }
    }

    public void logOut(){
        sessionService.logout();
    }
    
    private BigDecimal totalPrice(String crypto, BigDecimal amount) {
        return this.cryptoInitialPrices.get(crypto).multiply(amount);
    }

    private void checkCryptoAvailability(String crypto) {
        if (!this.cryptoBalances.containsKey(crypto)) {
            throw new InvalidCryptoException("Invalid Crypto");
        }
    }
    
    private void checkCryptoFunds(String crypto, BigDecimal amount) {
        if (this.cryptoBalances.get(crypto).compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough cryptos to sell.");
        }
    }

    private void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
    }
}
