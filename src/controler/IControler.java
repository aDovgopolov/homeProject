package controler;

import javax.swing.table.DefaultTableModel;

public interface IControler {
    void create();

    void read(DefaultTableModel tableModel, String str);

    void update(DefaultTableModel tableModel, String str);

    void delete(DefaultTableModel tableModel, String str);

    void search();
}
