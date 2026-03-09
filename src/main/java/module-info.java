module lk.ijse.layerdmobileshop.mobileshop {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires jdk.jdi;
    requires static lombok;
    requires jakarta.persistence;

    opens lk.ijse.layerdmobileshop.mobileshop to javafx.fxml;
    exports lk.ijse.layerdmobileshop.mobileshop;
    exports lk.ijse.layerdmobileshop.mobileshop.controller;
    opens lk.ijse.layerdmobileshop.mobileshop.controller to javafx.fxml;
}