package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.EmployeeBO;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.UserBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.EmployeeDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.UserDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UsermanageViewController {

    @FXML
    private ComboBox<String> cmbEmployee;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TableView<User> tblUsers;

    @FXML
    private TableColumn<User, String> colUsername;

    @FXML
    private TableColumn<User, String> colRole;

    private User selectedUser = null;

    UserBO userBO =
            (UserBO) BOFactory.getInstance().getBo(BOFactory.BOType.USER);

    public void initialize() {

        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadTable();
        loadEmployees();
    }

    // ================= LOAD TABLE =================
    private void loadTable() {

        tblUsers.getItems().clear();

        try {
            ArrayList<UserDTO> userList = userBO.getAllUser();

            ObservableList<User> tmList = FXCollections.observableArrayList();

            for (UserDTO dto : userList) {
                tmList.add(new User(
                        dto.getUsername(),
                        dto.getRole()
                ));
            }

            tblUsers.setItems(tmList);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void loadEmployees() {

        try {
            EmployeeBO employeeBO =
                    (EmployeeBO) BOFactory.getInstance().getBo(BOFactory.BOType.EMPLOYEE);

            ArrayList<EmployeeDTO> list = employeeBO.getAllEmployee();

            ObservableList<String> empList = FXCollections.observableArrayList();

            for (EmployeeDTO dto : list) {
                empList.add(dto.getName());   // 👈 ONLY NAME
            }

            cmbEmployee.setItems(empList);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnSaveUserOnAction(ActionEvent event) {

        try {

            UserDTO dto = new UserDTO(
                    null,
                    txtUsername.getText(),
                    txtPassword.getText(),
                    "CASHIER"
            );

            if (selectedUser == null) {

                userBO.saveUser(dto);
                new Alert(Alert.AlertType.INFORMATION, "User Saved").show();

            } else {

                userBO.updateUser(dto);
                new Alert(Alert.AlertType.INFORMATION, "User Updated").show();
            }

            loadTable();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void clearFields() {

        txtUsername.clear();
        txtPassword.clear();
        cmbEmployee.setValue(null);

        selectedUser = null; // reset mode
    }

    @FXML
    void btnCancelOnAction(ActionEvent event) {

        User selected = tblUsers.getSelectionModel().getSelectedItem();

        if (selected == null) {
            new Alert(Alert.AlertType.WARNING, "Select user first").show();
            return;
        }

        try {
            userBO.deleteUser(selected.getUsername());

            new Alert(Alert.AlertType.INFORMATION, "User Deleted").show();

            loadTable();
            clearFields();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cmbEmployeeOnAction(ActionEvent event) {
        String selectedName = cmbEmployee.getValue();

        if (selectedName == null) return;

        String username = selectedName.toLowerCase().replace(" ", "");

        txtUsername.setText(username);
    }
    @FXML
    void tblUsersOnMouseClicked() {

        User selected = tblUsers.getSelectionModel().getSelectedItem();

        if (selected == null) return;

        txtUsername.setText(selected.getUsername());
        cmbEmployee.setValue(selected.getEmpId());
    }


    public void btnWarrantyGroupOnAction(ActionEvent event) {
    }

    public void btnSuppliersGroupOnAction(ActionEvent event) {
    }

    public void btnOrdersAndItemsOnAction(ActionEvent event) {
    }

    public void btnManageEmployeeOnAction(ActionEvent event) {
    }

    public void handleLogout(ActionEvent event) {
    }

    public void btnManageCustomerOnAction(ActionEvent event) {
    }



}