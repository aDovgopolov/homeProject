package view;

import controler.Controler;
import controler.IControler;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainPanel extends JFrame{

    private IControler iControler;
    private JTable table = null;
    private JScrollPane jsp;
    private String[] columnNames = {"LDAP_LOGIN", "REP_FAM", "REP_NAME", "REP_OT", "REP_BIRTH", "REP_POSIT"};
    private JButton btnRead;
    private JButton btnUPD;
    private JButton btnDelete;
    private JButton btnCreate;
    private JButton btnSearch;
    private JComboBox comboBox;
    private DefaultTableModel tableModel;

//    String[] items = {
//            "Табл. по сотрудникам",
//            "Табл. по департаменту",
//            "Табл. по должностям"
//    };
        String[] items = {
            "dep",
            "emp",
            "posit"
    };


    MainPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Welcome");
        setSize(600, 300);

        initButtonPanel();
        initTablePanel();
        initTableCheckbox();

        iControler = new Controler(tableModel);
        System.out.println(comboBox.getSelectedItem().toString());
        btnRead.addActionListener(v->{iControler.read(comboBox.getSelectedItem().toString());});
        btnUPD.addActionListener(v->{iControler.update();});
        btnDelete.addActionListener(v->{iControler.delete();});
        btnCreate.addActionListener(v->{iControler.create();});
        btnSearch.addActionListener(v->{iControler.search();});

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

        grid.add( btnRead );
        grid.add( btnUPD );
        grid.add( btnCreate );
        grid.add( btnDelete );
        grid.add( btnSearch );

        getContentPane().add( grid , BorderLayout.NORTH);
    }

    private void initTablePanel(){

        table = new JTable(new DefaultTableModel(null,columnNames ));
        tableModel = (DefaultTableModel) table.getModel();
        jsp = new JScrollPane(table);
        jsp.setBounds(100,100,50,50);
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
            String item = (String)box.getSelectedItem();
            System.out.println(item);
        };

        comboBox.addActionListener(actionListener);
        content.add(comboBox);
    }
}
