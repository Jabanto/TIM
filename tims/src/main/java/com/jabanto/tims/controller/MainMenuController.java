package com.jabanto.tims.controller;

import com.jabanto.tims.configuration.DataBaseConfig;
import com.jabanto.tims.configuration.SpringFxmlLoader;
import com.jabanto.tims.service.generic.UserGroupService;
import com.jabanto.tims.service.generic.UserRoleService;
import com.jabanto.tims.service.generic.UserService;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static javafx.scene.control.Alert.AlertType.*;

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
    public Button backupView_btn;
    public Button login_btn;
    public PasswordField loginPasswordField;
    public TextField loginNameField;
    public Button backUp_btn;

    @Value("classpath:/fxml/users_view.fxml")
    private Resource usersViewResource;
    @Value("classpath:/fxml/items_view.fxml")
    private Resource itemsViewResource;
    @Value("classpath:/fxml/reports_view.fxml")
    private Resource reportsViewResource;
    @Value("classpath:/fxml/tools_view.fxml")
    private Resource toolsViewResource;
    @Value("classpath:/fxml/backup_view.fxml")
    private Resource backupViewResource;
    @Value("classpath:/fxml/keys_view.fxml")
    private Resource keysViewResource;

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
    public static String USERLOGGED_NAME = "";
    private static final int BACK_UP_SUCCESS = 1;
    private static final int BACK_UP_ERROR = 2;

    @Autowired
    private SpringFxmlLoader fxmlLoader;

    @Autowired
    private UserService userService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DataBaseConfig dataBaseConfig;

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
        Tooltip.install(backupView_btn,tooltipBackup);
        Tooltip.install(reports_btn,tooltipReports);
    }


    @FXML
    public void openUserMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){

            if(USERLOGGED){
                fxmlLoader.changeWindow(usersViewResource);
            }else {
                ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void openItemsMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){

            if(USERLOGGED){
                fxmlLoader.changeWindow(itemsViewResource);
            }else {
               ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void openToolsMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if(USERLOGGED){
                fxmlLoader.changeWindow(toolsViewResource);
            }else {
               ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void openReportsMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if(USERLOGGED){
                fxmlLoader.changeWindow(reportsViewResource);
            }else {
                ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void openBackupMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if(USERLOGGED){
                fxmlLoader.changeWindow(backupViewResource);
            }else {
               ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void openKeysMenu(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
            if(USERLOGGED){
                fxmlLoader.changeWindow(keysViewResource);
            }else {
                ViewControllerUtils.generateAlert("ACCESS RESTRICTION: Only logged User can open this view: check or log in", WARNING);
            }
        }
    }

    @FXML
    public void initiateLogin(ActionEvent actionEvent) {

        if (actionEvent.getSource().equals(login_btn)&&!login_btn.getText().equals("Logout")){

            if (loginNameField.getText().isEmpty() && loginPasswordField.getText().isEmpty()){
                ViewControllerUtils.generateAlert ("LOGIN ERROR : Invalid Data Access: check Password or Email",WARNING);
            }else {
                String userName = loginNameField.getText();
                String password = loginPasswordField.getText();
                int state = userService.loginUser(userName,password);

                if (state!=-1){
                    if (state==1){
                        ViewControllerUtils.generateAlert("Login success, features are activate", CONFIRMATION);
                        loginNameField.setText("");
                        loginPasswordField.setText("");
                        loginPasswordField.setDisable(true);
                        loginNameField.setDisable(true);
                        login_btn.setText("Logout");
                        USERLOGGED=true;
                        USERLOGGED_NAME=userName;
                    }
                }else {
                    ViewControllerUtils.generateAlert("Invalid Data Access: check Password or Name",WARNING);
                }
            }
        }else {
            loginPasswordField.setDisable(false);
            loginNameField.setDisable(false);
            //TODO set button to Log when another view is open and closet again , check controllers
            login_btn.setText("Log in");
            USERLOGGED=false;
        }
    }

    @FXML
    public void eventKey(KeyEvent keyEvent) {
        Object evt = keyEvent.getSource();
        if (evt.equals(loginNameField) || evt.equals(loginPasswordField)) {
            if (keyEvent.getCharacter().equals(" ")) {
                keyEvent.consume();
            }
        }
    }

    @FXML
    public void createDbBackup(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(backUp_btn)){

            backUp_btn.setOnAction(e-> showDirectoryChooser());

        }
    }

    private void showDirectoryChooser() {
        
        File selectedFile = showFileChooser("Select Database File for backup.");
        File selectedDirectory = showDirectoryChooser("Select folder to save Database Backup.");
        // Aquí debes tener la lógica para copiar tu archivo a la carpeta seleccionada
        if (selectedDirectory != null && selectedFile !=null) {
             int result = copyFileToDirectory(selectedFile, selectedDirectory);
             if (result==1){
                 ViewControllerUtils.generateAlert("Backup Success. Data base copy is located in: "+selectedDirectory.getPath() , INFORMATION);
             }else{
         ViewControllerUtils.generateAlert("An error occurred during the backup process. Please check the logs for details", ERROR);
             }
        }else {
            ViewControllerUtils.generateAlert("The backup couldn't be performed. A folder or file to copy needs to be selected.",WARNING);
        }
    }


    public File showFileChooser(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        return fileChooser.showOpenDialog(null);
    }

    public File showDirectoryChooser(String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(null);
    }

    private int copyFileToDirectory(File sourceFile, File targetDirectory) {
        try {
            Files.copy(sourceFile.toPath(), new File(targetDirectory, sourceFile.getName()).toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup File copied successfully.");
            return BACK_UP_SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
            return BACK_UP_ERROR;
        }
    }
}