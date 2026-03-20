package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.AttendanceDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.SalaryDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Attendance;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AttendanceDAOImpl implements AttendanceDAO {

    @Override
    public ArrayList<Attendance> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM attendance");
        ArrayList<Attendance> attendances = new ArrayList<>();

        while (rst.next()) {
            Attendance attendance = new Attendance(
                    rst.getString("id"),
                    rst.getString("employeeid"),
                    rst.getDate("date").toLocalDate(),
                    rst.getTime("checkin").toLocalTime(),
                    rst.getTime("checkout").toLocalTime(),
                    rst.getString("status")
            );
            attendances.add(attendance);
        }
        return attendances;
    }

    @Override
    public boolean save(Attendance attendance) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "INSERT INTO attendance (employeeid, date, checkin, checkout, status) VALUES (?,?,?,?,?)",
                attendance.getEmployeeId(),
                attendance.getDate(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getStatus()
        );
    }

    @Override
    public boolean update(Attendance attendance) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "UPDATE attendance SET employeeid=?, date=?, checkin=?, checkout=?, status=? WHERE id=?",
                attendance.getEmployeeId(),
                attendance.getDate(),
                attendance.getCheckIn(),
                attendance.getCheckOut(),
                attendance.getStatus(),
                attendance.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("DELETE FROM attendance WHERE id=?", id);
    }

    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT id FROM attendance WHERE id=?", id);
        return rst.next();
    }

    @Override
    public Attendance find(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM attendance WHERE id=?", id);
        if (rst.next()) {
            return new Attendance(
                    rst.getString("id"),
                    rst.getString("employeeid"),
                    rst.getDate("date").toLocalDate(),
                    rst.getTime("checkin").toLocalTime(),
                    rst.getTime("checkout").toLocalTime(),
                    rst.getString("status")
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT id FROM attendance");
        ArrayList<String> idArray = new ArrayList<>();
        while (rst.next()) {
            idArray.add(rst.getString("id"));
        }
        return idArray;
    }

    @Override
    public ArrayList<Attendance> getAllEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Attendance> getAllEmployeeid(String employeeId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM attendance WHERE employeeid = ?";
        ResultSet rst = CRUDUtil.execute(sql, employeeId);
        ArrayList<Attendance> list = new ArrayList<>();
        while (rst.next()) {
            list.add(new Attendance(
                    rst.getString("id"),
                    rst.getString("employeeid"),
                    rst.getDate("date") != null ? rst.getDate("date").toLocalDate() : null,
                    rst.getTime("checkin") != null ? rst.getTime("checkin").toLocalTime() : null,
                    rst.getTime("checkout") != null ? rst.getTime("checkout").toLocalTime() : null,
                    rst.getString("status")
            ));
        }
        return list;
    }

    @Override
    public boolean saveSalary(String employeeId,
                              BigDecimal salary,
                              LocalDate payDate,
                              String payForMonth,
                              String method,
                              String remarks) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO salaryhistory (employee_id, salary, pay_date, pay_for_month, method, remarks) VALUES (?,?,?,?,?,?)";

        return CRUDUtil.execute(
                sql,
                employeeId,
                salary,
                payDate,
                payForMonth,
                method,
                remarks
        );
    }

    public ArrayList<SalaryDTO> getSalaryByEmployeeId(String empId) throws Exception {

        ResultSet rst = CRUDUtil.execute(
                "SELECT * FROM salaryhistory WHERE employee_id = ?", empId
        );

        ArrayList<SalaryDTO> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new SalaryDTO(
                    rst.getString("employee_id"),
                    rst.getBigDecimal("salary"),
                    rst.getDate("pay_date").toLocalDate()
            ));
        }

        return list;
    }


}