package com.globant.service.loggedInServices;

import com.globant.exceptions.OrderNotFoundException;
import com.globant.model.Transaction;
import com.globant.model.User;
import com.globant.model.currencies.CryptoCurrency;
import com.globant.model.currencies.FiatCurrency;
import com.globant.model.orders.BuyOrder;
import com.globant.model.orders.Order;
import com.globant.model.orders.SellOrder;
import com.globant.model.wallets.UserWallet;
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

    public String sellOrder(CryptoCurrency crypto, BigDecimal amount, BigDecimal price, User userSeller){
        Validation.checkCurrencyFunds(amount, crypto, userSeller.getWallet());
        UserWallet userSellerWallet = userSeller.getWallet();
        try {
            Order order = buyOrderService.getOrder(amount, price, crypto);
            User userBuyer = order.getUser();
            UserWallet userBuyerWallet = userBuyer.getWallet();
            BigDecimal change = order.getPrice().subtract(price);
            userSellerWallet.deliverCurrency(crypto, amount);
            userSellerWallet.receiveCurrency(fiat, price);
            userBuyerWallet.receiveCurrency(crypto, amount);
            userBuyerWallet.unfrozeCurrency(fiat, price);
            userBuyerWallet.unfrozeCurrency(fiat, change);
            userBuyerWallet.receiveCurrency(fiat, change);

            Transaction sellTransaction = new Transaction(crypto, amount, price, "Sell");
            userSellerWallet.saveTransaction(sellTransaction);
            Transaction buyTransaction = new Transaction(crypto, amount, price, "Buy");
            userBuyerWallet.saveTransaction(buyTransaction);
            return "A matching buy order was found and the sale was made.";
        } catch (OrderNotFoundException e) {
            userSellerWallet.deliverCurrency(crypto, amount);
            userSellerWallet.frozeCurrency(crypto, amount);
            SellOrder sellOrder = new SellOrder(userSeller, crypto, amount, price);
            sellOrderService.addOrder(sellOrder);
            return "Sell order made.";
        }
    }

    public String buyOrder(CryptoCurrency crypto, BigDecimal amount, BigDecimal price, User userBuyer) {
        Validation.checkCurrencyFunds(price, fiat, userBuyer.getWallet());
        UserWallet userBuyerWallet = userBuyer.getWallet();
        try {
            Order order = sellOrderService.getOrder(amount, price, crypto);
            User userSeller = order.getUser();
            UserWallet userSellerWallet = userSeller.getWallet();
            BigDecimal sellPrice = order.getPrice();
            userBuyerWallet.deliverCurrency(fiat, sellPrice);
            userBuyerWallet.receiveCurrency(crypto, amount);
            userSellerWallet.receiveCurrency(fiat, sellPrice);
            userSellerWallet.unfrozeCurrency(crypto, amount);
            //Create Transaction
            Transaction sellTransaction = new Transaction(crypto, amount, sellPrice, "Sell");
            userSellerWallet.saveTransaction(sellTransaction);
            Transaction buyTransaction = new Transaction(crypto, amount, sellPrice, "Buy");
            userBuyerWallet.saveTransaction(buyTransaction);
            return "A matching sell order was found and the purchase was made.";
        } catch (OrderNotFoundException e) {
            userBuyerWallet.deliverCurrency(fiat, price);
            userBuyerWallet.frozeCurrency(fiat, price);
            BuyOrder buyOrder = new BuyOrder(userBuyer, crypto, amount, price);
            buyOrderService.addOrder(buyOrder);
            return "Buy order made.";
        }
    }
}
