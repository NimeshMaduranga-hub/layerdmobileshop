package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.ItemBO;
import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;
import net.sf.jasperreports.engine.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
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
    private TextField txtEmi;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtStorage;

    @FXML
    private DatePicker colReceivedDate;

    @FXML
    private ComboBox<String> cmbWarranty;


    @FXML
    private Button btnOrder;
    //Dependency injection
    ItemBO itemBO=(ItemBO) BOFactory.getInstance().getBo(BOFactory.BOType.ITEM);

    public void initialize() {
        tblItems.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItems.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("description"));
        tblItems.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
        tblItems.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        tblItems.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblItems.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("storage"));
        tblItems.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("color"));
        tblItems.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("emiNo"));
        tblItems.getColumns().get(8).setCellValueFactory(new PropertyValueFactory<>("warranty"));

        initUI();
        cmbWarranty.setItems(FXCollections.observableArrayList(
                "No Warranty ","one month","3 months","6 months", "one year", "two years", "Lifetime"
        ));

        tblItems.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                cmbWarranty.setDisable(false);
                txtEmi.setDisable(false);
                txtColor.setDisable(false);
                txtStorage.setDisable(false);
                colReceivedDate.setDisable(false);
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

            for (ItemDTO i : itemList) {

                tblItems.getItems().add(new Item(
                        i.getCode(),
                        i.getDescription(),
                        i.getReceivedDate(),
                        i.getQtyOnHand(),
                        i.getUnitPrice(),
                        i.getStorage(),
                        i.getColor(),
                        i.getEmiNo(),
                        i.getWarranty()
                ));
            }

        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
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
        cmbWarranty.setDisable(true);
        txtEmi.setDisable(true);
        txtColor.setDisable(true);
        txtStorage.setDisable(true);
        colReceivedDate.setDisable(true);
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
        cmbWarranty.setDisable(false);
        txtEmi.setDisable(false);
        txtColor.setDisable(false);
        txtStorage.setDisable(false);
        colReceivedDate.setDisable(false);
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

/*    public void btnSave_OnAction(ActionEvent actionEvent) {
        String code = txtCode.getText();
        String description = txtDescription.getText();
        String storage = txtStorage.getText().isEmpty() ? null : txtStorage.getText();
        String color = txtColor.getText().isEmpty() ? null : txtColor.getText();
        String emiNo = txtEmi.getText().isEmpty() ? null : txtEmi.getText();
        String warranty = cmbWarranty.getValue();
        java.time.LocalDate receivedDate = colReceivedDate.getValue();


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
                itemBO.saveItem(new ItemDTO(
                        code,
                        description,
                        receivedDate,
                        qtyOnHand,
                        unitPrice,
                        storage,
                        color,
                        emiNo,
                        warranty
                ));

                loadAllItems();
                initUI();

                tblItems.getItems().add(new Item(
                        code,
                        description,
                        receivedDate,
                        Integer.parseInt(txtQtyOnHand.getText()),
                        new BigDecimal(txtUnitPrice.getText()),
                        storage,
                        color,
                        txtEmi.getText().isEmpty() ? null : txtEmi.getText(),
                        warranty
                ));



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
                *//*Update Item*//*

                itemBO.updateItem(new ItemDTO(
                        code,
                        description,
                        receivedDate,
                        qtyOnHand,
                        unitPrice,
                        storage,
                        color,
                        emiNo,
                        warranty
                ));

                loadAllItems();
                initUI();

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
    */

    public void btnSave_OnAction(ActionEvent actionEvent) {

        String code = txtCode.getText();
        String description = txtDescription.getText();
        String storage = txtStorage.getText().isEmpty() ? "N/A" : txtStorage.getText();
        String color = txtColor.getText().isEmpty() ? "N/A" : txtColor.getText();
        String emiNo = txtEmi.getText().isEmpty() ? "N/A" : txtEmi.getText();
        String warranty = cmbWarranty.getValue() == null ? "No Warranty" : cmbWarranty.getValue();
        java.time.LocalDate receivedDate = colReceivedDate.getValue();

        // =========================
        // 🔴 VALIDATION SECTION
        // =========================

        if (code == null || code.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Item Code cannot be empty").show();
            txtCode.requestFocus();
            return;
        }

        if (description == null || !description.matches("[A-Za-z0-9 ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid description").show();
            txtDescription.requestFocus();
            return;
        }

        if (txtUnitPrice.getText() == null || txtUnitPrice.getText().isEmpty()
                || !txtUnitPrice.getText().matches("^[0-9]+(\\.[0-9]+)?$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        }

        if (txtQtyOnHand.getText() == null || txtQtyOnHand.getText().isEmpty()
                || !txtQtyOnHand.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        if (receivedDate == null) {
            new Alert(Alert.AlertType.ERROR, "Please select received date").show();
            colReceivedDate.requestFocus();
            return;
        }

        // =========================
        // 🔵 SAFE PARSING SECTION
        // =========================

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);

        ItemDTO itemDTO = new ItemDTO(
                code,
                description,
                receivedDate,
                qtyOnHand,
                unitPrice,
                storage,
                color,
                emiNo,
                warranty
        );

        // =========================
        // 🟢 SAVE OR UPDATE
        // =========================

        try {

            if (btnSave.getText().equalsIgnoreCase("save")) {

                if (existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "Item already exists: " + code).show();
                    return;
                }

                itemBO.saveItem(itemDTO);

                new Alert(Alert.AlertType.INFORMATION, "Item Saved Successfully").show();

            } else {

                if (!existItem(code)) {
                    new Alert(Alert.AlertType.ERROR, "Item not found: " + code).show();
                    return;
                }

                itemBO.updateItem(itemDTO);

                new Alert(Alert.AlertType.INFORMATION, "Item Updated Successfully").show();
            }

            // =========================
            // 🔄 REFRESH UI
            // =========================

            loadAllItems();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database Error: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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


    public void btnUserAndPeopleOnAction(ActionEvent event) throws IOException {
        App.setRoot("userandpeople-form");

    }

    public void btnSuppliersGroupOnAction(ActionEvent event) {
    }

    public void btnWarrantyGroupOnAction(ActionEvent event) {
    }

    public void btnLogoutOnAction(ActionEvent event) throws IOException {

    }

    public void printStockReport(ActionEvent event) throws JRException, SQLException, ClassNotFoundException {

        Connection conn =DBconnection.getdBconnection().getConnection();

        //step ONE Jasperreport
       InputStream reportObject = getClass().getResourceAsStream("lk/ijse/layerdmobileshop/mobileshop/reports/stockReport.jrxml");

       //Complie jasperRepor
       JasperReport jr = JasperCompileManager.compileReport(reportObject);//throw jr Exception

        //JasperFill Manager
       JasperPrint jp = JasperFillManager.fillReport(jr,null,conn);//Parameter denawa Methenna Parameter thiyenawanam,ConnectionObject eka

        //
    }

}
