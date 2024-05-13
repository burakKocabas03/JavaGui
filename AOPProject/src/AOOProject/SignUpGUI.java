package AOOProject;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JCheckBox;
import java.awt.SystemColor;
import javax.swing.JPasswordField;

public class SignUpGUI extends JFrame {

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
		setTitle("LOGIN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 728, 609);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 158, 160));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel signUpGuiIcon = new JLabel(new ImageIcon(getClass().getResource("cat64.png")));
		signUpGuiIcon.setBounds(282, 16, 147, 119);
		contentPane.add(signUpGuiIcon);
		
		JPanel signGuiInputPanel = new JPanel();
		signGuiInputPanel.setBackground(new Color(112, 128, 144));
		signGuiInputPanel.setBounds(150, 167, 410, 409);
		contentPane.add(signGuiInputPanel);
		signGuiInputPanel.setLayout(null);
		
		JLabel signGuiuserNameLbl = new JLabel("USERNAME : ");
		signGuiuserNameLbl.setForeground(new Color(255, 255, 0));
		signGuiuserNameLbl.setBackground(new Color(238, 238, 238));
		signGuiuserNameLbl.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		signGuiuserNameLbl.setBounds(6, 50, 87, 27);
		signGuiInputPanel.add(signGuiuserNameLbl);
		
		JLabel signGuipasswordLbl = new JLabel("PASSWORD :");
		signGuipasswordLbl.setForeground(new Color(255, 255, 0));
		signGuipasswordLbl.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		signGuipasswordLbl.setBounds(6, 125, 102, 27);
		signGuiInputPanel.add(signGuipasswordLbl);
		
		JLabel signGuilblSıgnUP = new JLabel("SIGN UP");
		signGuilblSıgnUP.setForeground(new Color(255, 255, 0));
		signGuilblSıgnUP.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
		signGuilblSıgnUP.setBounds(154, 6, 102, 47);
		signGuiInputPanel.add(signGuilblSıgnUP);
		
		signGuiusernameTxtField = new JTextField();
		signGuiusernameTxtField.setBounds(6, 75, 204, 47);
		signGuiInputPanel.add(signGuiusernameTxtField);
		signGuiusernameTxtField.setColumns(10);
		
		JButton signGuibtnLogin = new JButton("SIGN IN");
		signGuibtnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		signGuibtnLogin.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		signGuibtnLogin.setOpaque(true);
		signGuibtnLogin.setBackground(new Color(95, 158, 160));
		signGuibtnLogin.setForeground(new Color(255, 255, 0));
		signGuibtnLogin.setBounds(6, 295, 121, 47);
		signGuibtnLogin.setBorderPainted(false);
		signGuiInputPanel.add(signGuibtnLogin);
		
		signGuipasswordField = new JPasswordField();
		signGuipasswordField.setBounds(6, 150, 204, 47);
		signGuiInputPanel.add(signGuipasswordField);
		
		JLabel signGuilblVerfyPassword = new JLabel("VERIFY PASSWORD");
		signGuilblVerfyPassword.setForeground(Color.YELLOW);
		signGuilblVerfyPassword.setFont(new Font("Lantinghei TC", Font.PLAIN, 12));
		signGuilblVerfyPassword.setBackground(UIManager.getColor("Button.background"));
		signGuilblVerfyPassword.setBounds(6, 200, 134, 27);
		signGuiInputPanel.add(signGuilblVerfyPassword);
		
		signGuiVerifyPasswordField = new JPasswordField();
		signGuiVerifyPasswordField.setBounds(6, 225, 204, 47);
		signGuiInputPanel.add(signGuiVerifyPasswordField);
		
		JLabel signGuiPurrConnect = new JLabel("PURR CONNECT");
		signGuiPurrConnect.setBounds(261, 125, 189, 34);
		contentPane.add(signGuiPurrConnect);
		signGuiPurrConnect.setBackground(new Color(255, 255, 0));
		signGuiPurrConnect.setForeground(new Color(255, 255, 0));
		signGuiPurrConnect.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 22));
	}
}
