package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.LoginBO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.io.IOException;

public class LoginControllerForm {

    @FXML
    private TextField txtUserName;

    @FXML
    private PasswordField txtPassword;

    LoginBO loginBO =
            (LoginBO) BOFactory.getInstance().getBo(BOFactory.BOType.LOGIN);

    @FXML
    void btnLoginOnAction(ActionEvent event) {

        String username = txtUserName.getText();
        String password = txtPassword.getText();

        try {

            User user = loginBO.login(username, password);


            if (user != null) {
                new Alert(Alert.AlertType.INFORMATION, "Login Success").show();
                App.setRoot("loading-form");

                PauseTransition delay = new PauseTransition(Duration.seconds(3));
                delay.setOnFinished(e -> {
                    try {
                        App.setRoot("dashboard-form");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                delay.play();

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid Username or Password").show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
