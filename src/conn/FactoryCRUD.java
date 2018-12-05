package conn;

public class FactoryCRUD {

    private static FactoryCRUD instance = null;

    private FactoryCRUD() {}

    public static synchronized FactoryCRUD getInstance() {
        if (instance == null){
            instance = new FactoryCRUD();
        }
        return instance;
    }

    public ICRUD factoryMethod(String cmd){
        switch (cmd) {
            case "dep" :   return new CRUD_rep_dep();
            case "emp" :   return new CRUD_rep_emp();
            case "posit" :   return new CRUD_rep_posit();
            default: return null;
        }
    }
}
