import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Date;

public class FrameClosing extends JFrame {

    MyConnection con = null;
    JTable table = null;

    public void setConnection(MyConnection connection){
        this.con = connection;
    }

    public FrameClosing(Connection connection) {

        super("Заголовок окна");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setIconImage(getToolkit().getImage("icon.gif"));
        setSize(500, 500);

        JButton buttonl = new JButton( "Добавить значение" );
        buttonl.addActionListener(e -> {
            try {
                MyConnection.readDbUserTable();
                String check = MyConnection.insertIntoDB("DN118899DAG2",
                        "", "", "", new Date(0), "");
                System.out.println(check);
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.addRow(new String[]{"DN118899DAG1"});
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
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
            // MyConnection.readDbUserTable();
            MyConnection.updateInDB("1", "2", "3");
            System.out.println("Updaete");
           // DefaultTableModel model = (DefaultTableModel) table.getModel();
            // model.addRow(new String[]{"DN118899DAG1"});
            // System.out.println(model.getColumnName(1));

            //model.removeRow(1);
            //JDialog dialog = createDialog("Немодальное", false);
            // dialog.setVisible(true);
        });

        JPanel contents = new JPanel();
        contents.add( buttonl );
        contents.add( button2 );
        contents.add( button3 );
        setContentPane( contents );

        //JTable table = new JTable(data2, columnNames2);
        table = new JTable(new DefaultTableModel(data2, columnNames2));
        getContentPane().add(new JScrollPane(table));

        setVisible(true);
    }

    // создает диалоговое окно
    private JDialog createDialog( String title, boolean modal ){
        JDialog dialog = new JDialog( this, title, modal );
        dialog.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
        dialog.setSize( 200, 60 );
        return dialog;
    }


    String[] columnNames = {"First Name",
            "Last Name",
            "Sport",
            "# of Years",
            "Vegetarian"};

    String[] columnNames2 = {"LDAP_LOGIN"};

    Object[][] data = {
            {"Kathy", "Smith",
                    "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                    "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                    "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                    "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                    "Pool", new Integer(10), new Boolean(false)}
    };
    String[][] data2 = {
            {"Kathy"},
            {"John"}
    };

    String[][] data3 = {
            {"Kathy"},
            {"John"},
            {"DN118899DAG1"}
    };
}