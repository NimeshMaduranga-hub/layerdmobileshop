package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.custom.OrderDetailsDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderdetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDetails enity) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(OrderDetails entity) throws SQLException, ClassNotFoundException {
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
    public OrderDetails find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
