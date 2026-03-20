package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.EmployeeBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.EmployeeDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.EmployeeDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;
import lk.ijse.layerdmobileshop.mobileshop.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public boolean saveEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.save(new Employee(employeeDTO.getId(), employeeDTO.getName(), employeeDTO.getAddress(), employeeDTO.getMobile(), employeeDTO.getSalary(),employeeDTO.getRole()));
    }

    @Override
    public boolean deleteEmployee(String name) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(name);
    }

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employees = employeeDAO.getAll();
        ArrayList<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee emp : employees) {
            employeeDTOList.add(new EmployeeDTO(emp.getId(), emp.getName(), emp.getAddress(), emp.getMobile(), emp.getSalary(),emp.getRole()));
        }
        return employeeDTOList;
    }
    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.isExist(id);
    }

    public boolean updateEmployee(EmployeeDTO employeeDTO)throws SQLException,ClassNotFoundException{
        Employee employee=new Employee(employeeDTO.getId(),employeeDTO.getName(),employeeDTO.getAddress(),employeeDTO.getMobile(),employeeDTO.getSalary(),employeeDTO.getRole());
        return  employeeDAO.update(employee);
    }


    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        return employeeDAO.genarateNewId();
    }

}
