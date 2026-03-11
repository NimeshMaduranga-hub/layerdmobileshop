package lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl;

import javafx.scene.control.Alert;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.PlaceOrderBO;
import lk.ijse.layerdmobileshop.mobileshop.dao.DAOFactory;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.CustomerDAO;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.ItemDAO;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.OrderDAO;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.OrderDetailsDAO;
import lk.ijse.layerdmobileshop.mobileshop.db.DBconnection;
import lk.ijse.layerdmobileshop.mobileshop.dto.CustomerDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.ItemDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDTO;
import lk.ijse.layerdmobileshop.mobileshop.dto.OrderDetailDTO;
import lk.ijse.layerdmobileshop.mobileshop.entity.Customer;
import lk.ijse.layerdmobileshop.mobileshop.entity.Item;
import lk.ijse.layerdmobileshop.mobileshop.entity.OrderDetails;
import lk.ijse.layerdmobileshop.mobileshop.entity.Orders;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlaceOrderBOImpl implements PlaceOrderBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ITEM);
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER);
    OrderDetailsDAO orderdetailsDAO = (OrderDetailsDAO) DAOFactory.getInstance().getDAOType(DAOFactory.DAOType.ORDER_DETAILS);

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
    public boolean saveOrder(OrderDTO orderDTO, List<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {

        //return orderDAO.save(orderDTO);
        Connection connection = DBconnection.getdBconnection().getConnection();

        try{
            connection.setAutoCommit(false);

            /*if order id already exist*/
            if (isExist(orderDTO.getOrderId())) {
                new Alert(Alert.AlertType.ERROR, "Order ID already exist!").show();
                return false;
            }
            Orders order = new Orders(orderDTO.getOrderId(), orderDTO.getOrderDate(), orderDTO.getCustomerId(), orderDTO.getCustomerName(), orderDTO.getOrderTotal());
            boolean b1 = orderDAO.save(order);

            if (!b1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            for (OrderDetailDTO detail : orderDetails) {
                boolean b2=saveOrderDetails(detail);

                if (!b2) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
//                //Search & Update Item
                ItemDTO item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                boolean b3=updateItem(item);

                if (!b3) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean saveOrderDetails(OrderDetailDTO detail) throws SQLException, ClassNotFoundException {

        OrderDetails orderDetails = new OrderDetails(detail.getOrderId(), detail.getItemCode(), detail.getQty(),detail.getUnitPrice());
        return orderdetailsDAO.save(orderDetails);
    }

    @Override
    public boolean updateItem(ItemDTO itemDTO) throws SQLException, ClassNotFoundException {
        Item item = new Item(itemDTO.getCode(), itemDTO.getDescription(), itemDTO.getUnitPrice(), itemDTO.getQtyOnHand());
        return itemDAO.update(item);
    }

    @Override
    public CustomerDTO findCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.find(id);
        CustomerDTO cusDTO = new CustomerDTO(customer.getId(), customer.getName(), customer.getAddress(),customer.getMobile());
        return cusDTO;
    }

    @Override
    public ItemDTO findItem(String id) throws SQLException, ClassNotFoundException {
        Item item =  itemDAO.find(id);
        ItemDTO itemDto = new ItemDTO(item.getCode(), item.getDescription(), item.getUnitPrice(),item.getQtyOnHand());
        return itemDto;
    }

    @Override
    public ArrayList<String> getAllCusId() throws SQLException, ClassNotFoundException {
        return customerDAO.getAllId();
    }

    @Override
    public ArrayList<String> getAllItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getAllId();
    }

}
