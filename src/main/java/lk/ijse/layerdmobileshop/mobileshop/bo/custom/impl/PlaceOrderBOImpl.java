package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.PlaceOrderBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.*;
import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;
import lk.ijse.layerdmobileshop.mobileshop.dto.*;
import lk.ijse.layerdmobileshop.mobileshop.entity.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO =
            (CustomerDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.CUSTOMER);

    ItemDAO itemDAO =
            (ItemDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ITEM);

    OrderDAO orderDAO =
            (OrderDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER);

    OrderDetailsDAO orderdetailsDAO =
            (OrderDetailsDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER_DETAILS);

    @Override
    public boolean saveOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails)
            throws SQLException, ClassNotFoundException {

        Connection connection = DBconnection.getdBconnection().getConnection();

        try {
            connection.setAutoCommit(false);

            // 1. check order exist
            if (orderDAO.isExist(orderDTO.getOrderId())) {
                throw new RuntimeException("Order already exists!");
            }

            // 2. save order
            Orders order = new Orders(
                    orderDTO.getOrderId(),
                    orderDTO.getOrderDate(),
                    orderDTO.getCustomerId(),
                    orderDTO.getCustomerName(),
                    orderDTO.getOrderTotal()
            );

            if (!orderDAO.save(order)) {
                connection.rollback();
                return false;
            }

            // 3. loop order details
            for (OrderDetailDTO detail : orderDetails) {

                // calculate total
                BigDecimal total = detail.getUnitPrice()
                        .multiply(BigDecimal.valueOf(detail.getQty()));

                OrderDetails od = new OrderDetails(
                        detail.getOrderId(),
                        detail.getItemCode(),
                        detail.getDescription(),
                        detail.getQty(),
                        detail.getUnitPrice(),
                        detail.getStorage(),
                        detail.getColor(),
                        detail.getEmiNo(),
                        detail.getWarranty(),
                        total
                );

                // save order details
                if (!orderdetailsDAO.save(od)) {
                    connection.rollback();
                    return false;
                }

                // 🔥 STOCK UPDATE (ONLY QTY)
                if (!itemDAO.updateQty(detail.getItemCode(), detail.getQty())) {
                    connection.rollback();
                    return false;
                }
            }

            connection.commit();
            return true;

        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
            return false;

        } finally {
            connection.setAutoCommit(true);
        }
    }

    // ---------------- other methods (unchanged) ----------------

    @Override
    public String genarateNewId() throws SQLException, ClassNotFoundException {
        return orderDAO.genarateNewId();
    }

    @Override
    public boolean isExist(String orderId) throws SQLException, ClassNotFoundException {
        return orderDAO.isExist(orderId);
    }

    @Override
    public boolean itemIsExist(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.isExist(itemId);
    }

    @Override
    public boolean customerIsExist(String customerId) throws SQLException, ClassNotFoundException {
        return customerDAO.isExist(customerId);
    }

    @Override
    public CustomerDTO findCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.find(id);
        return new CustomerDTO(c.getId(), c.getName(), c.getAddress(), c.getMobile());
    }

    @Override
    public ItemDTO findItem(String id) throws SQLException, ClassNotFoundException {

        Item i = itemDAO.find(id);

        return new ItemDTO(
                i.getCode(),
                i.getDescription(),
                i.getUnitPrice(),
                i.getQtyOnHand(),
                i.getStorage(),   // ✅ FIX
                i.getColor(),     // ✅ FIX
                i.getEmiNo(),     // ✅ FIX
                i.getWarranty()   // ✅ FIX
        );
    }

    @Override
    public ArrayList<String> getAllCusId() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllId();
    }

    @Override
    public ArrayList<String> getAllItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllId();
    }

    @Override
    public boolean saveOrderDetails(OrderDetailDTO detail) {
        return false;
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) {
        return false;
    }
}