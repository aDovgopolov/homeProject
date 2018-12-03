import java.sql.*;
import org.apache.log4j.Logger;

public final class  MyConnection{

    private static final Logger log = Logger.getLogger(MyConnection.class);
    private static Connection conn = null;
    
    private MyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root", "Buggati");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            log.info("MyConnection error :" + e);
        }
    }

    public static synchronized Connection getInstance() {
        if (conn == null){
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                        "root", "Buggati");
            } catch (SQLException e) {
                log.info("MyConnection error :" + e);
            }
        }
        System.out.println("CONN");
        return conn;
    }

    private static boolean checkLoginInDB(String login){

        if(login == null) return false;

        String selectTableSQL = "select distinct ldap_login from test.rep_emp where ldap_login = '"
                + login + "';";
        try {

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            if(rs.next()) return true;

            rs.close();
            statement.close();

            log.info("checkLoginSuccess, script : " + selectTableSQL + " , login = " + login);
        } catch (SQLException e) {
            log.info("checkLoginError, script : " + selectTableSQL);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static synchronized String[][] readDb(){
        String[][] data1 = null;
        String selectTableSQL = "select * from test.rep_emp";

        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            rs.last();
            int rowCount = rs.getRow();
            data1 = new String[rowCount][6];
            rs.beforeFirst();

           int i = 0;
            while (rs.next()) {
                data1[i][0] = rs.getString("LDAP_LOGIN");
                data1[i][1] = rs.getString("REP_FAM");
                data1[i][2] = rs.getString("REP_NAME");
                data1[i][3] = rs.getString("REP_OT");
                data1[i][4] = rs.getString("REP_BIRTH");
                data1[i][5] = rs.getString("REP_POSIT");
                i++;
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            log.info("insertIntoDBError, script : " + selectTableSQL);
            e.printStackTrace();
        }
        return data1;
    }

    public static synchronized String insertIntoDB(String ldap_login, String rep_fam,
                                                   String rep_name, String rep_ot,
                                                   Date rep_birth, String rep_posit){
        log.info("Начало вставки данных");
        if(checkLoginInDB(ldap_login)) return "Already exists";

        if(ldap_login.length() > 12) return "Too long login";

        String selectTableSQL = "insert into test.rep_emp values('"
                + ldap_login + "', '"
                + rep_fam  + "', '"
                + rep_name  + "', '"
                + rep_ot  + "', '"
                + rep_birth  + "', '"
                + rep_posit  + "')";

        try {
            PreparedStatement statement = conn.prepareStatement(selectTableSQL);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            log.info("insertIntoDBError, script : " + selectTableSQL);
            e.printStackTrace();
            return "Error";
        }

        log.info("Вставка завершена");
        return "Success";
    }

    public static synchronized String deleteFromDB(String ldap_login){

        if(!checkLoginInDB(ldap_login)) return "Deleted";

        String selectTableSQL = "delete from test.rep_emp"
                + " where test.rep_emp.LDAP_LOGIN = '" + ldap_login + "'";

        try {
            Statement statement = conn.createStatement();
            statement.execute(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            log.info("deleteFromDB, script : " + selectTableSQL);
            e.printStackTrace();
            return "Error";
        }

        return "Success";
    }

    public static synchronized String updateInDB(String ldap_login,
                                                 String attr_name,
                                                 String attr_value){

        if(!checkLoginInDB(ldap_login)) return "No such user";

        String selectTableSQL = "update test.rep_emp"
        + " set test.rep_emp." + attr_name + " = '" + attr_value + "'"
        + " where LDAP_LOGIN = '" + ldap_login + "'";

        try {
            Statement statement = conn.createStatement();
            statement.execute(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            log.info("deleteFromDB, script : " + selectTableSQL);
            e.printStackTrace();
            return "Error";
        }
        return "Success";
    }
}
