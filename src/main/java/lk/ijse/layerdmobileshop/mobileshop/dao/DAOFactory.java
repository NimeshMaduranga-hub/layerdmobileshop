package lk.ijse.layerdmobileshop.mobileshop.dao;

import lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl.ItemDAOImpl;
import lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl.OrderDAOImpl;

public class DAOFactory {

    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOType {
        CUSTOMER,
        ITEM,
        ORDER,
        ORDER_DETAILS,
        QUERY
    }

    public SuperDAO getDAOType(DAOType daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();

            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();

            default:
                return null;
        }
    }
}
