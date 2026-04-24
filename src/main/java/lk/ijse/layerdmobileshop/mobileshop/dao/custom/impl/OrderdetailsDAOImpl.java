package lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.dao.CRUDUtil;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.OrderDetailsDAO;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderdetailsDAOImpl implements OrderDetailsDAO {

    @Override
    public ArrayList<OrderDetails> getAll() throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM OrderDetails";
        ResultSet rst = CRUDUtil.execute(sql);

        ArrayList<OrderDetails> list = new ArrayList<>();

        while (rst.next()) {
            list.add(new OrderDetails(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getInt("qty"),
                    rst.getBigDecimal("unitPrice"),
                    rst.getBigDecimal("total"),
                    rst.getString("storage"),
                    rst.getString("color"),
                    rst.getString("emiNo"),
                    rst.getString("warranty")
            ));
        }

        return list;
    }


    @Override
    public boolean save(OrderDetails detail) throws SQLException, ClassNotFoundException {

        String sql = "INSERT INTO OrderDetails " +
                "(oid, itemCode, description, qty, unitPrice, storage, color, emiNo, warranty, total) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        return CRUDUtil.execute(sql,
                detail.getOrderId(),
                detail.getItemCode(),
                detail.getDescription(),
                detail.getQty(),
                detail.getUnitPrice(),
                detail.getStorage(),
                detail.getColor(),
                detail.getEmiNo(),
                detail.getWarranty(),
                detail.getTotal()
        );
    }

    @Override
    public boolean update(OrderDetails detail) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE OrderDetails SET " +
                "description=?, qty=?, unitPrice=?, storage=?, color=?, emiNo=?, warranty=?, total=? " +
                "WHERE oid=? AND itemCode=?";

        return CRUDUtil.execute(sql,
                detail.getDescription(),
                detail.getQty(),
                detail.getUnitPrice(),
                detail.getStorage(),
                detail.getColor(),
                detail.getEmiNo(),
                detail.getWarranty(),
                detail.getTotal(),
                detail.getOrderId(),
                detail.getItemCode()
        );
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

        String sql = "SELECT oid FROM OrderDetails WHERE oid=?";
        ResultSet rst = CRUDUtil.execute(sql, id);
        return rst.next();
    }

    @Override
    public ArrayList<String> getAllId() throws SQLException, ClassNotFoundException {

        String sql = "SELECT oid FROM OrderDetails";
        ResultSet rst = CRUDUtil.execute(sql);

        ArrayList<String> list = new ArrayList<>();

        while (rst.next()) {
            list.add(rst.getString("oid"));
        }

        return list;
    }

    @Override
    public OrderDetails find(String id) throws SQLException, ClassNotFoundException {

        String sql = "SELECT * FROM OrderDetails WHERE oid=?";
        ResultSet rst = CRUDUtil.execute(sql, id);

        if (rst.next()) {
            return new OrderDetails(
                    rst.getString("itemCode"),
                    rst.getString("description"),
                    rst.getInt("qty"),
                    rst.getBigDecimal("unitPrice"),
                    rst.getBigDecimal("total"),
                    rst.getString("storage"),
                    rst.getString("color"),
                    rst.getString("emiNo"),
                    rst.getString("warranty")
            );
        }

        return null;
    }
}
