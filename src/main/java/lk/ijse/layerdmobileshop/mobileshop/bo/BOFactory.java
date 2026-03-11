package lk.ijse.layerdmobileshop.mobileshop.bo;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.CustomerBOImpl;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.ItemBOImpl;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.LoginBOImpl;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.PlaceOrderBOImpl;

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
        LOGIN
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
                return new LoginBOImpl();
            default:
                return null;
        }
    }
}
