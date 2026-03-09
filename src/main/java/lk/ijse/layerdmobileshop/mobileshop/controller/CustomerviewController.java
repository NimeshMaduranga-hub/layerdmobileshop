package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomerviewController {

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtCustomerName;

    @FXML
    private TextField txtCustomerAddress;

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        System.out.println(txtCustomerId.getText());
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        System.out.println(txtCustomerName.getText());
        System.out.println(txtCustomerAddress.getText());
    }

}
