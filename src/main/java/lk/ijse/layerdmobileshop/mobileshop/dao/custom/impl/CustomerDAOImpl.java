package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.CustomerDAO;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll()throws SQLException,ClassNotFoundException {
        ResultSet rst=CRUDUtil.execute("SELECT * FROM Customer");
        ArrayList<Customer> customers=new ArrayList<Customer>();
        while (rst.next()) {
            String id=rst.getString("id");
            String name=rst.getString("name");
            String address=rst.getString("address");
            String mobile=rst.getString("mobile");

            Customer customer=new Customer(id,name,address,mobile);
            customers.add(customer);
        }
        return customers;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "INSERT INTO Customer (id,name,address,mobile) VALUES (?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getMobile()
        );
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {
        return CRUDUtil.execute(
                "UPDATE Customer SET name=?, address=?, mobile=? WHERE id=?",
                entity.getId(),
                entity.getName(),
                entity.getAddress(),
                entity.getMobile()
        );
    }

    @Override
    public boolean delete(String id)throws SQLException,ClassNotFoundException {
        return CRUDUtil.execute("DELETE FROM Customer WHERE id=?",id);
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {

        ResultSet rst = CRUDUtil.execute("SELECT id FROM Customer ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }
    }

    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT id FROM Customer WHERE id=?", id);
        return rst.next();
    }

    @Override
    public Customer find(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer WHERE id=?", id);
        Customer customer = null;

        if (rst.next()){
            customer = new Customer(id + "", rst.getString("name"), rst.getString("address"),rst.getString("mobile"));
        }

        return customer;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CRUDUtil.execute("SELECT * FROM Customer");

        ArrayList<String> idArray = new ArrayList<>();

        while(rst.next()){
            idArray.add(rst.getString("id"));
        }

        return idArray;    }


}
