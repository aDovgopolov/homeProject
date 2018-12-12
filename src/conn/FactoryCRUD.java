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
            case "dep" :   return new CrudRepDep();
            case "emp" :   return new CrudRepEmp();
            case "posit" :   return new CrudRepPosit();
            default: return null;
        }
    }
}
