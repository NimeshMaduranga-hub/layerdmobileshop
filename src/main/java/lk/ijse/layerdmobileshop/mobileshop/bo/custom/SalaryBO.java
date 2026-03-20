package lk.ijse.layerdmobileshop.mobileshop.bo.custom;

import lk.ijse.layerdmobileshop.mobileshop.bo.SuperBO;
import lk.ijse.layerdmobileshop.mobileshop.dto.SalaryDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SalaryBO extends SuperBO {
   

  public boolean saveSalary(SalaryDTO salaryDTO)throws SQLException,ClassNotFoundException;

    ArrayList<SalaryDTO> getSalaryByEmployeeId(String employeeId)throws SQLException,ClassNotFoundException;
}
