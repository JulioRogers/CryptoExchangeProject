package com.globant.service;

import com.globant.model.Wallet;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ExchangeService {
    private final Map<String, BigDecimal> cryptoInitialPrices;
    private final SessionService sessionService;

    public ExchangeService(SessionService sessionService) {
        this.cryptoInitialPrices = new HashMap<>();
        this.cryptoInitialPrices.put("BTC", new BigDecimal("50000"));
        this.cryptoInitialPrices.put("ETH", new BigDecimal("3000"));
        this.sessionService = sessionService;
    }

    public BigDecimal totalPrice(String crypto, BigDecimal amount) {
        return this.cryptoInitialPrices.get(crypto).multiply(amount);
    }
    public String buyCrypto(String crypto, BigDecimal amount) {
        BigDecimal price = totalPrice(crypto, amount);
        Wallet wallet = sessionService.getCurrentUser().getWallet();
        wallet.deliverFiat(price);
        wallet.receiveCrypto(crypto, amount);
        return "Purchase successful";
    }

    public void logOut(){
        sessionService.logout();
    }

}
