package controler;

import conn.MyConnection;
import javax.swing.table.DefaultTableModel;

public class Controler implements IControler{

    private DefaultTableModel _tableModel;

    public Controler(DefaultTableModel tableModel) {
        _tableModel = tableModel;
    }

    @Override
    public void create() {
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
    public void update(DefaultTableModel tableModel, String table) {
        System.out.println("!!!!!!!");
        tableModel.fireTableDataChanged();
        tableModel.fireTableRowsDeleted(0,0);
        tableModel.fireTableStructureChanged();
        //tableModel.fireTableChanged();
    }

    @Override
    public void delete(DefaultTableModel tableModel, String table) {
    }

    @Override
    public void search() {

    }
}
