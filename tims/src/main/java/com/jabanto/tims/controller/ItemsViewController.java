package com.jabanto.tims.controller;

import com.jabanto.tims.dao.models.Item;
import com.jabanto.tims.dao.models.ItemType;
import com.jabanto.tims.dao.models.User;
import com.jabanto.tims.service.generic.CategoryService;
import com.jabanto.tims.service.generic.ItemService;
import com.jabanto.tims.service.generic.ItemStatusService;
import com.jabanto.tims.service.generic.UserService;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static javafx.scene.control.Alert.AlertType.*;

@Component
public class ItemsViewController {

    @FXML
    public ComboBox new_group;
    public ComboBox<String> new_category;
    public TextField new_itemName;
    public TableView tableItems;
    public TableColumn itemsIDColumn;
    public TableColumn nameColumn;
    public TableColumn categoryColumn;
    public TableColumn<Item, ItemType> groupColumn;
    public TableColumn statusColumn;
    public TableColumn remarkColumn;
    public TextField edit_name;
    public ComboBox edit_group;
    public ComboBox edit_category;
    public ComboBox edit_status;
    public TextArea edit_remark;
    public Button new_item;
    public Button new_reset;
    public Button edit_update_item;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ItemStatusService itemStatusService;
    private int updateItemId;

    @FXML
    public void initialize(){

        loadItemsTable("ALL");
        loadComboBox();
        configureItemsSelectionTable();
        configureComboBoxSelection();
    }

    private void configureItemsSelectionTable() {

        // Configurar el listener para la selección de la tabla
        tableItems.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Item>() {
            @Override
            public void changed(ObservableValue<? extends Item> observable, Item oldValue, Item newValue) {
                if (newValue != null ) {
                    // Actualizar los datos en el TextField y ComboBox
                    updateItemId = newValue.getId();
                    edit_name.setText(newValue.getItemName());
                    edit_group.setValue(newValue.getItemType().name());
                    edit_category.setValue(newValue.getCategory().getName());
                    edit_status.setValue(newValue.getStatus().getName());
                    edit_remark.setText(newValue.getRemark());
                } else {
                    // Limpiar los datos si no hay ninguna fila seleccionada

                    edit_name.clear();
                    edit_category.setValue("Item Category");
                    edit_remark.clear();
                }
            }
        });
    }

    private void configureComboBoxSelection() {
        // Agregar un ChangeListener al ComboBox
        edit_group.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Aquí se ejecuta tu método cuando se selecciona un nuevo valor
                tableItems.getItems().clear();
                if (newValue != null){
                    loadItemsTable(newValue);
                }
            }
        });

        // Configure combo box selection by creating new keys
        new_group.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Lógica para establecer automáticamente el valor en el segundo ComboBox
            if ("KEYS".equals(newValue)) {
                new_category.setValue("Storage");
                new_category.setDisable(true); // Deshabilitar el ComboBox
                tableItems.getItems().clear();
                loadItemsTable("KEYS");
            } else {
                new_category.setValue(null); // Restablecer el valor si no es ninguna opción válida
                new_category.setDisable(false); // Habilitar el ComboBox
                tableItems.getItems().clear();
                loadItemsTable("ALL");
            }
        });
    }


    private void loadComboBox() {

        List<String> itemTypes = Arrays.stream(ItemType.values())
                .map(ItemType::name)
                .collect(Collectors.toList());

        List<String> categoriesNames = categoryService.getCategoriesNames();
        List<String> statusNames = itemStatusService.getStatusNames();

        new_group.getItems().addAll(itemTypes);
        new_category.getItems().addAll(categoriesNames);
        edit_group.getItems().addAll(itemTypes);
        edit_category.getItems().addAll(categoriesNames);
        edit_status.getItems().addAll(statusNames);
    }

    private void loadItemsTable(String itemTypeName) {

        List<Item> items = itemService.readItems();
        List<Item> filteredItems = new ArrayList<>();

        if (itemTypeName.equals("TOOLS")){
            filteredItems = items.stream()
                    .filter(item -> item.getItemType() == ItemType.TOOLS)
                    .collect(Collectors.toList());
            items = filteredItems;
        }else if (itemTypeName.equals("KEYS")){
            filteredItems = items.stream()
                    .filter(item -> item.getItemType() == ItemType.KEYS)
                    .collect(Collectors.toList());
            items = filteredItems;
        }

        tableItems.getItems().addAll(items);

        for (Item item: items) {
            itemsIDColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
            groupColumn.setCellValueFactory(new PropertyValueFactory<>("itemType"));
            categoryColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> itemStringCellData) {
                    return Bindings.selectString(itemStringCellData.getValue().getCategory(), "name");
                }
            });
            statusColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TableColumn.CellDataFeatures<Item, String> itemStringCellData) {
                    return Bindings.selectString(itemStringCellData.getValue().getStatus(), "name");
                }
            });
            remarkColumn.setCellValueFactory(new PropertyValueFactory<>("remark"));
        }
    }




    @FXML
    public void createItem(ActionEvent actionEvent) {

        Item newItem = new Item();
        if (actionEvent.getSource().equals(new_item)){
            boolean validName = ViewControllerUtils.validateToolName(new_itemName.getText());
            if (validName){
                newItem.setItemName(new_itemName.getText());
                newItem.setItemType(new_group.getValue().equals("TOOLS")? ItemType.TOOLS:ItemType.KEYS);
                newItem.setCategory(categoryService.getCategory(new_category.getValue()).get());
                newItem.setStatus(itemStatusService.getStatus("Avaible").get());
                newItem.setRemark("");
                int result = itemService.createItem(newItem);
                if (result == 1) {
                    ViewControllerUtils.generateAlert(ItemService.ITEM_CREATED, INFORMATION);
                }else if (result == 2) {
                    ViewControllerUtils.generateAlert(ItemService.ITEM_EXITS, WARNING);
                }else {
                    ViewControllerUtils.generateAlert(UserService.USER_DATABASE_ERROR, ERROR);
                }
                tableItems.getItems().clear();
                loadItemsTable(newItem.getItemName());
            }else {
                ViewControllerUtils.generateAlert(ItemService.ITEM_NAME_INVALID, Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    public void reset_itemInfo(ActionEvent actionEvent) {

        Set<Button> resetButtons = new HashSet<>(Arrays.asList(new_reset));

        if (resetButtons.contains(actionEvent.getSource())) {
            new_itemName.clear();
            new_group.getSelectionModel().clearSelection();
            new_category.getSelectionModel().clearSelection();
        }

    }

    @FXML
    public void updateItem(ActionEvent actionEvent) {

        Item updateItem = new Item();
        if (actionEvent.getSource().equals(edit_update_item) && !edit_name.getText().isEmpty()){

            updateItem.setItemName(edit_name.getText());
            updateItem.setItemType(edit_group.getValue().equals("KEYS")? ItemType.KEYS:ItemType.TOOLS);
            updateItem.setCategory(categoryService.getCategory(edit_category.getValue().toString()).get());
            updateItem.setStatus(itemStatusService.getStatus(edit_status.getValue().toString()).get());
            if (edit_remark.getText().isEmpty()){ edit_remark.setText(""); }
            updateItem.setRemark(edit_remark.getText());

            Item existingItem = itemService.getItemById(updateItemId).get();

            itemService.updateItem(updateItem,existingItem);
            tableItems.getItems().clear();
            loadItemsTable("ALL");
            ViewControllerUtils.generateAlert("Item with ID: "+ updateItemId +" successfully updated.", INFORMATION);
        }else {
            ViewControllerUtils.generateAlert("Item name Text field is empty", WARNING);
        }

    }
}
