import java.sql.*;
import org.apache.log4j.Logger;

public class MyConnection {

    private static final Logger log = Logger.getLogger(MyConnection.class);
    static Connection conn = null;
    static Statement statement = null;

    MyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root", "Buggati");
            statement = conn.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            //log
            //statement.close();
            //conn.close();
            e.printStackTrace();
        }
    }

    public static synchronized void readDbUserTable() throws SQLException {

        try {
           // Connection dbConnection = MyConnection.conn;
           // Statement statement = dbConnection.createStatement();

            String selectTableSQL = "select ldap_login from test.rep_emp";

            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {
                String userid = rs.getString("LDAP_LOGIN");

                System.out.println("userid : " + userid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static synchronized String insertIntoDB(String ldap_login, String rep_fam,
                                                   String rep_name, String rep_ot,
                                                   Date rep_birth, String rep_posit){
        log.info("Начало обработки строки");
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
            statement.executeUpdate(selectTableSQL);
        } catch (SQLException e) {
            //log System.out.println(selectTableSQL);

            log.info("Еррок");
            e.printStackTrace();
            return "Error";
        }

        log.info("Начало обработки строки");
        return "Success";
    }

    private static boolean checkLoginInDB(String login){

        try {
            String selectTableSQL = "select distinct ldap_login from test.rep_emp where ldap_login = '"
                                    + login + "';";
            ResultSet rs = statement.executeQuery(selectTableSQL);
            System.out.println(selectTableSQL);

            if(rs.next())                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public static synchronized String deleteFromDB(String ldap_login){

        if(!checkLoginInDB(ldap_login)) return "Deleted";

            String selectTableSQL = "delete from test.rep_emp"
                   + " where test.rep_emp.LDAP_LOGIN = '" + ldap_login + "'";


        try {
            statement.execute(selectTableSQL);
        } catch (SQLException e) {
            //log System.out.println(selectTableSQL);
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
            statement.execute(selectTableSQL);
        } catch (SQLException e) {
            System.out.println(selectTableSQL);
            e.printStackTrace();
            return "Error";
        }
        return "Success";
    }

    private static void createDbUserTable(Connection con) throws SQLException {
        Connection dbConnection = con;
        Statement statement = null;

        String createTableSQL = "CREATE TABLE DBUSER("
                + "USER_ID integer(5) NOT NULL, "
                + "USERNAME VARCHAR(20) NOT NULL, "
                + "CREATED_BY VARCHAR(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
                + ")";

        try {
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            statement.execute(createTableSQL);
            System.out.println("Table \"dbuser\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

}
