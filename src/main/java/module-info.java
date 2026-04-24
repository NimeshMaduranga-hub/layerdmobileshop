module lk.ijse.layerdmobileshop.mobileshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.jdi;
    requires static lombok;
    requires jakarta.persistence;
    requires com.jfoenix;
    requires jasperreports;

    opens lk.ijse.layerdmobileshop.mobileshop to javafx.fxml;
    opens lk.ijse.layerdmobileshop.mobileshop.dto to javafx.base;
    exports lk.ijse.layerdmobileshop.mobileshop;
    exports lk.ijse.layerdmobileshop.mobileshop.controller;
    opens lk.ijse.layerdmobileshop.mobileshop.controller to javafx.fxml;
    opens lk.ijse.layerdmobileshop.mobileshop.entity to javafx.base;


}