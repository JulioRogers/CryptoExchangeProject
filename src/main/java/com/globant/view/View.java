package com.globant.view;

import java.math.BigDecimal;

public interface View {
    int getUserChoice();
    int getLoggedInChoice();

    BigDecimal getBigDecimalInput(String prompt);
    String getStringInput(String prompt);

    void showError(String errorMessage);
    void showInfo(String message);
    void showSuccessMessage(String message);

    void close();
}