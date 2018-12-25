package conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudRepDep implements ICRUD {

    @Override
    public boolean checkDataInDB(String login) {

        if(login == null) return false;

        String selectTableSQL = "select distinct rep_dep from test.rep_dep where rep_dep_code = '"
                + login + "';";

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

        String selectTableSQL = "select * from test.rep_dep";
        try {
            Statement statement = MyConnection.getConnection().createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);

            rs.last();
            int rowCount = rs.getRow();
            data1 = new String[rowCount][4];
            rs.beforeFirst();

            int i = 0;
            while (rs.next()) {
                data1[i][0] = rs.getString("REP_DEP");
                data1[i][1] = rs.getString("REP_DEP_CODE");
                data1[i][2] = rs.getString("REP_ADDR");
                data1[i][3] = rs.getString("REP_PARENT_DEP_CODE");
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

        if(str[1].length() > 12) return "Too long DEP";

        if(checkDataInDB(str[0])) return "Already exists";

        int rows = 0;
        String selectTableSQL = "insert into test.rep_dep values('"
                + str[0] + "', '"
                + str[1]  + "', '"
                + str[2]  + "', '"
                + str[3]  + "')";

        try {
            PreparedStatement statement = MyConnection.getConnection().prepareStatement(selectTableSQL);
            rows = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL  + " , error = " + e);
            return "Error";
        }

        return String.valueOf(rows);
    }

    @Override
    public String deleteFromDB(String dep){
        if(!checkDataInDB(dep)) return "Deleted";

        int rows = 0;
        String selectTableSQL = "delete from test.rep_dep"
                + " where test.rep_dep.REP_DEP_CODE = '" + dep + "'";

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
    public String updateInDB(String dep,String attr_name, String attr_value){

        if(!checkDataInDB(dep)) return "No such dep";

        int rows = 0;
        String selectTableSQL = "update test.rep_dep"
                + " set test.rep_dep." + attr_name + " = '" + attr_value + "'"
                + " where REP_DEP_CODE = '" + dep + "'";

        try {
            Statement statement = MyConnection.getConnection().createStatement();
            rows = statement.executeUpdate(selectTableSQL);
            statement.close();
        } catch (SQLException e) {
            MyConnection.getLogger().info("deleteFromDB, script : " + selectTableSQL + " , error = " + e);
            return "Error";
        }

        return String.valueOf(rows);
    }
}
