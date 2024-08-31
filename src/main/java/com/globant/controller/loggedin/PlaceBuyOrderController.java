package com.globant.controller.loggedin;

import com.globant.exceptions.InsufficientFundsException;
import com.globant.exceptions.InvalidCryptoException;
import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.orders.BuyOrder;
import com.globant.model.orders.Order;
import com.globant.service.orders.BuyOrderService;
import com.globant.service.orders.SellOrderService;

import java.math.BigDecimal;

public class PlaceBuyOrderController extends LoggedInUserController {

    @Override
    public void run() {
        String cryptoString = view.getStringInput("Enter crypto name (BTC or ETH): ");
        BigDecimal amount = view.getBigDecimalInput("Enter amount: ");
        BigDecimal price = view.getBigDecimalInput("Enter price: ");
        try {
            placeBuyOrder(cryptoString, amount, price);
        } catch (InvalidCryptoException e) {
            view.showError(e.getMessage());
        }
    }

    private static void placeBuyOrder(String cryptoString, BigDecimal amount, BigDecimal price) {
        checkFiatFunds(price);
        CryptoCurrency crypto = exchangeService.findCrypto(cryptoString);
        User userBuyer = user;
        FiatCurrency fiat = exchangeService.fiatCurrency;
        try {
            Order order = sellOrderService.getOrder(amount, price, crypto);
            User userSeller = order.getUser();
            BigDecimal sellPrice = order.getPrice();
            userBuyer.getWallet().deliverCurrency(fiat, sellPrice);
            userBuyer.getWallet().receiveCurrency(crypto, amount);
            userSeller.getWallet().receiveCurrency(fiat, sellPrice);
            userSeller.getWallet().unfrozeCurrency(crypto, amount);
            //Create Transaction
            view.showSuccessMessage("A matching sell order was found and the purchase was made.");
        } catch (OrderNotFoundException e) {
            userBuyer.getWallet().deliverCurrency(fiat, price);
            userBuyer.getWallet().frozeCurrency(fiat, price);
            BuyOrder buyOrder = new BuyOrder(userBuyer, crypto, amount, price);
            buyOrderService.addOrder(buyOrder);
            view.showSuccessMessage("Buy order made.");
        }
    }

    private static void checkFiatFunds(BigDecimal price) {
        if (user.getWallet().getWalletBalance(exchangeService.fiatCurrency).compareTo(price) < 0) {
            throw new InsufficientFundsException("Not enough fiat to sell.");
        }
    }
}