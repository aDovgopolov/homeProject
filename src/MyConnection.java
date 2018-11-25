import java.sql.*;

public class MyConnection {
    static Connection conn = null;

    MyConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
                    "root", "Buggati");
        } catch (ClassNotFoundException | SQLException e) {
            //conn.close();
            e.printStackTrace();
        }
    }

    public static void readDbUserTable() throws SQLException {
        try {
            Connection dbConnection = MyConnection.conn;
            Statement statement = dbConnection.createStatement();

            String selectTableSQL = "select ldap_login from test.rep_emp";
            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                String userid = rs.getString("LDAP_LOGIN");

                System.out.println("userid : " + userid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
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
