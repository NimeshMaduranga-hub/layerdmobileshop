package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.ItemBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

public class ManageItemsForm {

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQtyOnHand;

    @FXML
    private Button btnAddNewItem;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Item> tblItems;
    
    @FXML
    private Button btnUserAndPeople;

    @FXML
    private Button btnSuppliers;

    @FXML
    private Button btnWarranty;

    @FXML
    private Label lblUser;
    
    @FXML
    private Button btnLogout;


    @FXML
    private Button btnOrder;
    //Dependency injection
    ItemBO itemBO=(ItemBO) BOFactory.getInstance().getBo(BOFactory.BOType.ITEM);

    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));

        initUI();

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtCode.setDisable(false);
                txtDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });

        txtQtyOnHand.setOnAction(event -> btnSave.fire());
        loadAllItems();
    }

    private void loadAllItems() {
        tblItems.getItems().clear();
        try {
            ArrayList<ItemDTO> itemList = itemBO.getAllItems();

            for(ItemDTO itemDTO : itemList) {
                tblItems.getItems().add(new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtCode.clear();
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtCode.setDisable(true);
        txtDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);
        txtCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtCode.setDisable(false);
        txtDescription.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtCode.clear();
        txtCode.setText(generateNewId());
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItems.getSelectionModel().clearSelection();
    }

    @FXML
    void btnDelete_OnAction(ActionEvent event) {
        /*Delete Item*/
        String code = tblItems.getSelectionModel().getSelectedItem().getCode();
        try {
            if (!existItem(code)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }

            itemBO.deleteItem(code);

            tblItems.getItems().remove(tblItems.getSelectionModel().getSelectedItem());
            tblItems.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();

        if (!description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        } else if (!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        } else if (!txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item

                itemBO.saveItem(new ItemDTO(code, description, unitPrice, qtyOnHand));

                tblItems.getItems().add(new Item(code, description, unitPrice, qtyOnHand));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                /*Update Item*/

                itemBO.updateItem(new ItemDTO(code, description, unitPrice, qtyOnHand));

                Item selectedItem = tblItems.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setQtyOnHand(qtyOnHand);
                selectedItem.setUnitPrice(unitPrice);
                tblItems.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewItem.fire();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {

        return itemBO.isExist(code);
    }

    private String generateNewId() {
        try {

            return itemBO.genarateNewId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }

    @FXML
    void btnOrderOnAction(ActionEvent event) throws IOException {
        App.setRoot("place-order-form");
    }


    public void btnUserAndPeopleOnAction(ActionEvent event) {
    }

    public void btnSuppliersGroupOnAction(ActionEvent event) {
    }

    public void btnWarrantyGroupOnAction(ActionEvent event) {
    }

    public void btnLogoutOnAction(ActionEvent event) {
    }
}
