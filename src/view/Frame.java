package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Date;

import conn.MyConnection;


public class Frame extends JFrame {

    JTable table = null;
    JScrollPane jsp;
    String[] columnNames = {"LDAP_LOGIN", "REP_FAM", "REP_NAME", "REP_OT", "REP_BIRTH", "REP_POSIT"};

    public Frame() {

        super("Заголовок окна");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);

        JButton button0 = new JButton( "Получить базу сотрудников" );
        button0.addActionListener(e -> {
            DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
            String[][] str = new String[0][];
            try {
                str = MyConnection.getInstance().getFactoryObj("emp").readFromDb();

                /*System.out.println( MyConnection.getInstance().getFactoryObj("emp").insertIntoDB(new String[]{"DN118899DAG7",
                                                                                                 "", "", "", "1983-09-17", ""}));*/
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            for (String[] aStr : str) {
                tableModel.addRow(aStr);
            }
            tableModel.fireTableDataChanged();
        });

        JButton buttonl = new JButton( "Добавить значение" );
        buttonl.addActionListener(e -> {
           // MyConnection.readFromDb();
            String check = null;//MyConnection.insertIntoDB("DN118899DAG2",
                   // "", "", "", new Date(0), "");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.addRow(new String[]{"DN118899DAG1"});
            //JDialog dialog = createDialog("Немодальное", false);
           // dialog.setVisible(true);
        });

        JButton button2 = new JButton( "Удалить значение" );
        button2.addActionListener(e -> {
            // conn.MyConnection.readDbUserTable();
           // MyConnection.deleteFromDB("DN118899DAG2");
            DefaultTableModel model = (DefaultTableModel) table.getModel();
           // model.addRow(new String[]{"DN118899DAG1"});
           // System.out.println(model.getColumnName(1));

            model.removeRow(1);
            //JDialog dialog = createDialog("Немодальное", false);
            // dialog.setVisible(true);
        });

        JButton button3 = new JButton( "Обновить" );
        button3.addActionListener(e -> {
            //MyConnection.updateInDB("1", "2", "3");
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

    void createFr(){

        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container с = getContentPane();

        JPanel jp1 = new JPanel();
        jp1.setBounds(0,0,400, 200);
        jp1.setBackground(Color.RED);
        с.add(jp1, BorderLayout.NORTH);

// ... или константы из класса BorderLayout
        JPanel jp2 = new JPanel();
        jp2.setBackground(Color.BLUE);
        с.add(jp2, BorderLayout.SOUTH);

       /* JPanel jp3 = new JPanel();
        jp3.setBackground(Color.ORANGE);
        с.add(jp3, BorderLayout.CENTER);*/
// выводим окно на экран
        setVisible(true);
    }
}