package com.globant.service.loggedInServices;

import com.globant.exceptions.InvalidCryptoException;
import com.globant.model.User;
import com.globant.model.currencies.Currency;
import com.globant.model.wallets.ExchangeWallet;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.wallets.UserWallet;
import com.globant.service.CryptoFluctuator;
import com.globant.service.Validation;

import java.math.BigDecimal;

/**
 * The {@code ExchangeServiceFacade} class provides a simplified interface to manage
 * various cryptocurrency exchange operations. It encapsulates the complexity of
 * interacting with multiple services, making it easier for controllers to perform
 * actions such as buying cryptocurrencies, depositing fiat, and placing orders.
 *
 * <p>The multiple services are designed as utility with static methods to avoid the
 * need for instantiating objects. The main method, {@code execute}, triggers each
 * service process.</p>
 *
 * <p>This class almost acts like a facade that coordinates various services related to
 * cryptocurrency transactions, ensuring that users can interact with the exchange
 * system effectively and securely. </p>
 *
 * <p>Key attributes include:</p>
 * <ul>
 *   <li>{@code fiatCurrency} - Represents the fiat currency (e.g., USD) used in the exchange.</li>
 *   <li>{@code exchangeWallet} - The wallet that holds the exchange's cryptocurrency assets.</li>
 *   <li>{@code placeOrder} - The service responsible for placing buy and sell orders.</li>
 * </ul>
 *
 */
public class ExchangeServiceFacade {
    private final FiatCurrency fiatCurrency;
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
        CryptoFluctuator cryptoFluctuator = new CryptoFluctuator(exchangeWallet);
        cryptoFluctuator.startFluctuation();
    }

public BigDecimal depositFiat(User user, BigDecimal amount){
        return Deposit.execute(user, fiatCurrency, amount);
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
        //BigDecimal newPrice = FluctuateCrypto.execute(crypto);  Deprecated fluctuate crypto price way
        //crypto.setPrice(newPrice);
    }

    public String printUserBalance(User user) {
        return PrintBalance.execute(user);
    }

    public String placeBuyOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userBuyer) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.buyOrder(crypto, amount, price, userBuyer);
    }

    public String placeSellOrder(String cryptoString, BigDecimal amount, BigDecimal price, User userSeller) {
        CryptoCurrency crypto = findCrypto(cryptoString);
        return placeOrder.sellOrder(crypto, amount, price,userSeller);
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