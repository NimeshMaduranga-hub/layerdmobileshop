package lk.ijse.layerdmobileshop.mobileshop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.PlaceOrderBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDetailDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;

import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import java.sql.Connection;
import java.util.HashMap;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class PlaceOrderForm {

    public JFXButton btnPlaceOrder;
    public TextField txtCustomerName;
    public TextField txtDescription;
    public TextField txtQtyOnHand;
    public JFXButton btnSave;
    public TableView<OrderDetails> tblOrderDetails;
    public TextField txtUnitPrice;
    public JFXComboBox<String> cmbCustomerId;
    public JFXComboBox<String> cmbItemCode;
    public TextField txtQty;
    public Label lblId;
    public Label lblDate;
    public Label lblTotal;
    private String orderId;
    private BigDecimal orderTotal = BigDecimal.ZERO;

    @FXML private TableColumn<OrderDetails, String> colItemCode;
    @FXML private TableColumn<OrderDetails, String> colDescription;
    @FXML private TableColumn<OrderDetails, Integer> colQty;
    @FXML private TableColumn<OrderDetails, BigDecimal> colUnitPrice;
    @FXML private TableColumn<OrderDetails, String> colStorage;
    @FXML private TableColumn<OrderDetails, String> colColor;
    @FXML private TableColumn<OrderDetails, String> colEmiNo;
    @FXML private TableColumn<OrderDetails, String> colWarranty;
    @FXML private TableColumn<OrderDetails, BigDecimal> colTotal;

    //  Dependency injection
    PlaceOrderBO orderBO = (PlaceOrderBO) BOFactory.getInstance().getBo(BOFactory.BOType.PLACE_ORDER);

    public void initialize() throws SQLException, ClassNotFoundException  {

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colStorage.setCellValueFactory(new PropertyValueFactory<>("storage"));
        colColor.setCellValueFactory(new PropertyValueFactory<>("color"));
        colEmiNo.setCellValueFactory(new PropertyValueFactory<>("emiNo"));
        colWarranty.setCellValueFactory(new PropertyValueFactory<>("warranty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetails, Button> lastCol = (TableColumn<OrderDetails, Button>) tblOrderDetails.getColumns().get(9);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrderDetails.getItems().remove(param.getValue());
                tblOrderDetails.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblId.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtDescription.setFocusTraversable(false);
        txtDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnSave.fire());
        txtQty.setEditable(false);
        btnSave.setDisable(true);

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/


                    if (!existCustomer(newValue + "")) {
                        //                 "There is no such customer associated with the id " + id
                        new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                    }

                    CustomerDTO customerDTO = orderBO.findCustomer(newValue);
                    txtCustomerName.setText(customerDTO.getName());

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });


        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnSave.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    // loose coupling
                    ItemDTO item = orderBO.findItem(newItemCode);

                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetails> optOrderDetail = tblOrderDetails.getItems().stream().filter(detail -> detail.getItemCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrderDetails.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getItemCode());
                btnSave.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnSave.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });

        loadAllCustomerIds();
        loadAllItemCodes();
    }



    private void loadAllItemCodes() {
        try {
            ArrayList<String> idList = orderBO.getAllItemId();

            for(String id : idList) {
                cmbItemCode.getItems().add(id);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIds() {
        try {
            ArrayList<String> idList = orderBO.getAllCusId();
            for(String id : idList) {
                cmbCustomerId.getItems().add(id);
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean existItem(String code) throws SQLException,ClassNotFoundException{
        return orderBO.itemIsExist(code);

    }

    private boolean existCustomer(String id) throws SQLException,ClassNotFoundException{
        return orderBO.customerIsExist(id);

    }

    private String generateNewOrderId() {
        try {
            return orderBO.genarateNewId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void calculateTotal() {

        orderTotal = BigDecimal.ZERO;

        for (OrderDetails detail : tblOrderDetails.getItems()) {
            orderTotal = orderTotal.add(detail.getTotal());
        }

        lblTotal.setText(String.format("Total: %.2f", orderTotal));
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerId.getSelectionModel().getSelectedItem() != null && !tblOrderDetails.getItems().isEmpty()));

    }

    public void txtQty_OnAction(ActionEvent event) {
    }

/*
    public void btnAdd_OnAction(ActionEvent event) {

        if (cmbItemCode.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Select item").show();
            return;
        }

        if (!txtQty.getText().matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            return;
        }

        int qty = Integer.parseInt(txtQty.getText());

        if (qty <= 0 || qty > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty range").show();
            return;
        }

        try {
            String itemCode = cmbItemCode.getValue();
            ItemDTO item = orderBO.findItem(itemCode);

            BigDecimal unitPrice = item.getUnitPrice().setScale(2);
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(qty));

            // check existing row
            OrderDetails existing = tblOrderDetails.getItems()
                    .stream()
                    .filter(d -> d.getItemCode().equals(itemCode))
                    .findFirst()
                    .orElse(null);

            if (existing != null) {

                existing.setQty(existing.getQty() + qty);

                existing.setTotal(
                        existing.getUnitPrice()
                                .multiply(BigDecimal.valueOf(existing.getQty()))
                );

            } else {

                tblOrderDetails.getItems().add(
                        new OrderDetails(
                                item.getCode(),
                                item.getDescription(),
                                qty,
                                unitPrice,
                                total,
                                item.getStorage(),
                                item.getColor(),
                                item.getEmiNo(),
                                item.getWarranty()
                        )
                );
            }

            tblOrderDetails.refresh();
            calculateTotal();

            txtQty.clear();
            cmbItemCode.getSelectionModel().clearSelection();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
*/


   /* public void btnAdd_OnAction(ActionEvent event) {

        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getValue();

        try {
            ItemDTO item = orderBO.findItem(itemCode);

            int qty = Integer.parseInt(txtQty.getText());
            BigDecimal unitPrice = item.getUnitPrice().setScale(2);
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(qty));

            boolean exists = tblOrderDetails.getItems()
                    .stream()
                    .anyMatch(d -> d.getItemCode().equals(itemCode));

            if (exists) {

                OrderDetails detail = tblOrderDetails.getItems()
                        .stream()
                        .filter(d -> d.getItemCode().equals(itemCode))
                        .findFirst()
                        .get();

                if (btnSave.getText().equalsIgnoreCase("Update")) {
                    detail.setQty(qty);
                } else {
                    detail.setQty(detail.getQty() + qty);
                }

                detail.setUnitPrice(unitPrice);
                detail.setTotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQty())));

            } else {

                tblOrderDetails.getItems().add(
                        new OrderDetails(
                                item.getCode(),
                                item.getDescription(),
                                qty,
                                unitPrice,
                                total,
                                item.getStorage(),
                                item.getColor(),
                                item.getEmiNo(),
                                item.getWarranty()
                        )
                );
            }

            tblOrderDetails.refresh();
            calculateTotal();
            enableOrDisablePlaceOrderButton();

            cmbItemCode.getSelectionModel().clearSelection();
            txtQty.clear();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }*/

    public void btnAdd_OnAction(ActionEvent event) {

        if (cmbItemCode.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Select item").show();
            return;
        }

        if (!txtQty.getText().matches("\\d+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            return;
        }

        int qty = Integer.parseInt(txtQty.getText());
        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());

        if (qty <= 0 || qty > qtyOnHand) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty range").show();
            return;
        }

        try {
            String itemCode = cmbItemCode.getValue();
            ItemDTO item = orderBO.findItem(itemCode);

            BigDecimal unitPrice = item.getUnitPrice().setScale(2);

            OrderDetails existing = tblOrderDetails.getItems()
                    .stream()
                    .filter(d -> d.getItemCode().equals(itemCode))
                    .findFirst()
                    .orElse(null);

            if (existing != null) {

                if (btnSave.getText().equalsIgnoreCase("Update")) {
                    // 🔥 REPLACE qty
                    existing.setQty(qty);
                } else {
                    // 🔥 ADD qty
                    existing.setQty(existing.getQty() + qty);
                }

                existing.setTotal(
                        existing.getUnitPrice()
                                .multiply(BigDecimal.valueOf(existing.getQty()))
                );

            } else {

                BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(qty));

                tblOrderDetails.getItems().add(
                        new OrderDetails(
                                item.getCode(),
                                item.getDescription(),
                                qty,
                                unitPrice,
                                total,
                                item.getStorage(),
                                item.getColor(),
                                item.getEmiNo(),
                                item.getWarranty()
                        )
                );
            }

            tblOrderDetails.refresh();
            calculateTotal();
            enableOrDisablePlaceOrderButton();

            // reset UI
            btnSave.setText("Add");
            cmbItemCode.setDisable(false);
            cmbItemCode.getSelectionModel().clearSelection();
            txtQty.clear();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void printInvoice(String orderId) {

        try {

            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/reports/Invoice_1.jrxml")
            );

            HashMap<String, Object> params = new HashMap<>();
            params.put("my_invoice_id", orderId);

            Connection connection = DBconnection.getdBconnection().getConnection();

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    params,
                    connection
            );

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Invoice load failed").show();
        }
    }
    public void btnPlaceOrder_OnAction(ActionEvent event) {

        try {

            List<OrderDetailDTO> detailDTOList =
                    tblOrderDetails.getItems()
                            .stream()
                            .map(tm -> new OrderDetailDTO(
                                    orderId,
                                    tm.getItemCode(),
                                    tm.getDescription(),
                                    tm.getQty(),
                                    tm.getUnitPrice(),
                                    tm.getTotal(),
                                    tm.getStorage(),
                                    tm.getColor(),
                                    tm.getEmiNo(),
                                    tm.getWarranty()
                            ))
                            .collect(Collectors.toList());

            boolean success = orderBO.saveOrder(
                    new OrderDTO(
                            orderId,
                            LocalDate.now(),
                            cmbCustomerId.getValue(),
                            txtCustomerName.getText(),
                            orderTotal
                    ),
                    detailDTOList
            );

            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Order placed successfully").show();
                printInvoice(orderId);
            } else {
                new Alert(Alert.AlertType.ERROR, "Order failed").show();
            }

            // reset UI
            orderId = generateNewOrderId();
            lblId.setText("Order ID: " + orderId);

            cmbCustomerId.getSelectionModel().clearSelection();
            cmbItemCode.getSelectionModel().clearSelection();
            tblOrderDetails.getItems().clear();
            txtQty.clear();
            calculateTotal();

        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
/*
    public void btnPlaceOrder_OnAction(ActionEvent event) {

        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerId.getValue(),
                tblOrderDetails.getItems().stream().map(tm -> new OrderDetailDTO(orderId, tm.getItemCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblId.setText("Order Id: " + orderId);
        cmbCustomerId.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrderDetails.getItems().clear();
        txtQty.clear();
        calculateTotal();

    }
*/
    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {

        /*Transaction*/

        try{
            return orderBO.saveOrder(new OrderDTO(orderId, orderDate, customerId), orderDetails);
        }catch(Exception e){
            e.printStackTrace();
        }

//        try{
//            Connection connection = DBConnection.getDbConnection().getConnection();
//            connection.setAutoCommit(false);
//
//            /*if order id already exist*/
//            if (orderBO.isExist(orderId)) {
//                new Alert(Alert.AlertType.ERROR, "Order ID already exist!").show();
//                return false;
//            }
//
//            boolean b1 = orderBO.saveOrder(new OrderDTO(orderId, orderDate, customerId), orderDetails);
//
//            if (!b1) {
//                connection.rollback();
//                connection.setAutoCommit(true);
//                return false;
//            }
//
//            for (OrderDetailDTO detail : orderDetails) {
//                boolean b2=orderBO.saveOrderDetails(detail);
//
//                if (!b2) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    return false;
//                }
////                //Search & Update Item
//                ItemDTO item = orderBO.findItem(detail.getItemCode());
//                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());
//
//                boolean b3=orderBO.updateItem(item);
//
//                if (!b3) {
//                    connection.rollback();
//                    connection.setAutoCommit(true);
//                    return false;
//                }
//            }
//
//            connection.commit();
//            connection.setAutoCommit(true);
//            return true;
//
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        return false;

    }

    public void btnItemOnAction(ActionEvent event) throws IOException {
        App.setRoot("manage-items-form");
    }

    public void btnUserAndPeopleOnAction(ActionEvent event) throws IOException {
        App.setRoot("userandpeople-form");
    }

    public void btnSuppliersGroupOnAction(ActionEvent event) {
    }

    public void btnWarrantyGroupOnAction(ActionEvent event) {
    }

    public void btnLogoutOnAction(ActionEvent event) throws IOException {
        App.setRoot("login-Form");

    }

}
