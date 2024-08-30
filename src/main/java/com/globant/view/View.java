package com.globant.view;

import java.math.BigDecimal;

public interface View {
    int getUserChoice();
    String getNameInput();
    String getEmailInput();
    String getPasswordInput();
    void showError(String errorMessage);
    void showInfo(String message);
    void showSuccessMessage(String message);
    void close();
    BigDecimal getAmountInput();
    int getLoggedInChoice();
    String getCryptoName();
}