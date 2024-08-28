package com.globant.service;

import com.globant.model.InsufficientFundsException;
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

    public BigDecimal totalPrice(String crypto, BigDecimal amount) {
        return this.cryptoInitialPrices.get(crypto).multiply(amount);
    }

    public String buyCrypto(String crypto, BigDecimal amount) {
        if (this.cryptoBalances.containsKey(crypto)) {
            if (this.cryptoBalances.get(crypto).compareTo(amount) >= 0) {
                BigDecimal price = totalPrice(crypto, amount);
                Wallet wallet = sessionService.getCurrentUser().getWallet();
                wallet.deliverFiat(price);

                try {
                    wallet.receiveCrypto(crypto, amount);
                    cryptoBalances.put(crypto, cryptoBalances.get(crypto).subtract(amount));
                } catch (Exception e) {
                    wallet.depositFiat(price);
                    throw new RuntimeException("There was a error buying the crypto");
                }
                return "Purchase successful";
            }else{
                throw new InsufficientFundsException("Not enough cryptos to sell.");
            }
        }else{
            throw new InvalidCryptoException("Invalid Crypto");

        }
    }

    public void logOut(){
        sessionService.logout();
    }

}
