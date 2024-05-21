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
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private JTextField usernameTextField;
    private JPasswordField passwordField;

    public LoginGUI() {
        setBackground(UIManager.getColor("EditorPane.background"));
        setResizable(false);
        setTitle("LOGIN");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 896, 804);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(95, 158, 160));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        setContentPane(contentPane);
        contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 240, 245));
		panel.setBounds(0, 109, 899, 211);
		contentPane.add(panel);
		panel.setLayout(null);
		

        JLabel lbl_icon = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));
        lbl_icon.setBounds(0, 10, 228, 189);
        panel.add(lbl_icon);
        lbl_icon.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel PurrConnectLabel = new JLabel("PURR CONNECT");
		PurrConnectLabel.setBounds(200, 45, 611, 110);
		panel.add(PurrConnectLabel);
		PurrConnectLabel.setForeground(Color.BLACK);
		PurrConnectLabel.setFont(new Font("Unispace", Font.PLAIN, 80));
		PurrConnectLabel.setHorizontalAlignment(SwingConstants.LEFT);
        
        JLabel SubLineLabel = new JLabel("Connect to People");
		SubLineLabel.setBounds(309, 145, 282, 45);
		panel.add(SubLineLabel);
		SubLineLabel.setFont(new Font("Candara", Font.BOLD, 17));
		SubLineLabel.setHorizontalAlignment(SwingConstants.CENTER);



		JLabel lblNewLabel_2 = new JLabel("Username  :");
		lblNewLabel_2.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		lblNewLabel_2.setBounds(132, 349, 206, 64);
		contentPane.add(lblNewLabel_2);
		
		JLabel PassLabel = new JLabel("Password  :");
		PassLabel.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		PassLabel.setBounds(132, 425, 191, 45);
		contentPane.add(PassLabel);

        
        
        usernameTextField = new JTextField();
		usernameTextField.setBounds(335, 374, 228, 31);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		 passwordField = new JPasswordField();
			passwordField.setBounds(335, 438, 228, 31);
			contentPane.add(passwordField);
		
JCheckBox checkRememberMe = new JCheckBox("Remember Me");
        
       
        
  

        JButton LoginButton = new JButton("LOGIN");
		LoginButton.setBackground(Color.PINK);
		LoginButton.setFont(new Font("Unispace", Font.BOLD, 24));
		LoginButton.setBounds(369, 520, 148, 45);
		contentPane.add(LoginButton);
        LoginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usernameTextField.getText().length() == 0 || passwordField.getPassword().length == 0) {
                    Helper.showMsg("fill");

                } else {
                    try {

                        Connection conn = dataBaseConnection.connection();

                        Statement stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery("SELECT * FROM admin");
                        while (rs.next()) {

                            if (usernameTextField.getText().equals(rs.getString("userName")) && passwordField.getText().equals(rs.getString("password"))) {
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
       

        JLabel CreateAnAccountLabel = new JLabel("Create an account");
		CreateAnAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		CreateAnAccountLabel.setFont(new Font("Unispace", Font.PLAIN, 17));
		CreateAnAccountLabel.setBounds(335, 660, 228, 45);
		contentPane.add(CreateAnAccountLabel);
		
		JButton SignInButton = new JButton("SIGN IN");
		SignInButton.setBackground(Color.PINK);
		SignInButton.setFont(new Font("Unispace", Font.BOLD, 24));
		SignInButton.setBounds(369, 605, 148, 45);
		contentPane.add(SignInButton);
        SignInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SignUpGUI sgnUp = new SignUpGUI();
                sgnUp.setVisible(true);
                dispose();

            }
        });
       
        JLabel BackGroundLabel = new JLabel(new ImageIcon(getClass().getResource("catBackRound.jpg")));
    	BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	BackGroundLabel.setBounds(0, 0, 899, 773);
    	contentPane.add(BackGroundLabel);

           

      
    }
   
       

    // Diğer metotlar burada devam eder...

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginGUI frame = new LoginGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
