package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {

    private JLabel l1, l2, l3;
    private JTextField tf1;
    private JButton btn1;
    private JPasswordField p1;

    public StartFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        l1 = new JLabel("Login Form");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Login");

        btn1.addActionListener(this);

        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);

        add(l1);
        add(l2);
        add(tf1);
        add(l3);
        add(p1);
        add(btn1);

        setSize(600, 400);
        setLayout(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        tf1.setText("root");p1.setText("Buggati");
        String uname = tf1.getText();
        String pass = p1.getText();
        if(uname.equals("root") && pass.equals("Buggati"))
        {
            this.setVisible(false);
            MainPanel wel = new MainPanel();
            wel.setVisible(true);

            /* MainPanel wel = new MainPanel();
//                wel.setVisible(true);
//                JLabel label = new JLabel("Welcome:"+uname);
//                wel.getContentPane().add(label);*/

           // JLabel label = new JLabel(); // "Welcome:"+uname
         //   wel.getContentPane().add(label);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Incorrect login or password",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

}
