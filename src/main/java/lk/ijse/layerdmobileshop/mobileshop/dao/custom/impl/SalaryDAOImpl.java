package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.SalaryDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Salary;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryDAOImpl implements SalaryDAO {
    @Override
    public ArrayList<Salary> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Salary");

        ArrayList<Salary> salaries = new ArrayList<>();

        while (rst.next()) {

            Salary salary = new Salary(
                    rst.getString("id"),
                    rst.getString("employee_id"),
                    rst.getBigDecimal("salary"),
                    rst.getDate("pay_date").toLocalDate()
            );

            salaries.add(salary);
        }

        return salaries;
    }

    @Override
    public boolean save(Salary salary) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "INSERT INTO Salary(employee_id, salary, pay_date) VALUES (?, ?, ?)",
                salary.getEmployeeId(),
                salary.getSalary(),
                salary.getPayDate()
        );
    }

    @Override
    public boolean update(Salary entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Salary find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
