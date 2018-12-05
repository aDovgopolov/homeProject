package conn;

public interface ICRUD {

    boolean checkDataInDB(String str);

    String[][] readFromDb();

    String insertIntoDB(String[] str);

    String deleteFromDB(String str);

    String updateInDB(String str, String attr_name, String attr_value);
}
