package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lk.ijse.layerdmobileshop.mobileshop.App;

import java.io.IOException;

public class DashboardcontrollerForm {

    @FXML
    private Button btnUserAndPeople;

    @FXML
    private Button btnOrdersAndItems;

    @FXML
    private Button btnSuppliersGroup;

    @FXML
    private Button btnWarrantyGroup;

    @FXML
    private Label lblUser;

    @FXML
    private Button btnLogout;

    @FXML
    void btnOrdersAndItemsOnAction(ActionEvent event) throws IOException {
        App.setRoot("place-order-form");
    }

    @FXML
    void btnSuppliersGroupOnAction(ActionEvent event) {
        System.out.println("btn supplier");
    }

    @FXML
    void btnUserAndPeopleOnAction(ActionEvent event) throws IOException {
        App.setRoot("userandpeople-form");
    }

    @FXML
    void btnWarrantyGroupOnAction(ActionEvent event) {
        System.out.println("btn warrenty");
    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

}
