package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.service.generic.UserGroupService;
import com.jabanto.tims.service.generic.UserRoleService;
import com.jabanto.tims.service.generic.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javafx.scene.control.Alert.AlertType.*;

@Component
public class UsersViewController {

    @FXML
    public ComboBox<String> register_role_cb;
    public ComboBox<String> register_group_cb;
    public TextField register_firstName;
    public TextField register_lastName;
    public TextField register_email;
    public PasswordField register_password;
    public TableView userTable;
    public TableColumn userIdCollumn;
    public TableColumn firstNameCollumn;
    public TableColumn lastNameCollumn;
    public TableColumn emailCollumn;
    public TableColumn roleCollumn;
    public TableColumn groupCollumn;
    public TableColumn enableCollumn;
    public TextField edit_firstName;
    public TextField edit_lastName;
    public TextField edit_email;
    public ComboBox<String> edit_group_cb;
    public ComboBox<String> edit_role_cb;
    public TextField edit_password;
    public ComboBox edit_enable;
    public Button edit_reset;
    public Button new_reset;
    public Button create_user;
    public Button edit_update_user;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserGroupService userGroupService;

    private int updateUserId;

    @FXML
    public void initialize(){
        loadUserTable();
        loadComboBox();
        configureTableSelection(userTable);
    }



    private void loadComboBox() {
        List<String> userRoles = userRoleService.getRolesNames();
        List<String> userGroups = userGroupService.getGroupNames();

        edit_role_cb.getItems().addAll(userRoles);
        edit_group_cb.getItems().addAll(userGroups);
        register_role_cb.getItems().addAll(userRoles);
        register_group_cb.getItems().addAll(userGroups);

        ObservableList<String> options = FXCollections.observableArrayList("Yes", "No");
        edit_enable.setItems(options);
    }

    private void configureTableSelection(TableView table) {

        // Configurar el listener para la selección de la tabla
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                if (newValue != null) {
                    // Actualizar los datos en el TextField y ComboBox
                    updateUserId = newValue.getId();
                    edit_firstName.setText(newValue.getFirstName());
                    edit_lastName.setText(newValue.getLastName());
                    edit_email.setText(newValue.getEmail());
                    // Configurar el ComboBox según el valor de getEnable()
                    edit_enable.setValue(newValue.getEnable()? "Yes" : "No");
                    edit_group_cb.setValue(newValue.getUserGroup().getGroupName());
                    edit_role_cb.setValue(newValue.getUserRole().getRoleName());

                    } else {
                    // Limpiar los datos si no hay ninguna fila seleccionada
                    edit_firstName.clear();
                    edit_lastName.clear();
                    edit_email.clear();
                    edit_enable.setValue("Enable");
                    edit_group_cb.setValue("User Group");
                    edit_role_cb.setValue("User Role");
                }
            }
        });
    }

    private void loadUserTable() {

        List<User> users = userService.readAllUsers();
        userTable.getItems().addAll(users);

        for (User user: users) {
            userIdCollumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            firstNameCollumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameCollumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            emailCollumn.setCellValueFactory(new PropertyValueFactory<>("email"));
            roleCollumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> userStringCellData) {
                    // userStringCellData.getValue() obtiene el objeto Usuario de la fila actual
                    // getUserRole() obtiene la propiedad userRole de la clase Usuario
                    // getValue() obtiene el objeto UserRole de la propiedad userRole
                    // "role_name" obtiene la propiedad nombre de la clase UserRole
                    return Bindings.selectString(userStringCellData.getValue().getUserRole(), "roleName");
                }
            });
            groupCollumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<User, String> userStringCellData) {
                    return Bindings.selectString(userStringCellData.getValue().getUserGroup(), "groupName");
                }
            });
            enableCollumn.setCellValueFactory(new PropertyValueFactory<>("enable"));
        }
    }

    @FXML
    public void resetFields(ActionEvent actionEvent) {

        Set<Button> resetButtons = new HashSet<>(Arrays.asList(edit_reset, new_reset));

        if (resetButtons.contains(actionEvent.getSource())) {
            edit_firstName.clear();
            edit_lastName.clear();
            edit_email.clear();
            edit_enable.getSelectionModel().clearSelection();
            edit_group_cb.getSelectionModel().clearSelection();
            edit_role_cb.getSelectionModel().clearSelection();

            register_firstName.clear();
            register_lastName.clear();
            register_email.clear();
            register_group_cb.getSelectionModel().clearSelection();
            register_role_cb.getSelectionModel().clearSelection();
            register_password.clear();
        }
    }

    @FXML
    public void registerUser(ActionEvent actionEvent) {

        User newUser = new User();

        if (actionEvent.getSource().equals(create_user)){

            newUser.setFirstName(register_firstName.getText());
            newUser.setLastName(register_lastName.getText());
            newUser.setEmail(register_email.getText());
            newUser.setUserRole(userRoleService.getRole(register_role_cb.getValue()).get());
            newUser.setUserGroup(userGroupService.getGroup(register_group_cb.getValue()).get());
            newUser.setPassword(register_password.getText());

            boolean userExits = userService.userEmailExits(newUser.getEmail());

            if (userExits){
                ViewControllerUtils.generateAlert(UserService.USER_EMAIL_EXITS, WARNING);
            }else {
                int result = userService.signUpUser(newUser);
                if (result == 1) {
                    ViewControllerUtils.generateAlert(UserService.USER_CREATED, INFORMATION);
                }else if (result == 3) {
                    ViewControllerUtils.generateAlert(UserService.USER_EMAIL_NOTVALID, WARNING);
                }else {
                    ViewControllerUtils.generateAlert(UserService.USER_DATABASE_ERROR, ERROR);
                }
                userTable.getItems().clear();
                loadUserTable();
            }
        }
    }

    @FXML
    public void updateUser(ActionEvent actionEvent) {

         User updateUser = new User();
        if (actionEvent.getSource().equals(edit_update_user) && !edit_email.getText().isEmpty()){

            updateUser.setFirstName(edit_firstName.getText());
            updateUser.setLastName(edit_lastName.getText());
            updateUser.setEmail(edit_email.getText());
            updateUser.setUserRole(userRoleService.getRole(edit_role_cb.getValue()).get());
            updateUser.setUserGroup(userGroupService.getGroup(edit_group_cb.getValue()).get());
            if (!edit_password.getText().isEmpty()){
                updateUser.setPassword(edit_password.getText());
            }
            User existingUser = (User)userService.loadUserByUsername(edit_email.getText());
            userService.updateUser(updateUser,existingUser);
            userTable.getItems().clear();
            loadUserTable();
            ViewControllerUtils.generateAlert("User with ID: "+ updateUserId +" successfully updated.", INFORMATION);

        }else {
            ViewControllerUtils.generateAlert("Email Text field is empty", WARNING);
        }

    }
}
