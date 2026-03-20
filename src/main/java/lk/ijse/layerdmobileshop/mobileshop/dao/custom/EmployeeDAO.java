package lk.ijse.layerdmobileshop.mobileshop.dao.custom;

import lk.ijse.layerdmobileshop.mobileshop.dao.CrudDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Employee;

import java.sql.SQLException;

public interface EmployeeDAO extends CrudDAO<Employee> {

     boolean isExist(String id) throws SQLException, ClassNotFoundException;

    String genarateNewId()throws SQLException,ClassNotFoundException;
}
