package view;

import controler.Controller;
import controler.IControler;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static javax.swing.JOptionPane.showMessageDialog;

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
    private String item = "emp";
    private final String[] items = {"emp", "dep", "posit"};
    JComboBox<String> combo = new JComboBox<>(new String[] { "Manage", "Gamer", "Driver"});

    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField filterText;

    MainPanel() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Welcome");
        setSize(700, 400);

        initButtonPanel();
        initTableCheckbox();
        initTablePanel();
        initFilterTextField();
        iControler = new Controller(tableModel);

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

        btnRead.addActionListener(  v-> new BtnReadListener().actionPerformed(v));
        btnDelete.addActionListener(v-> new BtnDeleteListener().actionPerformed(v));
        btnUPD.addActionListener(   v-> new BtnUpdateListener().actionPerformed(v));
        btnCreate.addActionListener(v-> new BtnCreateListener().actionPerformed(v));
        btnSearch.addActionListener(v-> new BtnSearchListener().actionPerformed(v));

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
        table.setDefaultEditor(Object.class, null);
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
            item = (String)box.getSelectedItem();
        };

        comboBox.addActionListener(actionListener);
        content.add(comboBox);
    }

    private void initFilterTextField() {

        Container content = getContentPane();
        //this time just for emp table
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);

        JLabel l1 = new JLabel("Filter Text:", SwingConstants.TRAILING);
        filterText = new JTextField();

        Font font = new Font("Verdana", Font.PLAIN, 18);
        filterText.setFont(font);
        filterText.setAlignmentX(LEFT_ALIGNMENT);

        filterText.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });

        content.add(l1);
        content.add(filterText);

    }

    private void newFilter() {
        System.out.println("Filter");
        RowFilter<DefaultTableModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(filterText.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf);
    }

    public class BtnReadListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            setDefaultTableModel();
            iControler.read((DefaultTableModel) table.getModel(), item);
        }
    }

    public class BtnDeleteListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String str = "";
            int idx = table.getSelectedRow();

            if(idx == -1) {
                JOptionPane.showMessageDialog(table, "Выберите записи для удаления");
            }else{
                if(comboBox.getSelectedItem().toString().equals("dep")){
                    str = (String) table.getModel().getValueAt(idx,1);
                }else{
                    str = (String) table.getModel().getValueAt(idx,0);
                }
                int reply = JOptionPane.showConfirmDialog(table, "Удалить из базы " + str);

                if(reply == 0) iControler.delete((DefaultTableModel) table.getModel(), item, str, idx);
            }
        }
    }

    public class BtnUpdateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            DefaultTableModel tableModel1 = checkDefaultTableModel();
            JTable table1 = new JTable(tableModel1);

            if(table.getSelectedRow() == -1){
                JOptionPane.showMessageDialog(table, "Выберите запись для обновления");
            }else{
                tableModel1.addRow((Object[]) null);
                for(int i = tableModel1.getColumnCount() - 1; i >= 0; i--){
                    tableModel1.setValueAt(table.getModel().
                            getValueAt(table.getSelectedRow(), i),0,i);
                }

                setEditors(table1);
                JScrollPane jsp =  new JScrollPane(table1);
                jsp.setBounds(100,100,0,0);
                jsp.setPreferredSize(new Dimension(700,100));

                ArrayList<String> arr = new ArrayList<>();
                for (int j = 0; j < table.getModel().getColumnCount(); j++){
                    arr.add((String) table.getModel().getValueAt(table.getSelectedRow(),j));
                }

                int reply = JOptionPane.showConfirmDialog(table,jsp, "Обновить данные", JOptionPane.YES_NO_OPTION);

                System.out.println("Updated " + updateRowValues(tableModel1, arr) + " values of the row");

                if(reply == 0){

                    for(int i = tableModel1.getColumnCount() - 1; i >= 0; i--){
                        table.getModel().setValueAt(tableModel1.
                                getValueAt(0, i),table.getSelectedRow() ,i);
                    }
                }
            }
        }

        private int updateRowValues(DefaultTableModel tableModel1, ArrayList arr) {
            int i = 0;

            for (int k = 0; k < tableModel1.getColumnCount(); k++) {
                if (arr.get(k).equals(tableModel1.getValueAt(0, k))) {
                    System.out.println(arr.get(k));
                    System.out.println("index" + k + " is not updated");
                } else {
                    String data = comboBox.getSelectedItem().toString().equals("dep") ?
                            String.valueOf(tableModel1.getValueAt(0, 1)) :
                            String.valueOf(tableModel1.getValueAt(0, 0));
                    System.out.println("index" + k + " is Updated and new value = " + tableModel1.getValueAt(0, k)
                                       + ", columnName = " + tableModel1.getColumnName(k));
                    i++;

                    iControler.update(tableModel1, comboBox.getSelectedItem().toString(),
                            data,
                            tableModel1.getColumnName(k),
                            String.valueOf(tableModel1.getValueAt(0, k)));
                }
            }

            return i;
        }

        private void setEditors(JTable table1){
            switch (comboBox.getSelectedItem().toString()) {
                case "emp":
                    DefaultCellEditor editor = new DefaultCellEditor(combo);
                    table1.getColumnModel().getColumn(0).setCellEditor(null);
                    table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(5).setCellEditor(editor);
                    break;
                case "dep":
                    table1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(1).setCellEditor(null);
                    table1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
                    break;
                default:
                    table1.getColumnModel().getColumn(0).setCellEditor(null);
                    table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
                    break;
            }
        }
    }

    public class BtnCreateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            DefaultTableModel tableModel1 = checkDefaultTableModel();
            JTable table1 = new JTable(tableModel1);
            tableModel1.addRow((Object[]) null);

            table1.setDefaultEditor(Date.class, new DateCellEditor());
            setEditors(table1);
            JScrollPane jsp = new JScrollPane(table1);
            jsp.setBounds(100, 100, 0, 0);
            jsp.setPreferredSize(new Dimension(700, 100));

            int reply = JOptionPane.showConfirmDialog(table, jsp, "Добавить строку", JOptionPane.YES_NO_OPTION);

            String[] data1 = new String[tableModel1.getColumnCount()];
            if (reply == 0) {
                for (int i = 0; i < tableModel1.getColumnCount(); i++) {
                    data1[i] = String.valueOf(tableModel1.getValueAt(0, i));
                }
                iControler.create(comboBox.getSelectedItem().toString(), data1);
                DefaultTableModel tt = (DefaultTableModel) table.getModel();
                tt.addRow(data1);
                tt.fireTableDataChanged();
            }
        }

        private void setEditors(JTable table1){
            switch (comboBox.getSelectedItem().toString()) {
                case "emp":
                    DefaultCellEditor editor = new DefaultCellEditor(combo);
                    table1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
                    //index 4 = Date TODO
                    table1.getColumnModel().getColumn(5).setCellEditor(editor);
                    break;
                case "dep":
                    table1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()));
                    break;
                default:
                    table1.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JTextField()));
                    table1.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JTextField()));
                    break;
            }
        }

    }

    public class BtnSearchListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //TODO
            //iControler.search();
        }
    }

    private DefaultTableModel checkDefaultTableModel(){
        DefaultTableModel tt;
        switch (comboBox.getSelectedItem().toString()) {
            case "emp":
                tt = new DefaultTableModel(null,empColumnNames );
                break;
            case "dep":
                tt = new DefaultTableModel(null,depColumnNames );
                break;
            default:
                tt = new DefaultTableModel(null,positColumnNames );
                break;
        }
        return tt;
    }

    private void setDefaultTableModel(){

        switch (comboBox.getSelectedItem().toString()) {
            case "emp":
                table.setModel(tableModel);
                break;
            case "dep":
                table.setModel(tableDepModel);
                break;
            default:
                table.setModel(tablePositModel);
                break;
        }
    }

    class DateCellEditor extends AbstractCellEditor implements TableCellEditor{

        private JSpinner editor;

        DateCellEditor() {
            SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
            editor = new JSpinner(model);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            editor.setValue(value);
            return editor;
        }

        public Object getCellEditorValue() {
            return editor.getValue();
        }
    }

}
