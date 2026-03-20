package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.EmployeeDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;
import lk.ijse.layerdmobileshop.mobileshop.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Employee");

        ArrayList<Employee> employees = new ArrayList<Employee>();

        while (rst.next()) {
            String id = rst.getString("id");
            String name = rst.getString("name");
            String address = rst.getString("address");
            String mobile = rst.getString("mobile");
            String salary = rst.getString("salary");
            String role = rst.getString("role");

            Employee employee = new Employee(id, name, address, mobile, salary,role);
            employees.add(employee);
        }
        return employees;
    }

    @Override
    public boolean save(Employee entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "INSERT INTO Employee (id,name,address,mobile,salary,role) VALUES (?,?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getMobile(),
                entity.getSalary(),
                entity.getRole()
        );
    }

    @Override
    public boolean update(Employee entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "UPDATE Employee SET name=?, address=?, mobile=?, salary=?,role=? WHERE id=?",
                entity.getName(),
                entity.getAddress(),
                entity.getMobile(),
                entity.getSalary(),
                entity.getRole(),
                entity.getId()
        );
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute("DELETE FROM Employee WHERE id=?", id);
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT id FROM Employee ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newEmployeeId = Integer.parseInt(id.replace("E00-", "")) + 1;
            return String.format("E00-%03d", newEmployeeId);
        } else {
            return "E00-001";
        }
    }

    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT id FROM Employee WHERE id=?", id);
        return rst.next();
    }

    @Override
    public Employee find(String id) throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT * FROM Employee WHERE id=?", id);
        Employee employee = null;

        if (rst.next()) {
            employee = new Employee(
                    rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("address"),
                    rst.getString("mobile"),
                    rst.getString("salary"),
                    rst.getString("role")
            );        }

        return employee;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CRUDUtil.execute("SELECT * FROM Employee");
        ArrayList<String> idArray=new ArrayList<>();
        while (rst.next()){
            idArray.add(rst.getString("id"));
        }
        return idArray;
    }

}
