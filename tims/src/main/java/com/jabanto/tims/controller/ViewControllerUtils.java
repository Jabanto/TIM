package com.jabanto.tims.controller;

import javafx.scene.control.Alert;

public class ViewControllerUtils {

    public static void generateAlert(String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

}
