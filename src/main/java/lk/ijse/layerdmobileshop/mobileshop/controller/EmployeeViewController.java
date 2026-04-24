package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.EmployeeBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.EmployeeDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;
import lk.ijse.layerdmobileshop.mobileshop.entity.Employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EmployeeViewController {
    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtEmployeeName;

    @FXML
    private TextField txtEmployeeAddress;

    @FXML
    private TextField txtEmployeeMobile;

    @FXML
    private TextField txtEmployeeSalary;

    @FXML
    private Button btnAddEmployee;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnManageCustomer;

    @FXML
    private Button btnManageUser;


    @FXML
    private TableView<Employee> tblEmployee;
    @FXML
    private TableColumn<Employee, String> colId;

    @FXML
    private TableColumn<Employee, String> colName;

    @FXML
    private TableColumn<Employee, String> colAddress;

    @FXML
    private TableColumn<Employee, String> colMobile;

    @FXML
    private TableColumn<Employee, String> colSalary;

    @FXML
    private TableColumn<Employee, String> colrole;

    @FXML
    private ComboBox<String> cmbRole;
    @FXML
    private Button btnWorkLog;


    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBo(BOFactory.BOType.EMPLOYEE);

    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colrole.setCellValueFactory(new PropertyValueFactory<>("role"));

        cmbRole.setItems(FXCollections.observableArrayList(
                "ADMIN",
                "CASHIER",
                "TECHNICIAN"
        ));
        cmbRole.getSelectionModel().selectFirst();

        initUI();
        loadAllEmployee();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtEmployeeId.setText(newValue.getId());
                txtEmployeeName.setText(newValue.getName());
                txtEmployeeAddress.setText(newValue.getAddress());
                txtEmployeeMobile.setText(newValue.getMobile());
                txtEmployeeSalary.setText(newValue.getSalary());

                if(newValue.getRole() != null) {
                    cmbRole.setValue(newValue.getRole());
                } else {
                    cmbRole.getSelectionModel().clearSelection();
                }

                txtEmployeeId.setDisable(false);
                txtEmployeeName.setDisable(false);
                txtEmployeeAddress.setDisable(false);
                txtEmployeeMobile.setDisable(false);
                txtEmployeeSalary.setDisable(false);
            }
        });
        txtEmployeeAddress.setOnAction(event -> btnSave.fire());
        loadAllEmployee();

    }

    @FXML
    void btnWorkLogOnAction(ActionEvent event) throws IOException {
        App.setRoot("employeeattendancesalary-form");
    }

    private void loadAllEmployee() {
        tblEmployee.getItems().clear();
        /*Get all customers*/
        try {

            ArrayList<EmployeeDTO> empList = employeeBO.getAllEmployee();
            ObservableList<Employee> employeeTableList = FXCollections.observableArrayList();

            for (EmployeeDTO emp : empList) {
                Employee emTM = new Employee(emp.getId(), emp.getName(), emp.getAddress(), emp.getMobile(), emp.getSalary(),emp.getRole());
                employeeTableList.add(emTM);
            }

            tblEmployee.setItems(employeeTableList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    private void initUI() {
        txtEmployeeId.clear();
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeMobile.clear();
        txtEmployeeSalary.clear();
        txtEmployeeId.setDisable(true);
        txtEmployeeName.setDisable(true);
        txtEmployeeAddress.setDisable(true);
        txtEmployeeMobile.setDisable(true);
        txtEmployeeSalary.setDisable(true);
        txtEmployeeId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnAddNew_OnAction(ActionEvent event) {
        txtEmployeeId.setDisable(false);
        txtEmployeeName.setDisable(false);
        txtEmployeeAddress.setDisable(false);
        txtEmployeeMobile.setDisable(false);
        txtEmployeeSalary.setDisable(false);
        txtEmployeeId.setText(generateNewId());
        txtEmployeeName.clear();
        txtEmployeeAddress.clear();
        txtEmployeeMobile.clear();
        txtEmployeeSalary.clear();
        txtEmployeeName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblEmployee.getSelectionModel().clearSelection();
    }

    private String generateNewId() {
        try {
            return employeeBO.genarateNewId();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblEmployee.getItems().isEmpty()) {
            return "E00-001";
        } else {
            String id = getLastEmployeeId();
            int newEmployeeId = Integer.parseInt(id.replace("E", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        }
    }

    private String getLastEmployeeId() {

        List<Employee> tempEmployeeList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempEmployeeList);
        return tempEmployeeList.get(tempEmployeeList.size() - 1).getId();
    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        /*Delete Employee*/
        String id = tblEmployee.getSelectionModel().getSelectedItem().getId();
        try {
            if (!existEmployee(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such Employee associated with the id " + id).show();
                return;
            }
            employeeBO.deleteEmployee(id);
            tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
            tblEmployee.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the Employee " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        String id = txtEmployeeId.getText();
        String name = txtEmployeeName.getText();
        String address = txtEmployeeAddress.getText();
        String mobile = txtEmployeeMobile.getText();
        String salary = txtEmployeeSalary.getText();
        String role = cmbRole.getValue();


        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtEmployeeName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtEmployeeAddress.requestFocus();
            return;
        }
        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Employee*/
            try {
                if (existEmployee(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                employeeBO.saveEmployee(new EmployeeDTO(id, name, address, mobile, salary,role));
                tblEmployee.getItems().add(new Employee(id, name, address, mobile, salary,role));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the employee " + e.getMessage()).show();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            /*Update Employee*/

            try {
                if (!existEmployee(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + id).show();

                }

                employeeBO.updateEmployee(new EmployeeDTO(id,name,address,mobile,salary,role));
            }catch (SQLException e){
                new Alert(Alert.AlertType.ERROR, "Failed to update the Employee " + id + e.getMessage()).show();

            }catch (ClassNotFoundException e){
                e.printStackTrace();
            }

            Employee selectEmployee=tblEmployee.getSelectionModel().getSelectedItem();
            selectEmployee.setName(name);
            selectEmployee.setAddress(address);
            selectEmployee.setMobile(mobile);
            selectEmployee.setSalary(salary);
            selectEmployee.setRole(role);

            tblEmployee.refresh();
        }
        btnAddEmployee.fire();
    }

    boolean existEmployee(String id) throws SQLException, ClassNotFoundException {

        return employeeBO.isExist(id);

    }

    public void btnSuppliersGroupOnAction(ActionEvent event) throws IOException {
        System.out.println("btn warrenty");

    }

    public void btnOrdersAndItemsOnAction(ActionEvent event) throws IOException {
        App.setRoot("place-order-form");


    }


    public void btnWarrantyGroupOnAction(ActionEvent event) {
    }

    public void btnManageCustomerOnAction(ActionEvent event) throws IOException {
        App.setRoot("customer-view");
    }

    public void btnManageUserOnAction(ActionEvent event) {
    }

    public void handleLogout(ActionEvent event) throws IOException {
        App.setRoot("login-Form");
    }


}
