package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Attendance;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public interface AttendanceDAO extends CrudDAO<Attendance> {
     ArrayList<Attendance> getAllEmployee(String employeeId) throws SQLException, ClassNotFoundException;

    ArrayList<Attendance> getAllEmployeeid(String employeeId) throws SQLException, ClassNotFoundException;

    boolean saveSalary(String employeeId,
                       BigDecimal salary,
                       LocalDate payDate,
                       String payForMonth,
                       String method,
                       String remarks) throws SQLException, ClassNotFoundException;}
