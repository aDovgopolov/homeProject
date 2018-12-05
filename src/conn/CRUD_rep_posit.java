package conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD_rep_posit implements ICRUD {

    @Override
    public boolean checkDataInDB(String posit) {

        if(posit == null) return false;

        String selectTableSQL = "select distinct rep_posit from test.rep_emp_posit where posit = '"
                + posit + "';";
        try {

            Statement statement = MyConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            if(rs.next()) return true;

            rs.close();
            statement.close();

        } catch (SQLException e) {
            MyConnection.getLogger().info("checkLoginError, script : " + selectTableSQL);
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
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL);
            e.printStackTrace();
        }
        return data1;
    }

    @Override
    public String insertIntoDB(String[] str) {

        if(str == null) return "Empty data";

        if(checkDataInDB(str[0])) return "Already exists";

        if(str[0].length() > 6) return "Too long POSIT";

        String selectTableSQL = "insert into test.rep_emp_posit values('"
                + str[0] + "', '"
                + str[1]  + "')";

        try {
            PreparedStatement statement = MyConnection.getConnection().prepareStatement(selectTableSQL);
            statement.execute();
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL);
            return "Error";
        }

        return "Success";
    }

    @Override
    public String deleteFromDB(String posit){

        if(!checkDataInDB(posit)) return "Deleted";

        String selectTableSQL = "delete from test.rep_emp_posit"
                + " where test.rep_dep.REP_POSIT = '" + posit + "'";

        try {
            Statement statement = MyConnection.getConnection().createStatement();
            statement.execute(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("deleteFromDB, script : " + selectTableSQL);
            e.printStackTrace();
            return "Error";
        }

        return "Success";
    }

    @Override
    public String updateInDB(String posit,String attr_name, String attr_value){

        if(!checkDataInDB(posit)) return "No such dep";

        String selectTableSQL = "update test.rep_emp_posit"
                + " set test.rep_emp_posit." + attr_name + " = '" + attr_value + "'"
                + " where posit = '" + posit + "'";

        try {
            Statement statement = MyConnection.getConnection().createStatement();
            statement.execute(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("deleteFromDB, script : " + selectTableSQL);
            return "Error";
        }
        return "Success";
    }

}
