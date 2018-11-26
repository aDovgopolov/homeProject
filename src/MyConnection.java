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

    public static synchronized void insertIntoDB(String ldap){

        Date date = new Date(0);
        String selectTableSQL = "insert into test.rep_emp values('" + ldap + "', '', '', '', '" + date + "', '');";
        System.out.println(selectTableSQL);
        try {
            //длина не может быть больше 12
            statement.executeUpdate(selectTableSQL);
        } catch (SQLException e) {
            //log
            e.printStackTrace();
        }
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
