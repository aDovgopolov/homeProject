package view;

import conn.MyConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartFrame extends JFrame implements ActionListener {

    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;

    public StartFrame() {
        JFrame frame = new JFrame("Login Form");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        l1 = new JLabel("Login Form");
        l1.setForeground(Color.blue);
        l1.setFont(new Font("Serif", Font.BOLD, 20));

        l2 = new JLabel("Username");
        l3 = new JLabel("Password");
        tf1 = new JTextField();
        p1 = new JPasswordField();
        btn1 = new JButton("Login");

        btn1.addActionListener(e -> {
            String uname = tf1.getText();
            String pass = p1.getText();
            if(uname.equals("root") && pass.equals("Buggati"))
            {
               /* MainPanel wel = new MainPanel();
                wel.setVisible(true);
                JLabel label = new JLabel("Welcome:"+uname);
                wel.getContentPane().add(label);*/
               frame.setVisible(false);
                Frame frame1 = new Frame();
                MyConnection.setEntryData(uname, pass);
            }
            else
            {
                JOptionPane.showMessageDialog(this,"Incorrect login or password",
                        "Error",JOptionPane.ERROR_MESSAGE);
            }
        });


        l1.setBounds(100, 30, 400, 30);
        l2.setBounds(80, 70, 200, 30);
        l3.setBounds(80, 110, 200, 30);
        tf1.setBounds(300, 70, 200, 30);
        p1.setBounds(300, 110, 200, 30);
        btn1.setBounds(150, 160, 100, 30);

        frame.add(l1);
        frame.add(l2);
        frame.add(tf1);
        frame.add(l3);
        frame.add(p1);
        frame.add(btn1);

        frame.setSize(600, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae)
    {
        System.out.println("SUC");
        String uname = tf1.getText();
        String pass = p1.getText();
        if(uname.equals("root") && pass.equals("Buggati"))
        {
            MainPanel wel = new MainPanel();
            wel.setVisible(true);
            JLabel label = new JLabel("Welcome:"+uname);
            wel.getContentPane().add(label);
        }
        else
        {
            JOptionPane.showMessageDialog(this,"Incorrect login or password",
                    "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

}
