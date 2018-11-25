import java.sql.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("Hello World!");

        MyConnection connection = new MyConnection();
        FrameClosing frameClosing = new FrameClosing(connection.conn);
        //createDbUserTable(conn);
//        try {
//            connection.readDbUserTable();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        //conn.close();

//        for (int i = 0; i < 30; i++){
//
//            String str = String.valueOf((int) (1+ Math.random()*31))+"."+
//                         String.valueOf((int) (1 + Math.random()*12))+"."+
//                    String.valueOf((int) (2018 - 18 - Math.random()*40));
//            System.out.println(str);
//        }


//        Connection conn = null;
//        try {
//            conn =  DriverManager.getConnection("jdbc:mysql://localhost/test?" +
//                            "user=root&password=Buggati");
//
//            // Do something with the Connection
//        } catch (SQLException ex) {
//            // handle any errors
//            System.out.println("SQLException: " + ex.getMessage());
//            System.out.println("SQLState: " + ex.getSQLState());
//            System.out.println("VendorError: " + ex.getErrorCode());
//        }


//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test",
//                    "root", "Buggati");
//
//            //createDbUserTable(conn);
//            //readDbUserTable(conn);
//            //conn.close();
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }

    }

}
