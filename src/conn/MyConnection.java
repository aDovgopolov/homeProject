package conn;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public final class MyConnection{

    private static final Logger log = Logger.getLogger(Connection.class);
    private static MyConnection conn = null;
    private static Connection conect;
    private ICRUD icrud ;
    private Map<String, ICRUD> hashMap = new HashMap<>();
    private static  String _login = null;
    private static  String _password = null;

    private MyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=UTC",
                    _login, _password);
        } catch (ClassNotFoundException | SQLException e) {
            log.error("conn.MyConnection error :" + e);
            e.printStackTrace();
        }
    }

    static Logger getLogger(){
        return log;
    }

     static  Connection getConnection(){
        return conect;
    }

    public static synchronized MyConnection getInstance() {
        if (conn == null){
            conn = new MyConnection();
        }
        return conn;
    }

    public static void setEntryData(String login, String password){
        _login = login;
        _password = password;
    }

    public ICRUD getFactoryObj(String str) throws Exception{

        switch (str) {
            case "dep":
                if (hashMap.get("dep") == null) {
                    icrud = FactoryCRUD.getInstance().factoryMethod("dep");
                    hashMap.put("dep", icrud);
                } else {
                    icrud = hashMap.get("dep");
                }
                break;

            case "emp":
                if (hashMap.get("emp") == null) {
                    icrud = FactoryCRUD.getInstance().factoryMethod("emp");
                    hashMap.put("emp", icrud);
                } else {
                    icrud = hashMap.get("emp");
                }
                break;

            case "posit":
                if (hashMap.get("posit") == null) {
                    icrud = FactoryCRUD.getInstance().factoryMethod("posit");
                    hashMap.put("posit", icrud);
                } else {
                    icrud = hashMap.get("posit");
                }
                break;

            default:
                throw new OperationNotFoundException();
        }
        return icrud;
    }

//
//    public void getTrueMethod(String str){
//        Class<?> clazz;
//        String ss = "";
//
//        try {
//            clazz = Class.forName(stra.get(0));
//
//            Method[] methods = clazz.getMethods();
//            Method metho = null;
//
//            ArrayList<String> argsA = new ArrayList<>();
//            for (Method method : methods) {
//                if (method.getName().equals(stra.get(1))) {
//                    metho = method;
//                    Class[] paramTypes = method.getParameterTypes();
//
//                    if (paramTypes.length == (stra.size() - 2)) {
//                        argsA.clear();
//                        for (int i = 0; i < paramTypes.length; i++) {
//                            argsA.add(stra.get(2 + i));
//                        }
//                        break;
//                    }
//                }
//            }
//
//            ss = (String) metho.invoke(clazz.newInstance(), argsA.toArray());
//
//            log.info("Вызов класс: " + clazz.getName()
//                    + ", метод = "+ metho
//                    + ", аргументы = "+  argsA.toString()
//                    + ", результат = "+ ss);
//
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | IllegalArgumentException e) {
//            log.error("Это сообщение ошибки = " + e);
//
//            System.out.println("Ошибка" + e);
//            e.printStackTrace();
//        }
//
//    }

}
