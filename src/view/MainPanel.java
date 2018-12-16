package view;

import controler.Controler;
import controler.IControler;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame{

    private IControler iControler;
    private JTable table = null;
    private JScrollPane jsp;
    private final String[] empColumnNames = {"LDAP_LOGIN", "REP_FAM", "REP_NAME", "REP_OT", "REP_BIRTH", "REP_POSIT"};
    private final String[] depColumnNames = {"REP_DEP", "REP_DEP_CODE", "REP_ADDR", "REP_PARENT_DEP"};
    private final String[] positColumnNames = {"REP_POSIT", "REP_NAME"};
    private JComboBox comboBox;
    private DefaultTableModel tableModel;
    private DefaultTableModel tableDepModel;
    private DefaultTableModel tablePositModel;
    JComboBox<String> combo = new JComboBox<>(new String[] { "Менеджер", "Программист", "Водитель"});
    private String item = "emp"; //default data
    private final String[] items = {"emp", "dep", "posit"};

    SimpleTableDemo simpleTableDemo;

    MainPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Welcome");
        setSize(700, 400);

        initButtonPanel();
        initTablePanel();
        initTableCheckbox();

        iControler = new Controler(tableModel);

        setVisible(true);
    }

    private void initButtonPanel(){
        JPanel grid = new JPanel();
        GridLayout gl = new GridLayout(1, 0, 5, 12);
        grid.setLayout(gl);

        JButton btnRead = new JButton("Получить базу");
        JButton btnUPD= new JButton("Обновить");
        JButton btnCreate= new JButton("Создать");
        JButton btnDelete= new JButton("Удалить");
        JButton btnSearch= new JButton("Поиск");

        btnRead.addActionListener(v-> new BtnReadListener().actionPerformed(v));
        btnUPD.addActionListener(v-> {
           // table.setModel(tableModel);
            //iControler.update(tableModel, item);
            tableModel.fireTableDataChanged();
        });
        btnDelete.addActionListener(v-> iControler.delete(tableModel, item));
        btnCreate.addActionListener(v-> iControler.create());
        btnSearch.addActionListener(v-> iControler.search());

        grid.add( btnRead );
        grid.add( btnUPD );
        grid.add( btnCreate );
        grid.add( btnDelete );
        grid.add( btnSearch );

        getContentPane().add( grid , BorderLayout.NORTH);
    }

    private void initTablePanel(){
        tableModel = new DefaultTableModel(null,empColumnNames );
        tableDepModel = new DefaultTableModel(null,depColumnNames );
        tablePositModel = new DefaultTableModel(null, positColumnNames );

        table = new JTable();
        jsp = new JScrollPane(table);
        jsp.setBounds(100,100,50,50);

        simpleTableDemo = new SimpleTableDemo();
        table.getModel().addTableModelListener(e -> System.out.println("HERE!!!!!!!!!!!!!!!!!!!"));

        getContentPane().add(jsp);
    }

    private void initTableCheckbox() {
        Font font = new Font("Verdana", Font.PLAIN, 18);
        Container content = getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        comboBox = new JComboBox(items);
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox)e.getSource();
            item = (String)box.getSelectedItem();
        };

        comboBox.addActionListener(actionListener);
        content.add(comboBox);
    }

    public class BtnReadListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (comboBox.getSelectedItem().toString()) {
                case "emp":
                    table.setModel(tableModel);
                    iControler.read(tableModel, item);
                    break;
                case "dep":
                    table.setModel(tableDepModel);
                    iControler.read(tableDepModel, item);
                    break;
                default:
                    table.setModel(tablePositModel);
                    iControler.read(tablePositModel, item);
                    break;
            }
        }
    }

    public void changeTableColums(String str){

        TableColumnModel tt =  table.getColumnModel();
        while( tt.getColumnCount() > 0 ) {
            tt.removeColumn( tt.getColumn(0) );
        }

       // tableModel.fireTableDataChanged();
        //System.out.println(tableModel.getColumnCount());
        tableModel.addColumn("STR");


      /*  for(int i = 0 ; i < depColumnNames.length;i++){

            tableModel.addColumn(depColumnNames[i]);
        }*/

        //        JTableHeader th = table.getTableHeader();
//        TableColumnModel tcm = th.getColumnModel();
//        TableColumn tc = tcm.getColumn(0);
//        tc.setHeaderValue( "???" );
//        th.repaint();



/*
        TableColumnModel cm = getColumnModel();
        while( cm.getColumnCount() > 0 ) {
            cm.removeColumn( cm.getColumn(0) );
        }

        // Create new columns from the data model info
        for( int i=0; i<m.getColumnCount(); i++ ) {
            TableColumn newColumn = new MyTableColumn(i);
            if( i == TaskListModel.COL_LOCATION )
                newColumn.setCellRenderer( new LeftDotRenderer() );
            else if( i != TaskListModel.COL_GROUP )
                newColumn.setCellRenderer( new TooltipRenderer() );
            addColumn(newColumn);
        }
        */

       /* DefaultCellEditor editor = new DefaultCellEditor(combo);
        table.getColumnModel().getColumn(1).setCellEditor(editor);*/

        /*
        switch (str){
            case "posit" :
                System.out.println("Table change posit");
                table = new JTable(new DefaultTableModel(null,positColumnNames ));  //
                tableModel = (DefaultTableModel) table.getModel();
                break;
            case "dep" :
                System.out.println("Table change dep");
                table.clearSelection();
                table = new JTable(new DefaultTableModel(null,depColumnNames ));  //
                tableModel = (DefaultTableModel) table.getModel();
                System.out.println(tableModel.getColumnCount());
                jsp = new JScrollPane(table);
                jsp.setBounds(100,100,50,50);
                getContentPane().add(jsp);
                break;
            default:
                table = new JTable(new DefaultTableModel(null,empColumnNames ));  //
                tableModel = (DefaultTableModel) table.getModel();
                //tableModel.set
                tableModel.addColumn("Column #" + "5");
                table.setModel(tableModel);

                tableModel.fireTableDataChanged();
                /*
                TableColumnModel columnModel = table.getColumnModel();
                int cnt = tableModel.getColumnCount();
                columnModel.addColumn(new TableColumn(6));
                System.out.println(columnModel.getColumnCount() );

        */


    }

    public class SimpleTableDemo  implements TableModelListener {

        public SimpleTableDemo() {
            table.getModel().addTableModelListener(this);

        }

        @Override
        public void tableChanged(TableModelEvent e) {
            int row = e.getFirstRow();
            int column = e.getColumn();
            DefaultTableModel model = (DefaultTableModel)e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);
            System.out.println("HERE");
        }
    }
}
