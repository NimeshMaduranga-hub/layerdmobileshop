package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String name) throws SQLException, ClassNotFoundException;

    ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;

    boolean isExist(String id) throws SQLException, ClassNotFoundException;

    String genarateNewId()throws SQLException,ClassNotFoundException;

    boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;

}
