package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.ijse.layerdmobileshop.mobileshop.App;

import java.io.IOException;

public class UserandPeopleController {

    @FXML
    private Label lblUser;

    @FXML
    private Button btnUserAndPeople;

    @FXML
    private Button btnOrdersAndItems;

    @FXML
    private Button btnSuppliersGroup;

    @FXML
    private Button btnWarrantyGroup;

    @FXML
    private Button btnManageUser;

    @FXML
    private Button btnManageCustomer;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnManageEmployee;

    @FXML
    void btnManageCustomerOnAction(ActionEvent event) throws IOException {
        App.setRoot("customer-view");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        App.setRoot("employee-view");
    }

    @FXML
    void btnManageUserOnAction(ActionEvent event) {

    }

    @FXML
    void btnOrdersAndItemsOnAction(ActionEvent event) throws IOException {
        App.setRoot("place-order-form");
    }

    @FXML
    void btnSuppliersGroupOnAction(ActionEvent event) {

    }

    @FXML
    void btnUserAndPeopleOnAction(ActionEvent event) {

    }

    @FXML
    void btnWarrantyGroupOnAction(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        App.setRoot("login-Form");

    }

}
