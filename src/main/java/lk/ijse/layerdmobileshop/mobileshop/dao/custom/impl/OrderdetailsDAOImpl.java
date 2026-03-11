package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
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
    public boolean save(OrderDetails detail) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO OrderDetails (oid, itemCode, unitPrice, qty) VALUES (?,?,?,?)";
        return CRUDUtil.execute(sql, detail.getOrderId(), detail.getItemCode(), detail.getUnitPrice(), detail.getQty());
    }

    @Override
    public boolean update(OrderDetails customerDTO) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public boolean isExist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public OrderDetails find(String id) throws SQLException, ClassNotFoundException {
        return null;
    }
}
