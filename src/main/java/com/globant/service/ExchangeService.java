package com.globant.service;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.NegativeAmountException;
import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.orders.BuyOrder;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.model.wallets.UserWallet;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.wallets.Wallet;
import com.globant.service.orders.BuyOrderService;
import com.globant.service.orders.SellOrderService;

import java.math.BigDecimal;
import java.util.Map;

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
        amountValidation(amount);
        CryptoCurrency crypto = findCrypto(cryptoString);
        checkCurrencyFunds(amount, crypto, exchangeWallet);

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

    public String placeBuyOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userBuyer) {
        FiatCurrency fiat = fiatCurrency;
        checkCurrencyFunds(price, fiat, userBuyer.getWallet());
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.buyOrder(crypto, amount, price, userBuyer, exchangeWallet);
    }

    public String placeSellOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userSeller) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.sellOrder(crypto, amount, price,userSeller, exchangeWallet);
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

    private void amountValidation(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new NegativeAmountException();
        }
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

    private void checkCurrencyFunds(BigDecimal amount, Currency currency, Wallet wallet) {
        if (wallet.getWalletBalance(currency).compareTo(amount) < 0) {
            if (currency instanceof FiatCurrency) {
                throw new InsufficientFundsException("Not enough fiat in your balance.");
            } else {
                throw new InsufficientFundsException("Not enough cryptos to sell.");
            }
        }
    }
}