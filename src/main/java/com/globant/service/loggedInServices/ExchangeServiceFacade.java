package com.globant.service.loggedInServices;

import com.globant.exceptions.InvalidCryptoException;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.wallets.UserWallet;
import com.globant.service.Validation;

import java.math.BigDecimal;

public class ExchangeServiceFacade {
    public final FiatCurrency fiatCurrency;
    private final ExchangeWallet exchangeWallet;
    private final PlaceOrderService placeOrder;


    public ExchangeServiceFacade() {
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
        exchangeWallet.deliverCurrency(crypto, amount);
        BigDecimal newPrice = FluctuateCrypto.execute(crypto);
        crypto.setPrice(newPrice);
    }

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

    public String getTransactions(User user) {
        return GetTransactions.execute(user);
    }

    public BigDecimal getCryptoValue(String cryptoString) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return crypto.getPrice();
    }
}