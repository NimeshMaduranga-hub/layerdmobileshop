package lk.ijse.layerdmobileshop.mobileshop.bo;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory instance;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return instance == null ? new BOFactory() : instance;
    }
    public enum BOType{
        CUSTOMER,
        ITEM,
        PLACE_ORDER,
        LOGIN,
        EMPLOYEE,
        ATTENDANCE,
        SALARY,
        USER
    }
    public SuperBO getBo(BOType type){
        switch (type){
            case CUSTOMER :
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case PLACE_ORDER:
                return new PlaceOrderBOImpl();
            case LOGIN:
                return new UserBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case ATTENDANCE:
                return new AttendanceBOImpl();
            case SALARY:
                return new SalaryBOImpl();
            case USER:
                return new UserBOImpl();
            default:
                return null;
        }
    }
}
