package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.CustomerBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.CustomerDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    /*CustomerDAOImpl customerDAO=new CustomerDAOImpl(); // Tigth cuple */

    /*CustomerDAO customerDAO=new CustomerDAOImpl(); //loose cuple*/

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.CUSTOMER);

    @Override
    public boolean saveCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(), customerDTO.getMobile()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customer = customerDAO.getAll();
        ArrayList<CustomerDTO> cusDTOList = new ArrayList<>();

        for (Customer cus : customer) {
            cusDTOList.add(new CustomerDTO(cus.getId(), cus.getName(), cus.getAddress(), cus.getMobile()));
        }

        return cusDTOList;

    }

    public boolean updateCustomer(CustomerDTO customerDTO) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(customerDTO.getId(), customerDTO.getName(), customerDTO.getAddress(),customerDTO.getMobile());
        return customerDAO.update(customer);
    }
    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.isExist(id);
    }
    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        return customerDAO.genarateNewId();
    }
}
