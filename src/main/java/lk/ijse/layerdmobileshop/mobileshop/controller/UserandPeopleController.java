package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import lk.ijse.layerdmobileshop.mobileshop.App;

import java.io.IOException;

public class UserandPeopleController {

    @FXML
    private Canvas dashbordCanvas;

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
    public void initialize() {
        GraphicsContext gc = dashbordCanvas.getGraphicsContext2D();

        int particleCount = 40;

        double[] x = new double[particleCount];
        double[] y = new double[particleCount];
        double[] dx = new double[particleCount];
        double[] dy = new double[particleCount];

        // Initialize particles
        for (int i = 0; i < particleCount; i++) {
            x[i] = Math.random() * dashbordCanvas.getWidth();
            y[i] = Math.random() * dashbordCanvas.getHeight();
            dx[i] = (Math.random() - 0.5) * 1.5;
            dy[i] = (Math.random() - 0.5) * 1.5;
        }

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                // Clear canvas
                gc.clearRect(0, 0, dashbordCanvas.getWidth(), dashbordCanvas.getHeight());

                // Dark gradient background
                gc.setFill(Color.web("#0f2027"));
                gc.fillRect(0, 0, dashbordCanvas.getWidth(), dashbordCanvas.getHeight());

                // Draw connections
                for (int i = 0; i < particleCount; i++) {
                    for (int j = i + 1; j < particleCount; j++) {

                        double dist = Math.hypot(x[i] - x[j], y[i] - y[j]);

                        if (dist < 120) {
                            double opacity = 1.0 - (dist / 120);
                            gc.setStroke(Color.rgb(0, 255, 255, opacity));
                            gc.strokeLine(x[i], y[i], x[j], y[j]);
                        }
                    }
                }

                // Draw particles
                for (int i = 0; i < particleCount; i++) {

                    gc.setFill(Color.CYAN);
                    gc.fillOval(x[i], y[i], 4, 4);

                    // Move
                    x[i] += dx[i];
                    y[i] += dy[i];

                    // Bounce edges
                    if (x[i] <= 0 || x[i] >= dashbordCanvas.getWidth()) dx[i] *= -1;
                    if (y[i] <= 0 || y[i] >= dashbordCanvas.getHeight()) dy[i] *= -1;
                }
            }
        };

        timer.start();
    }

    @FXML
    void btnManageCustomerOnAction(ActionEvent event) throws IOException {
        App.setRoot("customer-view");
    }

    @FXML
    void btnManageEmployeeOnAction(ActionEvent event) throws IOException {
        App.setRoot("employee-view");
    }

    @FXML
    void btnManageUserOnAction(ActionEvent event) throws IOException {
        App.setRoot("usermanage-view");
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