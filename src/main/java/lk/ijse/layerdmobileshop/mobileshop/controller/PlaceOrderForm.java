package lk.ijse.layerdmobileshop.mobileshop.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.PauseTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.PlaceOrderBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDetailDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;
import javafx.concurrent.Worker;
import javafx.print.PrinterJob;
import javafx.scene.web.WebEngine;


import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;
import lk.ijse.layerdmobileshop.mobileshop.util.PdfGenerator;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
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
    @FXML
    private WebView webViewInvoice;

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



    public void btnPlaceOrder_OnAction(ActionEvent e) {

        try {
            List<OrderDetailDTO> details = tblOrderDetails.getItems()
                    .stream()
                    .map(d -> new OrderDetailDTO(
                            orderId,
                            d.getItemCode(),
                            d.getDescription(),
                            d.getQty(),
                            d.getUnitPrice(),
                            d.getTotal(),
                            d.getStorage(),
                            d.getColor(),
                            d.getEmiNo(),
                            d.getWarranty()
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
                    details
            );

            if (success) {
                new Alert(Alert.AlertType.INFORMATION, "Order Success").showAndWait();
                String html = generateInvoiceHtml();

                try {
                    String fileName = "invoice_" + orderId + ".pdf";

                    PdfGenerator.generatePdf(html, fileName);

                    // optional auto open + print
                    Desktop.getDesktop().open(new File(fileName));

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                reset();

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private void reset() {
        tblOrderDetails.getItems().clear();
        orderId = generateNewOrderId();
        lblId.setText(orderId);
        calculateTotal();
    }

    private String generateOrderId() {
        try {
            return orderBO.genarateNewId();
        } catch (Exception e) {
            e.printStackTrace();
            return "OID-001";
        }
    }
    // ================= HTML INVOICE =================
    private String generateInvoiceHtml() {

        StringBuilder rows = new StringBuilder();

        for (OrderDetails d : tblOrderDetails.getItems()) {
            System.out.println("Unit Price Type : " + d.getUnitPrice().getClass());
            System.out.println("Total Type      : " + d.getTotal().getClass());
            System.out.println("EMI Type        : " + d.getEmiNo().getClass());
            System.out.println("Warranty Type   : " + d.getWarranty().getClass());

            rows.append("""
<tr>
    <td>%s</td>
    <td>%s</td>
    <td>%s / %s</td>
    <td style="text-align:right;">%d</td>
    <td style="text-align:right;">%.2f</td>
    <td style="text-align:right;">%s</td>
    <td style="text-align:right;">%s</td>
    <td style="text-align:right;">%.2f</td>
</tr>
""".formatted(
                    d.getItemCode(),
                    d.getDescription(),
                    d.getStorage(),
                    d.getColor(),
                    d.getQty(),
                    d.getUnitPrice(),
                    d.getEmiNo(),
                    d.getWarranty(),
                    d.getTotal()
            ));
        }

        URL logoUrl = getClass().getResource("/lk/ijse/layerdmobileshop/mobileshop/image/Artboard 6.png");

        if (logoUrl == null) {
            throw new RuntimeException("Logo image not found!");
        }

        String logo = logoUrl.toExternalForm();
        String orderId = this.orderId;
        String date = LocalDate.now().toString();
        String customer = cmbCustomerId.getValue() + " - " + txtCustomerName.getText();

        String html = """
                <!DOCTYPE html>
                       <html>
                       <head>
                       <meta charset="UTF-8"/>
                       <style>
                       @page {
                           size: A4;
                           margin: 0;
                       }
                       body {
                           margin: 0;
                           padding: 0;
                           font-family: Arial, sans-serif;
                       }
                       table {
                           border-collapse: collapse;
                       }
                       </style>
                       </head>
                       <body>
                
                       <table width="100%%" height="100%%" cellpadding="0" cellspacing="0" style="width:100%%; border:2px solid #ff9500; padding:20px;">
                       <tr>
                       <td style="padding:20px;">
                
                           <!-- OUTER PAGE TABLE: header / body / footer rows -->
                           <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                
                               <!-- HEADER ROW -->
                               <tr>
                                   <td style="border-bottom:4px solid #ff9500; padding-bottom:14px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                                           <tr>
                                               <td width="55%%" valign="top" style="text-align:left;">
                                                   <img src="%s" style="width:190px; max-height:70px;"/><br/>
                                                   <span style="font-size:11px; letter-spacing:0.5px; color:#7e4700; font-weight:bold; text-transform:uppercase;">Find the Best, Right Here</span>
                                               </td>
                                               <td width="45%%" valign="top" style="text-align:right;">
                                                   <span style="font-size:28px; font-weight:bold; color:#171717; letter-spacing:1px;">INVOICE</span><br/>
                                                   <span style="font-size:12px; color:#d4a017; font-weight:bold; letter-spacing:2px;">MOBILE SHOP</span>
                                               </td>
                                           </tr>
                                       </table>
                                   </td>
                               </tr>
                
                               <!-- INFO CARDS ROW -->
                               <tr>
                                   <td style="padding-top:18px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                                           <tr>
                                               <td width="33%%" style="background:#fafafa; border:1px solid #e2e8f0; padding:10px 14px;">
                                                   <span style="font-size:10px; text-transform:uppercase; letter-spacing:0.5px; color:#8a8a8a; font-weight:bold;">Order ID</span><br/>
                                                   <span style="font-size:14px; font-weight:bold; color:#171717;">%s</span>
                                               </td>
                                               <td width="2%%">&#160;</td>
                                               <td width="33%%" style="background:#fafafa; border:1px solid #e2e8f0; padding:10px 14px;">
                                                   <span style="font-size:10px; text-transform:uppercase; letter-spacing:0.5px; color:#8a8a8a; font-weight:bold;">Date</span><br/>
                                                   <span style="font-size:14px; font-weight:bold; color:#171717;">%s</span>
                                               </td>
                                               <td width="2%%">&#160;</td>
                                               <td width="30%%" style="background:#fafafa; border:1px solid #e2e8f0; padding:10px 14px;">
                                                   <span style="font-size:10px; text-transform:uppercase; letter-spacing:0.5px; color:#8a8a8a; font-weight:bold;">Customer</span><br/>
                                                   <span style="font-size:14px; font-weight:bold; color:#171717;">%s</span>
                                               </td>
                                           </tr>
                                       </table>
                                   </td>
                               </tr>
                
                               <!-- ITEMS TABLE -->
                               <tr>
                                   <td style="padding-top:22px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%; font-size:12px;">
                                           <tr>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:left;">Code</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:left;">Description</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:left;">Details</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:right;">Qty</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:right;">Unit Price</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:right;">EmiNo</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:right;">Warranty</th>
                                               <th style="background:#f4c430; color:#000000; padding:8px; text-align:right;">Total</th>
                                           </tr>
                                           %s
                                       </table>
                                   </td>
                               </tr>
                
                               <!-- TERMS AND CONDITIONS -->
                               <tr>
                                   <td style="padding-top:20px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                                           <tr>
                                               <td style="background:#fafafa; border:1px solid #e2e8f0; border-left:4px solid #ff9500; padding:10px 14px;">
                                                   <span style="font-size:11px; text-transform:uppercase; letter-spacing:0.5px; color:#171717; font-weight:bold;">Terms &amp; Conditions</span><br/>
                                                   <span style="font-size:10px; color:#525252; line-height:1.7;">
                                                       &#8226; One-month phone-to-phone warranty.<br/>
                                                       &#8226; One-year software warranty.<br/>
                                                       &#8226; No cash-back warranty.<br/>
                                                       &#8226; No warranty for display, Face ID, fingerprint, physical damage or water damage.
                                                   </span>
                                               </td>
                                           </tr>
                                       </table>
                                   </td>
                               </tr>
                
                               <!-- SIGNATURES -->
                               <tr>
                                   <td style="padding-top:36px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                                           <tr>
                                               <td width="45%%" style="border-top:1px solid #171717; padding-top:6px; text-align:center; font-size:11px; color:#525252; font-weight:bold;">
                                                   Customer Signature
                                               </td>
                                               <td width="10%%">&#160;</td>
                                               <td width="45%%" style="border-top:1px solid #171717; padding-top:6px; text-align:center; font-size:11px; color:#525252; font-weight:bold;">
                                                   Authorized Signature
                                               </td>
                                           </tr>
                                       </table>
                                   </td>
                               </tr>
                            
                
                                                    <!-- THANK YOU NOTE -->
                                                    <tr>
                                                        <td style="padding-top:14px; text-align:center;">
                                                            <span style="font-size:12px; color:#7e4700; font-weight:bold; letter-spacing:0.3px;">
                                                                Thank you for your purchase  Have a great shopping experience!
                                                            </span>
                                                        </td>
                                                    </tr>
                
                               <!-- SPACER TO PUSH FOOTER DOWN -->
                               <tr>
                                   <td style="height:100%%;">&#160;</td>
                               </tr>
                
                               <!-- FOOTER ROW -->
                               <tr>
                                   <td style="border-top:1px solid #e2e8f0; padding-top:12px;">
                                       <table width="100%%" cellpadding="0" cellspacing="0" style="width:100%%;">
                                           <tr>
                                               <td width="55%%" valign="bottom" style="font-size:11px; color:#525252; line-height:1.6;">
                                                   <span style="font-size:13px; color:#171717; font-weight:bold;">Pasi Mobile</span><br/>
                                                   Wariyapola, Sri Lanka<br/>
                                                   Tel: 077 475 7669
                                               </td>
                                               <td width="45%%" valign="bottom" style="text-align:right;">
                                                   <table cellpadding="0" cellspacing="0" align="right" style="background:#fff4e5; border:1px solid #ff9500; padding:10px 20px;">
                                                       <tr>
                                                           <td width="45%%" valign="bottom" style="text-align:left; padding-left:50px;">
                                                               <span style="font-size:11px; text-transform:uppercase; letter-spacing:1px; color:#d4a017; font-weight:bold;">Total Amount</span><br/>
                                                               <span style="font-size:22px; font-weight:bold; color:#171717;">%.2f</span>
                                                           </td>
                                                       </tr>
                                                   </table>
                                               </td>
                                           </tr>
                                       </table>
                                   </td>
                               </tr>
                               
                               
                
                           </table>
                
                       </td>
                       </tr>
                       </table>
                
                       </body>
                       </html>
""".formatted(
                logo,
                orderId,
                date,
                customer,
                rows.toString(),
                orderTotal
        );

        return html;
    }


    private void loadInvoiceToWebView(String html) {

        WebEngine engine = webViewInvoice.getEngine();
        engine.loadContent(html);

        PauseTransition pause = new PauseTransition(Duration.seconds(1));

        pause.setOnFinished(event -> {

            PrinterJob job = PrinterJob.createPrinterJob();

            if (job != null) {

                boolean ok = job.showPrintDialog(webViewInvoice.getScene().getWindow());

                if (ok) {
                    engine.print(job);
                    job.endJob();
                }
            }

        });

        pause.play();
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
