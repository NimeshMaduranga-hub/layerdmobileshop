package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.AttendanceDTO;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AttendanceBO extends SuperBO {

    boolean saveAttendance(AttendanceDTO dto) throws SQLException, ClassNotFoundException;

    ArrayList<AttendanceDTO> getAllAttendance()throws SQLException,ClassNotFoundException;

    ArrayList<AttendanceDTO> getAttendanceByEmployeeId(String employeeId)throws SQLException,ClassNotFoundException;

    void saveSalary(String employeeId, BigDecimal salary, LocalDate salaryDate, String string, String cash, String salaryPayment)throws SQLException, ClassNotFoundException;
}
