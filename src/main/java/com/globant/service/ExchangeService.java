package com.globant.service;

import com.globant.exceptions.InvalidCryptoException;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.wallets.UserWallet;

import java.math.BigDecimal;

public class ExchangeService {
    public final FiatCurrency fiatCurrency;
    private final ExchangeWallet exchangeWallet;
    private final PlaceOrderService placeOrder;


    public ExchangeService() {
        exchangeWallet = new ExchangeWallet();

        CryptoCurrency bitcoin = new CryptoCurrency("Bitcoin","BTC", new BigDecimal("50000"));
        CryptoCurrency eth = new CryptoCurrency("Ether","ETH", new BigDecimal("3000"));
        FiatCurrency usd = new FiatCurrency("Dolar","USD", BigDecimal.ONE);

        exchangeWallet.receiveCurrency(bitcoin, new BigDecimal(100));
        exchangeWallet.receiveCurrency(eth, new BigDecimal(100));
        this.fiatCurrency = usd;

        this.placeOrder = new PlaceOrderService(fiatCurrency);
    }

    public void depositFiat(User user, BigDecimal amount){
        Deposit.execute(user, fiatCurrency, amount);
    }

    public void buyCrypto(String cryptoString, BigDecimal amount, User user) {
        Validation.amountValidation(amount);
        CryptoCurrency crypto = findCrypto(cryptoString);
        Validation.checkCurrencyFunds(amount, crypto, exchangeWallet);

        BigDecimal price = crypto.getPrice().multiply(amount);
        UserWallet userWallet = user.getWallet();
        userWallet.deliverCurrency(fiatCurrency, price);
        userWallet.receiveCurrency(crypto, amount);
        exchangeWallet.deliverCurrency(crypto, amount);    }

    public String printUserBalance(User user) {
        return PrintBalance.execute(user);
    }

    public String placeBuyOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userBuyer) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.buyOrder(crypto, amount, price, userBuyer, exchangeWallet);
    }

    public String placeSellOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userSeller) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.sellOrder(crypto, amount, price,userSeller, exchangeWallet);
    }

    private CryptoCurrency findCrypto(String cryptoString) {
        cryptoString = cryptoString.toUpperCase();
        for (Currency crypto : exchangeWallet.getCurrencies().keySet()) {
            if (crypto.getSymbol().equals(cryptoString) || crypto.getName().equals(cryptoString)) {
                return (CryptoCurrency) crypto;
            }
        }
        throw new InvalidCryptoException("Crypto not found: " + cryptoString);
    }
}