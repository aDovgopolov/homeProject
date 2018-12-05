package conn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CRUD_rep_emp implements ICRUD {

    @Override
    public boolean checkDataInDB(String login) {

        if(login == null) return false;

        String selectTableSQL = "select distinct ldap_login from test.rep_emp where ldap_login = '"
                + login + "';";
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
        String selectTableSQL = "select * from test.rep_emp";

        try {
            Statement statement = MyConnection.getConnection().createStatement();
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
            MyConnection.getLogger().info("insertIntoDBError, script : " + selectTableSQL);
            e.printStackTrace();
        }
        return data1;
    }

    @Override
    public String insertIntoDB(String[] str) {

        if(str == null) return "Empty data";

        if(checkDataInDB(str[0])) return "Already exists";

        if(str[0].length() > 12) return "Too long login";

        String selectTableSQL = "insert into test.rep_emp values('"
                + str[0] + "', '"
                + str[1]  + "', '"
                + str[2]  + "', '"
                + str[3]  + "', '"
                + str[4]  + "', '"
                + str[5]  + "')";

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
    public String deleteFromDB(String ldap_login){

        if(!checkDataInDB(ldap_login)) return "Deleted";

        String selectTableSQL = "delete from test.rep_emp"
                + " where test.rep_emp.LDAP_LOGIN = '" + ldap_login + "'";

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
    public String updateInDB(String ldap_login,String attr_name, String attr_value){

        if(!checkDataInDB(ldap_login)) return "No such user";

        String selectTableSQL = "update test.rep_emp"
                + " set test.rep_emp." + attr_name + " = '" + attr_value + "'"
                + " where LDAP_LOGIN = '" + ldap_login + "'";

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
