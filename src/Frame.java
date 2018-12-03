import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public class Frame extends JFrame {

    JTable table = null;
    JScrollPane jsp;
    String[] columnNames = {"LDAP_LOGIN", "REP_FAM", "REP_NAME", "REP_OT", "REP_BIRTH", "REP_POSIT"};

    public Frame() {

        super("Заголовок окна");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(getToolkit().getImage("icon.gif"));
        setSize(700, 500);


        JDialog jDialog = createDialog("title", true);
        jDialog.setBounds(100, 100, 500, 100);

        JTextField jTextField = new JTextField("Check");
        jTextField.setSize(100, 50);
        JButton ok = new JButton("ok");
        ok.addActionListener(event -> System.out.println(jTextField.getText()));
        JPanel panel = new JPanel();
        panel.add(jTextField);
        panel.add(ok);
        jDialog.add(panel, BorderLayout.SOUTH);
        panel.setSize(260, 160);
        jDialog.add(panel);
        jDialog.setVisible(true);



        JButton button0 = new JButton( "Получить базу сотрудников" );
        button0.addActionListener(e -> {
            // if table is not empty - dont show new or recreaty data (must be clear data from DB)
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            String[][] str = MyConnection.readDb();
            for (String[] aStr : str) {
                tableModel.addRow(aStr);
            }
            tableModel.fireTableDataChanged();
        });

        JButton buttonl = new JButton( "Добавить значение" );
        buttonl.addActionListener(e -> {
            MyConnection.readDb();
            String check = MyConnection.insertIntoDB("DN118899DAG2",
                    "", "", "", new Date(0), "");
            System.out.println(check);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new String[]{"DN118899DAG1"});
            //JDialog dialog = createDialog("Немодальное", false);
           // dialog.setVisible(true);
        });

        JButton button2 = new JButton( "Удалить значение" );
        button2.addActionListener(e -> {
            // MyConnection.readDbUserTable();
            MyConnection.deleteFromDB("DN118899DAG2");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
           // model.addRow(new String[]{"DN118899DAG1"});
           // System.out.println(model.getColumnName(1));

            model.removeRow(1);
            //JDialog dialog = createDialog("Немодальное", false);
            // dialog.setVisible(true);
        });

        JButton button3 = new JButton( "Обновить" );
        button3.addActionListener(e -> {
            MyConnection.updateInDB("1", "2", "3");
            System.out.println("Updaete");
        });

        JPanel contents = new JPanel();
        contents.add( button0 );
        contents.add( buttonl );
        contents.add( button2 );
        contents.add( button3 );
        setContentPane( contents );

        table = new JTable(new DefaultTableModel(null,columnNames ));
        jsp = new JScrollPane(table);
        jsp.setBounds(100,100,50,50);
        getContentPane().add(jsp);

       // jsp.setVisible(false);

        setVisible(true);
    }

    private JDialog createDialog( String title, boolean modal ){
        JDialog dialog = new JDialog( this, title, modal );
        dialog.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        dialog.setSize( 200, 60 );
        return dialog;
    }

}