package com.globant.service.loggedInServices;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.Transaction;
import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.orders.BuyOrder;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;
import com.globant.model.wallets.Wallet;
import com.globant.service.Validation;
import com.globant.model.orderBook.BuyOrderBook;
import com.globant.model.orderBook.SellOrderBook;

import java.math.BigDecimal;

public class PlaceOrderService {
    private final SellOrderBook sellOrderService;
    private final BuyOrderBook buyOrderService;
    private final FiatCurrency fiat;
    public PlaceOrderService(FiatCurrency fiat) {
        this.sellOrderService = new SellOrderBook();
        this.buyOrderService = new BuyOrderBook();
        this.fiat = fiat;
    }

    public String sellOrder(CryptoCurrency crypto, BigDecimal amount, BigDecimal price, User userSeller, Wallet exchangeWallet){
        Validation.checkCurrencyFunds(amount, crypto, userSeller.getWallet());
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
            Transaction sellTransaction = new Transaction(crypto, amount, price, "Sell");
            userSeller.getWallet().saveTransaction(sellTransaction);
            Transaction buyTransaction = new Transaction(crypto, amount, price, "Buy");
            userBuyer.getWallet().saveTransaction(buyTransaction);
            return "A matching buy order was found and the sale was made.";
        } catch (OrderNotFoundException e) {
            userSeller.getWallet().deliverCurrency(crypto, amount);
            userSeller.getWallet().frozeCurrency(crypto, amount);
            SellOrder sellOrder = new SellOrder(userSeller, crypto, amount, price);
            sellOrderService.addOrder(sellOrder);
            return "Sell order made.";
        }
    }

    public String buyOrder(CryptoCurrency crypto, BigDecimal amount, BigDecimal price, User userBuyer, Wallet exchangeWallet) {
        Validation.checkCurrencyFunds(price, fiat, userBuyer.getWallet());
        try {
            Order order = sellOrderService.getOrder(amount, price, crypto);
            User userSeller = order.getUser();
            BigDecimal sellPrice = order.getPrice();
            userBuyer.getWallet().deliverCurrency(fiat, sellPrice);
            userBuyer.getWallet().receiveCurrency(crypto, amount);
            userSeller.getWallet().receiveCurrency(fiat, sellPrice);
            userSeller.getWallet().unfrozeCurrency(crypto, amount);
            //Create Transaction
            Transaction sellTransaction = new Transaction(crypto, amount, sellPrice, "Sell");
            userSeller.getWallet().saveTransaction(sellTransaction);
            Transaction buyTransaction = new Transaction(crypto, amount, sellPrice, "Buy");
            userBuyer.getWallet().saveTransaction(buyTransaction);
            return "A matching sell order was found and the purchase was made.";
        } catch (OrderNotFoundException e) {
            userBuyer.getWallet().deliverCurrency(fiat, price);
            userBuyer.getWallet().frozeCurrency(fiat, price);
            BuyOrder buyOrder = new BuyOrder(userBuyer, crypto, amount, price);
            buyOrderService.addOrder(buyOrder);
            return "Buy order made.";
        }
    }
}
