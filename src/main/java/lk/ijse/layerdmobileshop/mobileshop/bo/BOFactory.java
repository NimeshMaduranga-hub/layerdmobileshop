package lk.ijse.layerdmobileshop.mobileshop.bo;

import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.CustomerBOImpl;
import lk.ijse.layerdmobileshop.mobileshop.bo.custom.impl.ItemBOImpl;

public class BOFactory {
    private static BOFactory instance;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return instance == null ? new BOFactory() : instance;
    }
    public enum BOType{
        CUSTOMER,
        ITEM
    }
    public SuperBO getBo(BOType type){
        switch (type){
            case CUSTOMER :
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            default:
                return null;
        }
    }
}
