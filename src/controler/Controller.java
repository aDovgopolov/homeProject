package controler;

import conn.MyConnection;
import javax.swing.table.DefaultTableModel;

public class Controller implements IControler{

    private DefaultTableModel _tableModel;

    public Controller(DefaultTableModel tableModel) {
        _tableModel = tableModel;
    }

    @Override
    public void create(String table, String[] data) {
        String count = "0";
        try {
            count = MyConnection.getInstance().getFactoryObj(table).insertIntoDB(data);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("count = " + count);
    }

    @Override
    public void read(DefaultTableModel tableModel, String table) {
        String[][] str = new String[0][];

        if( tableModel.getRowCount() > 0){
            for(int i = tableModel.getRowCount() - 1; i >= 0; i--){
                tableModel.removeRow(i);
            }
        }

        try {
            str = MyConnection.getInstance().getFactoryObj(table).readFromDb();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        for (String[] aStr : str) {
            tableModel.addRow(aStr);
        }

        tableModel.fireTableDataChanged();
    }

    @Override
    public void update(DefaultTableModel tableModel, String table, String data, String attr, String value) {
        String count = "0";
        try {
            count = MyConnection.getInstance().getFactoryObj(table).updateInDB(data,attr, value);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("count = " + count);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void delete(DefaultTableModel tableModel, String table, String data, int row) {
        String count = "0";
        try {
            count = MyConnection.getInstance().getFactoryObj(table).deleteFromDB(data);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        System.out.println("count = " + count);
        tableModel.removeRow(row);
        tableModel.fireTableDataChanged();
    }

    @Override
    public void search() {
       //TODO
    }
}
