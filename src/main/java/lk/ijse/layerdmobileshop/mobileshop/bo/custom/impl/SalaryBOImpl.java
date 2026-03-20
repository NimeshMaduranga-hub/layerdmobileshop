package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.SalaryBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.SalaryDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.SalaryDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Salary;

import java.sql.SQLException;
import java.util.ArrayList;

public class SalaryBOImpl implements SalaryBO {

    private final SalaryDAO salaryDAO = (SalaryDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.SALARY);

    @Override
    public boolean saveSalary(SalaryDTO dto) throws SQLException, ClassNotFoundException {

        Salary salary = new Salary(
                null, // ID auto-increment in DB
                dto.getEmployeeId(),
                dto.getSalary(),
                dto.getPayDate()
        );

        return salaryDAO.save(salary);
    }

    @Override
    public ArrayList<SalaryDTO> getSalaryByEmployeeId(String employeeId)
            throws SQLException, ClassNotFoundException {

        ArrayList<Salary> allSalaries = salaryDAO.getAll();
        ArrayList<SalaryDTO> dtoList = new ArrayList<>();

        for (Salary s : allSalaries) {
            if (s.getEmployeeId() != null && s.getEmployeeId().equals(employeeId)) {
                dtoList.add(new SalaryDTO(
                        s.getEmployeeId(),
                        s.getSalary(),
                        s.getPayDate()
                ));
            }
        }

        return dtoList;
    }
}