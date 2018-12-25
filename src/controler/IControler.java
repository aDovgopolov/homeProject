package controler;

import javax.swing.table.DefaultTableModel;

public interface IControler {

    void create(String table, String[] data);

    void read(DefaultTableModel tableModel, String str);

    void update(DefaultTableModel tableModel, String table, String data, String attr, String value);

    void delete(DefaultTableModel tableModel, String str, String data, int column);

    void search();
}
