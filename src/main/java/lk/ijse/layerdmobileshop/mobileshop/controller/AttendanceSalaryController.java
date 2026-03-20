package lk.ijse.layerdmobileshop.mobileshop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.layerdmobileshop.mobileshop.App;
import lk.ijse.layerdmobileshop.mobileshop.bo.BOFactory;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.AttendanceBO;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.EmployeeBO;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.SalaryBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.AttendanceDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.EmployeeDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.SalaryDTO;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AttendanceSalaryController {

    @FXML
    private ComboBox<String> cmbEmployee;

    @FXML
    private TableView<AttendanceDTO> tblAttendanceHistory;

    @FXML
    private TableColumn<AttendanceDTO, LocalDate> colAttDate;

    @FXML
    private TableColumn<AttendanceDTO, String> colCheckIn;

    @FXML
    private TableColumn<AttendanceDTO, String> colCheckOut;

    @FXML
    private TableColumn<AttendanceDTO, String> colStatus;

    @FXML
    private TableView<SalaryDTO> tblSalaryHistory;

    @FXML
    private TableColumn<SalaryDTO, String> colEmpId;

    @FXML
    private TableColumn<SalaryDTO, BigDecimal> colSalaryAmountHist;

    @FXML
    private TableColumn<SalaryDTO, LocalDate> colSalaryDate;


    @FXML
    private DatePicker dpMonth;

    @FXML
    private DatePicker dpAttendanceDate;

    @FXML
    private TextField txtCheckIn;

    @FXML
    private TextField txtCheckOut;

    @FXML
    private TextField txtSalaryAmount;

    @FXML
    private DatePicker dpSalaryDate;

    @FXML
    private ComboBox<String> cmbStatus;

    @FXML
    private Button btnSave, btnClear, btnBack;

    private final ObservableList<AttendanceDTO> attendanceList = FXCollections.observableArrayList();
    private final ObservableList<SalaryDTO> salaryList = FXCollections.observableArrayList();

    private final Map<String,String> employeeMap = new HashMap<>();

    private final EmployeeBO employeeBO =
            (EmployeeBO) BOFactory.getInstance().getBo(BOFactory.BOType.EMPLOYEE);

    private final AttendanceBO attendanceBO =
            (AttendanceBO) BOFactory.getInstance().getBo(BOFactory.BOType.ATTENDANCE);

    private final SalaryBO salaryBO =
            (SalaryBO) BOFactory.getInstance().getBo(BOFactory.BOType.SALARY);


    @FXML
    public void initialize() {

        colAttDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colCheckIn.setCellValueFactory(new PropertyValueFactory<>("checkIn"));
        colCheckOut.setCellValueFactory(new PropertyValueFactory<>("checkOut"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        colEmpId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        colSalaryAmountHist.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colSalaryDate.setCellValueFactory(new PropertyValueFactory<>("payDate"));



        tblAttendanceHistory.setItems(attendanceList);
        tblSalaryHistory.setItems(salaryList);

        txtCheckIn.setText("00:00");
        txtCheckOut.setText("00:00");

        cmbStatus.setItems(FXCollections.observableArrayList(
                "Present","Absent","Half Day","Leave"
        ));

        loadEmployeeNames();

        cmbEmployee.setOnAction(e -> {
            String name = cmbEmployee.getValue();
            if(name != null){
                String empId = employeeMap.get(name);

                loadAttendanceHistory(empId);
                loadSalaryHistory(empId);
            }
        });

        colStatus.setCellFactory(column -> new TableCell<AttendanceDTO, String>() {
            @Override
            protected void updateItem(String status, boolean empty) {
                super.updateItem(status, empty);
                if (empty || status == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(status);
                    switch (status) {
                        case "Present" -> setStyle("-fx-background-color: lightblue; -fx-alignment: CENTER;");
                        case "Absent" -> setStyle("-fx-background-color: salmon; -fx-alignment: CENTER;");
                        case "Half Day" -> setStyle("-fx-background-color: yellow; -fx-alignment: CENTER;");
                        case "Leave" -> setStyle("-fx-background-color: red; -fx-alignment: CENTER;");
                        default -> setStyle("");
                    }
                }
            }
        });
    }


    private void loadSalaryHistory(String employeeId){

        salaryList.clear();

        try{
            ArrayList<SalaryDTO> list = salaryBO.getSalaryByEmployeeId(employeeId);

            if(list != null){
                salaryList.addAll(list);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void loadEmployeeNames(){
        try{

            ArrayList<EmployeeDTO> employees = employeeBO.getAllEmployee();

            ObservableList<String> names = FXCollections.observableArrayList();

            for(EmployeeDTO e : employees){
                names.add(e.getName());
                employeeMap.put(e.getName(), e.getId());
            }

            cmbEmployee.setItems(names);

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    private void loadAttendanceHistory(String employeeId){

        attendanceList.clear();

        try{

            ArrayList<AttendanceDTO> list =
                    attendanceBO.getAttendanceByEmployeeId(employeeId);

            if(list != null){
                attendanceList.addAll(list);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void btnSaveOnAction(ActionEvent event){

        String selectedName = cmbEmployee.getValue();

        if(selectedName == null){
            new Alert(Alert.AlertType.ERROR,"Select Employee").show();
            return;
        }

        String employeeId = employeeMap.get(selectedName);

        LocalDate date = dpAttendanceDate.getValue();
        String checkIn = txtCheckIn.getText();
        String checkOut = txtCheckOut.getText();
        String status = cmbStatus.getValue();

        if(date == null || checkIn.isEmpty() || checkOut.isEmpty() || status == null){
            new Alert(Alert.AlertType.ERROR,"Fill Attendance Fields").show();
            return;
        }

        try{

            boolean isSaved = attendanceBO.saveAttendance(
                    new AttendanceDTO(employeeId,date,checkIn,checkOut,status)
            );

            if(!isSaved){
                new Alert(Alert.AlertType.ERROR,"Attendance Save Failed").show();
                return;
            }


            String salaryText = txtSalaryAmount.getText();
            LocalDate salaryDate = dpSalaryDate.getValue();

            if(salaryText != null && !salaryText.isEmpty() && salaryDate != null){

                BigDecimal salary = new BigDecimal(salaryText);

                SalaryDTO salaryDTO = new SalaryDTO(
                        employeeId,
                        salary,
                        salaryDate
                );

                salaryBO.saveSalary(salaryDTO);
            }

            new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully").show();

            loadAttendanceHistory(employeeId);
            loadSalaryHistory(employeeId);  // 🔥 REFRESH TABLE

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    @FXML
    void btnClearOnAction(ActionEvent event){

        cmbEmployee.getSelectionModel().clearSelection();
        dpAttendanceDate.setValue(null);
        txtCheckIn.clear();
        txtCheckOut.clear();
        txtSalaryAmount.clear();
        dpSalaryDate.setValue(null);
        cmbStatus.getSelectionModel().clearSelection();

        attendanceList.clear();
        salaryList.clear();
    }
    @FXML
    private void filterByMonth(ActionEvent event) {
        if (cmbEmployee.getValue() == null) {
            new Alert(Alert.AlertType.ERROR, "Select Employee").show();
            return;
        }

        LocalDate selected = dpMonth.getValue();
        if (selected == null) {
            new Alert(Alert.AlertType.ERROR, "Select Month").show();
            return;
        }

        int month = selected.getMonthValue();
        int year = selected.getYear();

        ObservableList<AttendanceDTO> filteredAttendance = FXCollections.observableArrayList();
        for (AttendanceDTO a : attendanceList) {
            if (a.getDate().getMonthValue() == month && a.getDate().getYear() == year) {
                filteredAttendance.add(a);
            }
        }
        tblAttendanceHistory.setItems(filteredAttendance);

        ObservableList<SalaryDTO> filteredSalary = FXCollections.observableArrayList();
        for (SalaryDTO s : salaryList) {
            if (s.getPayDate().getMonthValue() == month && s.getPayDate().getYear() == year) {
                filteredSalary.add(s);
            }
        }
        tblSalaryHistory.setItems(filteredSalary);
    }


    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        App.setRoot("employee-view");
    }
}