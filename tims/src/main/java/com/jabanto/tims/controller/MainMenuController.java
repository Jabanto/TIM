package com.jabanto.tims.controller;

import com.jabanto.tims.configuration.SpringFxmlLoader;
import com.jabanto.tims.dao.config.entities.UserDAO;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static javafx.scene.control.Alert.*;

@Component
public class MainMenuController {

    @FXML
    public Label current_date_label;
    public BorderPane mainPanel;
    public StackPane users_btn;
    public StackPane tools_btn;
    public StackPane items_btn;
    public StackPane keys_btn;
    public Button reports_btn;
    public Button backup_btn;
    public Button loginButton;
    public PasswordField loginPasswordField;
    public TextField loginNameField;

    @Value("classpath:/fxml/users_view.fxml")
    private Resource usersViewResource;
    @Value("${spring.application.ui.user-btn-description}")
    private String user_btn_description;
    @Value("${spring.application.ui.tools-btn-description}")
    private String tools_btn_description;
    @Value("${spring.application.ui.items-btn-description}")
    private String items_btn_description;
    @Value("${spring.application.ui.keys-btn-description}")
    private String keys_btn_description;
    @Value("${spring.application.ui.reports-btn-description}")
    private String reports_btn_description;
    @Value("${spring.application.ui.backup-btn-description}")
    private String backup_btn_description;

    public static boolean USERLOGGED = false;

    @Autowired
    SpringFxmlLoader fxmlLoader;

    @Autowired
    UserDAO userDAO;

    public MainMenuController() {
    }

    /**
     * In this method i can call all me define elements on my fxml file
     * and set properties as well cann i create new elements and set these
     * to my fxml file , but normally i set data from another services
     * into the elements that i have already create or defined on my
     * fxml file
     */

    @FXML
    public void initialize(){
        setButtonsDescriptions();
        setLocalTime_Date();

        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> updateTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        // Inicializar la hora al cargar la vista
        updateTime();
    }

    private void updateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String currentTime = dateFormat.format(new Date());
        //TODO Find why elementos are include in bottom no charge on the controller and are null
        //current_date_label.setText("Current Time: " + currentTime);

    }

    private void setLocalTime_Date() {
        // Hole das aktuelle Datum und die aktuelle Uhrzeit
        LocalDateTime now = LocalDateTime.now();

        // Formatiere das Datum und die Uhrzeit im gewünschten Format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
        String formattedDateTime = now.format(formatter);

        // Setze den Text des Labels
        //current_date_label.getText();
    }

    private void setButtonsDescriptions(){
        // Define all text for Tooltips from  buttons to the list
        Tooltip tooltipUsers = new Tooltip(user_btn_description);
        Tooltip tooltipItems = new Tooltip(items_btn_description);
        Tooltip tooltipKeys = new Tooltip(keys_btn_description);
        Tooltip tooltipTools = new Tooltip(tools_btn_description);
        Tooltip tooltipBackup = new Tooltip(backup_btn_description);
        Tooltip tooltipReports = new Tooltip(reports_btn_description);
        // create a tooltip and assing to the button
        Tooltip.install(users_btn,tooltipUsers);
        Tooltip.install(items_btn,tooltipItems);
        Tooltip.install(keys_btn,tooltipKeys);
        Tooltip.install(tools_btn,tooltipTools);
        Tooltip.install(backup_btn,tooltipBackup);
        Tooltip.install(reports_btn,tooltipReports);
    }


    @FXML
    public void openUserMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            fxmlLoader.changeWindow(usersViewResource);
        }
    }

    @FXML
    public void initiateLogin(ActionEvent actionEvent) {

        if (actionEvent.getSource().equals(loginButton)){

            if (loginNameField.getText().isEmpty() && loginPasswordField.getText().isEmpty()){
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("LOGIN ERROR");
                alert.setHeaderText("Invalid Data Access: check Password or Name");
                alert.showAndWait();
            }else {
                String userName = loginNameField.getText();
                String password = loginPasswordField.getText();
                int state = userDAO.login(userName,password);

                if (state!=-1){
                    if (state ==1){
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText("Login success, features are activate");
                        alert.show();
                        USERLOGGED=true;
                    }else {
                        Alert alert = new Alert(AlertType.WARNING);
                        alert.setHeaderText("Invalid Data Access: check Password or Name");
                        alert.showAndWait();
                    }
                }
            }
        }
    }
}
