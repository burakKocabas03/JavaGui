package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import Helper.*;
import Model.Users;

import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.JPasswordField;

public class LoginGUI extends JFrame {
    private static LoginGUI instance = null;

    private JPanel contentPane;
    private JTextField usernameTxtField;
    private JPasswordField passwordField;

    public static LoginGUI getInstance() {
        if (instance == null) {
            instance = new LoginGUI();
        }
        return instance;
    }

    private LoginGUI() {
        setBackground(UIManager.getColor("EditorPane.background"));
        setResizable(false);
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 728, 609);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(95, 158, 160));
        contentPane.setBorder(null);

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lbl_icon = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));
        lbl_icon.setBounds(264, 6, 189, 141);
        contentPane.add(lbl_icon);

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(112, 128, 144));
        inputPanel.setBounds(148, 166, 410, 409);
        contentPane.add(inputPanel);
        inputPanel.setLayout(null);

        JLabel userNameLbl = new JLabel("USERNAME : ");
        userNameLbl.setForeground(new Color(255, 255, 0));
        userNameLbl.setBackground(new Color(238, 238, 238));
        userNameLbl.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
        userNameLbl.setBounds(6, 84, 87, 27);
        inputPanel.add(userNameLbl);

        JLabel passwordLbl = new JLabel("PASSWORD :");
        passwordLbl.setForeground(new Color(255, 255, 0));
        passwordLbl.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
        passwordLbl.setBounds(6, 168, 102, 27);
        inputPanel.add(passwordLbl);

        JLabel loginLbl = new JLabel("LOGIN");
        loginLbl.setForeground(new Color(255, 255, 0));
        loginLbl.setFont(new Font(".AppleSystemUIFont", Font.BOLD | Font.ITALIC, 22));
        loginLbl.setBounds(151, 6, 79, 47);
        inputPanel.add(loginLbl);

        usernameTxtField = new JTextField();
        usernameTxtField.setBounds(6, 109, 204, 47);
        inputPanel.add(usernameTxtField);
        usernameTxtField.setColumns(10);

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usernameTxtField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                    Helper.showMsg("fill");

                } else {
                    try {

                        Connection conn = dataBaseConnection.connection();

                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM admin");
                        while (rs.next()) {

                            if (usernameTxtField.getText().equals(rs.getString("userName")) && passwordField.getText().equals(rs.getString("password"))) {
                                Users user = new Users();
                                user.setId(rs.getInt("id"));
                                user.setUserName(rs.getString("userName"));
                                user.setPassWord(rs.getString("password"));	
                                ApplicationArea appArea = new ApplicationArea(user);
                                appArea.setVisible(true);
                                dispose();
                                break;

                            }

                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        btnLogin.setOpaque(true);
        btnLogin.setBackground(new Color(95, 158, 160));
        btnLogin.setForeground(new Color(255, 255, 0));
        btnLogin.setBounds(6, 250, 121, 47);
        btnLogin.setBorderPainted(false);
        inputPanel.add(btnLogin);

        JLabel lblNewLabel = new JLabel("I don't have an account");
        lblNewLabel.setForeground(new Color(255, 255, 0));
        lblNewLabel.setBounds(20, 366, 146, 16);
        inputPanel.add(lblNewLabel);

        JButton btnSıgnUp = new JButton("SIGN UP");
        btnSıgnUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUpGUI sgnUp = new SignUpGUI();
                sgnUp.setVisible(true);
                dispose();

            }
        });
        btnSıgnUp.setBackground(new Color(95, 158, 160));
        btnSıgnUp.setOpaque(true);
        btnSıgnUp.setForeground(new Color(255, 255, 0));
        btnSıgnUp.setBorderPainted(false);
        btnSıgnUp.setBounds(194, 352, 121, 47);
        inputPanel.add(btnSıgnUp);

        JCheckBox checkRememberMe = new JCheckBox("Remember Me");
        checkRememberMe.setForeground(new Color(255, 255, 0));
        checkRememberMe.setBounds(6, 309, 128, 23);
        inputPanel.add(checkRememberMe);

        passwordField = new JPasswordField();
        passwordField.setBounds(6, 191, 204, 47);
        inputPanel.add(passwordField);

        JLabel purrConnectLbl = new JLabel("PURR CONNECT");
        purrConnectLbl.setBackground(new Color(255, 255, 0));
        purrConnectLbl.setBounds(264, 132, 189, 34);
        contentPane.add(purrConnectLbl);
        purrConnectLbl.setForeground(new Color(255, 255, 0));
        purrConnectLbl.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
    }

    // Diğer metotlar burada devam eder...

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI frame = LoginGUI.getInstance();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
