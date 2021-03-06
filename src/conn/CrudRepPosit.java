package conn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudRepPosit implements ICRUD {

    @Override
    public boolean checkDataInDB(String posit) {

        if(posit == null) return false;

        String selectTableSQL = "select distinct rep_posit from test.rep_emp_posit where REP_POSIT = '"
                + posit + "';";
        try {

            Statement statement = MyConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            if(rs.next()) return true;

            rs.close();
            statement.close();

        } catch (SQLException e) {
            MyConnection.getLogger().info("checkLoginError, script : " + selectTableSQL  + " , error = " + e);
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public String[][] readFromDb() {

        String[][] data1 = null;

        String selectTableSQL = "select * from test.rep_emp_posit";
        try {
            Statement statement = MyConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            rs.last();
            int rowCount = rs.getRow();
            data1 = new String[rowCount][2];
            rs.beforeFirst();

            int i = 0;
            while (rs.next()) {
                data1[i][0] = rs.getString("REP_POSIT");
                data1[i][1] = rs.getString("REP_NAME");
                i++;
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL  + " , error = " + e);
            e.printStackTrace();
        }
        return data1;
    }

    @Override
    public String insertIntoDB(String[] str) {

        if(str == null) return "Empty data";

        if(str[0].length() > 6) return "Too long POSIT";

        if(checkDataInDB(str[0])) return "Already exists";

        int rows = 0;

        String selectTableSQL = "insert into test.rep_emp_posit values('"
                + str[0] + "', '"
                + str[1]  + "')";

        try {
            Statement statement = MyConnection.getConnection().createStatement();//prepareStatement(selectTableSQL);
            rows = statement.executeUpdate(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL  + " , error = " + e);
            return "Error";
        }

        return String.valueOf(rows);
    }

    @Override
    public String deleteFromDB(String posit){

        if(!checkDataInDB(posit)) return "Deleted";

        int rows = 0;
        String selectTableSQL = "delete from test.rep_emp_posit"
                + " where test.rep_dep.REP_POSIT = '" + posit + "'";

        try {
            Statement statement = MyConnection.getConnection().createStatement();
            rows = statement.executeUpdate(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("deleteFromDB, script : " + selectTableSQL  + " , error = " + e);
            e.printStackTrace();
            return "Error";
        }

        return String.valueOf(rows);
    }

    @Override
    public String updateInDB(String posit,String attr_name, String attr_value){

        if(!checkDataInDB(posit)) return "No such dep";

        int rows = 0;
        String selectTableSQL = "update test.rep_emp_posit"
                + " set test.rep_emp_posit." + attr_name + " = '" + attr_value + "'"
                + " where REP_POSIT = '" + posit + "'";
        try {
            Statement statement = MyConnection.getConnection().createStatement();
            rows = statement.executeUpdate(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("deleteFromDB, script : " + selectTableSQL  + " , error = " + e);
            return "Error";
        }

        return String.valueOf(rows);
    }

}
