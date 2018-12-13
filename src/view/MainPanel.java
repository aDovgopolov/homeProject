package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame implements ActionListener {

    private JTable table = null;
    private JScrollPane jsp;
    private String[] columnNames = {"LDAP_LOGIN", "REP_FAM", "REP_NAME", "REP_OT", "REP_BIRTH", "REP_POSIT"};
    private JButton btnRead;
    private JButton btnUPD;
    private JButton btnDelete;
    private JButton btnCreate;
    private JButton btnSearch;
    private JComboBox comboBox;

    String[] items = {
            "CrudRepDep",
            "CrudRepEmp",
            "CrudRepPosit"
    };

    MainPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Welcome");
        setSize(600, 300);

        initButtonPanel();
        initTablePanel();
        initTableCheckbox();

        setVisible(true);
    }

    public void initButtonPanel(){
        JPanel grid = new JPanel();
        GridLayout gl = new GridLayout(1, 0, 5, 12);
        grid.setLayout(gl);

        btnRead = new JButton("Получить базу");
        btnUPD= new JButton("Обновить");
        btnCreate= new JButton("Создать");
        btnDelete= new JButton("Удалить");
        btnSearch= new JButton("Поиск");

        btnRead.addActionListener(this);
        btnUPD.addActionListener(this);
        btnCreate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnSearch.addActionListener(this);

        grid.add( btnRead );
        grid.add( btnUPD );
        grid.add( btnCreate );
        grid.add( btnDelete );
        grid.add( btnSearch );

        getContentPane().add( grid , BorderLayout.NORTH);
    }

    public void initTablePanel(){
        table = new JTable(new DefaultTableModel(null,columnNames ));

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.addRow(new String[]{"DN118899DAG7","", "", "", "1983-09-17", ""});
        tableModel.fireTableDataChanged();
        jsp = new JScrollPane(table);
        jsp.setBounds(100,100,50,50);
        getContentPane().add(jsp);
    }

    public void initTableCheckbox() {
        Font font = new Font("Verdana", Font.PLAIN, 18);

        Container content = getContentPane();

        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        //final JLabel label = new JLabel(" ");
        //label.setAlignmentX(LEFT_ALIGNMENT);
       // label.setFont(font);
       // content.add(label);



        comboBox = new JComboBox(items);
        comboBox.setFont(font);
        comboBox.setAlignmentX(LEFT_ALIGNMENT);

        ActionListener actionListener = e -> {
            JComboBox box = (JComboBox)e.getSource();
            String item = (String)box.getSelectedItem();
            // label.setText(item);
        };

        comboBox.addActionListener(actionListener);
        content.add(comboBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();
        if(source == btnRead)        {
            System.out.println("btnRead");

            System.out.println(comboBox.getSelectedItem());
        }else if(source == btnUPD){
            System.out.println("btnUPD");
        }else if(source == btnCreate){
            System.out.println("btnCreate");
        }else if(source == btnDelete){
            System.out.println("btnDelete");
        }else if(source == btnSearch){
            System.out.println("btnSearch");
        }
    }
}
