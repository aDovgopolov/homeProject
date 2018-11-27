import java.sql.*;

public class MyConnection {

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
        if(checkLoginInDB(ldap_login)) return "Already exists";

        String selectTableSQL = "insert into test.rep_emp values('"
                + ldap_login + "', '"
                + rep_fam  + "', '"
                + rep_name  + "', '"
                + rep_ot  + "', '"
                + rep_birth  + "', '"
                + rep_posit  + "')";
               // + "', '', '', '', '" + date + "', '');";
        System.out.println(selectTableSQL);
        try {
            //длина не может быть больше 12
            statement.executeUpdate(selectTableSQL);
        } catch (SQLException e) {
            //log
            e.printStackTrace();
            return "Error";
        }

        return "Success";
    }

    private static boolean checkLoginInDB(String login){

        try {
            String selectTableSQL = "select ldap_login from test.rep_emp where ldap_login = '"
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

    public static synchronized void deleteFromDB(String str){

            String selectTableSQL = "delete from test.rep_emp"
                   + " where test.rep_emp.LDAP_LOGIN = '" + str + "'";

        System.out.println(selectTableSQL);
        try {
            statement.execute(selectTableSQL);
        } catch (SQLException e) {
            //log
            e.printStackTrace();
        }
    }

    public static synchronized void updateInDB(){
        String selectTableSQL = "update test.rep_emp"
        + " set test.rep_emp.REP_POSIT = '224488'"
        + " where LDAP_LOGIN = 'DN118899DAG2'";
        System.out.println(selectTableSQL);
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
