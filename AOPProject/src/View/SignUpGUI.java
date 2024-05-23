package View;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Users;

public class SignUpGUI extends JFrame {
	dataBaseConnection dtbs = new dataBaseConnection();
	Connection conn =dtbs.connection() ;
	Statement st ;
	PreparedStatement ps;
	

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField signGuiusernameTxtField;
	private JPasswordField signGuipasswordField;
	private JPasswordField signGuiVerifyPasswordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpGUI frame = new SignUpGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUpGUI() {
		setBackground(UIManager.getColor("EditorPane.background"));
		setResizable(false);
		setTitle("SIGN UP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 902, 796);
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
		
		JLabel signUpGuiIcon = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));
		signUpGuiIcon.setBounds(0, 10, 228, 189);
		panel.add(signUpGuiIcon);
		signUpGuiIcon.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		
		
		
		JLabel signGuiuserNameLbl = new JLabel("USERNAME :");
		signGuiuserNameLbl.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		signGuiuserNameLbl.setBounds(174, 352, 203, 64);
		contentPane.add(signGuiuserNameLbl);
		
		JLabel signGuipasswordLbl = new JLabel("PASSWORD :");
	
		signGuipasswordLbl.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		signGuipasswordLbl.setBounds(177, 428, 223, 45);
		contentPane.add(signGuipasswordLbl);
		
		JLabel signGuilblVerfyPassword = new JLabel("CONFIRM PASSWORD :");
		
		signGuilblVerfyPassword.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		
		signGuilblVerfyPassword.setBounds(18, 492, 359, 45);
		contentPane.add(signGuilblVerfyPassword);
		
		JLabel signGuilblSıgnUP = new JLabel("SIGN UP");
		
		signGuilblSıgnUP.setFont(new Font("Haettenschweiler", Font.ITALIC, 33));
		signGuilblSıgnUP.setBounds(321, 6, 179, 47);
		contentPane.add(signGuilblSıgnUP);
		
		signGuiusernameTxtField = new JTextField();
		signGuiusernameTxtField.setBounds(389, 376, 209, 33);
		contentPane.add(signGuiusernameTxtField);
		signGuiusernameTxtField.setColumns(10);
		
		signGuipasswordField = new JPasswordField();
		signGuipasswordField.setBounds(389, 440, 209, 33);
		contentPane.add(signGuipasswordField);
		
		signGuiVerifyPasswordField = new JPasswordField();
		signGuiVerifyPasswordField.setBounds(389, 506, 209, 33);
		contentPane.add(signGuiVerifyPasswordField);
		
		JButton signGuibtnLogin = new JButton("SIGN IN");
		signGuibtnLogin.setBackground(new Color(250, 250, 210));
		signGuibtnLogin.setFont(new Font("Unispace", Font.BOLD, 24));
		signGuibtnLogin.setBounds(422, 584, 148, 45);
		contentPane.add(signGuibtnLogin);
		
		signGuibtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(signGuiusernameTxtField.getText().length()==0 ||signGuipasswordField.getPassword().length == 0||signGuiVerifyPasswordField.getPassword().length==0 ) {
					Helper.showMsg("fill");
					
				}
				else {
					if( !signGuipasswordField.getText().equals(signGuiVerifyPasswordField.getText())) {
						JOptionPane.showMessageDialog(null,"Password and VerifyPassword are not same try again","Message",JOptionPane.INFORMATION_MESSAGE);
						
					}
					else {
						String query  ="INSERT INTO user (userName,password,friend_ids) VALUES (?,?,?)" ;
						try {
							
							Users user = new Users();
							user.setUserName(signGuiusernameTxtField.getText());
							user.setPassWord(signGuipasswordField.getText());
							st=conn.createStatement();
							ps = conn.prepareStatement(query);
							ps.setString(1,signGuiusernameTxtField.getText() );
							ps.setString(2,signGuiVerifyPasswordField.getText());
							ps.setString(3, "");
							
							ps.executeUpdate();
                           LoginGUI lgnGui = new LoginGUI();
                           lgnGui.setVisible(true);
                            dispose();
                          
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
						
					}
					
					
				}
				
			}
		});
		
				
		JLabel BackGroundLabel = new JLabel(new ImageIcon(getClass().getResource("catBackRound.jpg")));
    	BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
    	BackGroundLabel.setBounds(0, 0, 899, 773);
    	contentPane.add(BackGroundLabel);
		
		
		
		
		
	}
}
