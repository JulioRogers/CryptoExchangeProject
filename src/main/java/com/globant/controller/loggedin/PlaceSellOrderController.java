package com.globant.controller.loggedin;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;

import java.math.BigDecimal;

public class PlaceSellOrderController extends LoggedInUserController{

    @Override
    public void run() {
        String cryptoString = view.getStringInput("Enter crypto name (BTC or ETH): ");
        BigDecimal amount = view.getBigDecimalInput("Enter amount: ");
        BigDecimal price = view.getBigDecimalInput("Enter price: ");
        try {
            placeSelleOrder(cryptoString, amount, price);
        } catch (InvalidCryptoException e) {
            view.showError(e.getMessage());
        }
    }

    private static void placeSelleOrder(String cryptoString, BigDecimal amount, BigDecimal price) {
        CryptoCurrency crypto = exchangeService.findCrypto(cryptoString);
        checkCryptoFunds(amount, crypto);
        User userSeller = user;
        FiatCurrency fiat = exchangeService.fiatCurrency;
        try {
            Order order = buyOrderService.getOrder(amount, price, crypto);
            User userBuyer = order.getUser();
            BigDecimal change = order.getPrice().subtract(price);
            userSeller.getWallet().deliverCurrency(crypto, amount);
            userSeller.getWallet().receiveCurrency(fiat, price);
            userBuyer.getWallet().receiveCurrency(crypto, amount);
            userBuyer.getWallet().unfrozeCurrency(fiat, price);
            userBuyer.getWallet().unfrozeCurrency(fiat, change);
            userBuyer.getWallet().receiveCurrency(fiat, change);
            //Create Transaction
            view.showSuccessMessage("A matching buy order was found and the sale was made.");
        } catch (OrderNotFoundException e) {
            userSeller.getWallet().deliverCurrency(crypto, amount);
            userSeller.getWallet().frozeCurrency(crypto, amount);
            SellOrder sellOrder = new SellOrder(userSeller, crypto, amount, price);
            sellOrderService.addOrder(sellOrder);
            view.showSuccessMessage("Sell order made.");
        }
    }

    private static void checkCryptoFunds(BigDecimal amount, CryptoCurrency crypto) {
        if (user.getWallet().getWalletBalance(crypto).compareTo(amount) < 0) {
            throw new InsufficientFundsException("Not enough cryptos to sell.");
        }
    }
}
