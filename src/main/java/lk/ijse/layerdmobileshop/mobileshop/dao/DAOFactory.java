package lk.ijse.layerdmobileshop.mobileshop.dao;

import lk.ijse.layerdmobileshop.mobileshop.dao.custom.impl.*;

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
        QUERY,
        USER,
        EMPLOYEE,
        ATTENDANCE,
        SALARY

    }

    public SuperDAO getDAOType(DAOType daoType) {
        switch (daoType) {
            case CUSTOMER:
                return new CustomerDAOImpl();

            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderdetailsDAOImpl();

            case QUERY:
                return new QueryDAOImpl();
            case USER:
                return new UserDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case ATTENDANCE:
                return new AttendanceDAOImpl();
            case SALARY:
                return new SalaryDAOImpl();
            default:
                return null;
        }
    }
}
