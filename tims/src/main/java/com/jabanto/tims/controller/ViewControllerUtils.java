package com.jabanto.tims.controller;

import javafx.scene.control.Alert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewControllerUtils {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z]+$");

    public static void generateAlert(String headerText, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.showAndWait();
    }

    public static boolean validateToolName(String toolName) {
        if (toolName == null) {
            return false;
        }
        Matcher matcher = NAME_PATTERN.matcher(toolName);
        return matcher.matches() && toolName.trim().length() == toolName.length();
    }

}
