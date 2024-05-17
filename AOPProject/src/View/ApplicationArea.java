package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Users;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ApplicationArea extends JFrame {
	private DefaultTableModel friendModel = null;
	private String [] friendsArray = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Users user = new Users();
	private JTextField addFriendtextField;
	private JTable friendListTable;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ApplicationArea frame = new ApplicationArea(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public ApplicationArea(Users user) {
		this.user =user;
		friendModel = new DefaultTableModel();
		String[]columName = new String[1];
		columName[0] = "USERNAME";
		friendModel.setColumnIdentifiers(columName);
		friendsArray = new String[1];
		try {
			for(int i = 0;i<user.getFriendList().size();i++) {
				friendsArray[0] = user.getFriendList().get(i);
				friendModel.addRow(friendsArray);
				
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 671, 633);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addFriendtextField = new JTextField();
		addFriendtextField.setBounds(538, 200, 130, 26);
		contentPane.add(addFriendtextField);
		addFriendtextField.setColumns(10);
		
		JButton btnAddFriend = new JButton("Add Friend");
		btnAddFriend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.addFriend(addFriendtextField.getText());
				addFriendtextField.setText(null);
				updateFriendsList();
				
			}
		});
		btnAddFriend.setBounds(551, 159, 117, 29);
		contentPane.add(btnAddFriend);
		
		JScrollPane friendListPane = new JScrollPane();
		friendListPane.setBounds(538, 257, 130, 324);
		contentPane.add(friendListPane);
		
		friendListTable = new JTable(friendModel);
		friendListPane.setViewportView(friendListTable);
		
		JLabel friendListLabel = new JLabel("Friends List");
		friendListLabel.setBounds(563, 227, 81, 16);
		contentPane.add(friendListLabel);
		
		JPanel feedPanel = new JPanel();
		feedPanel.setBounds(110, 179, 389, 406);
		contentPane.add(feedPanel);
		feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.X_AXIS));
		
		JScrollPane feedScrollPanel = new JScrollPane();
		feedPanel.add(feedScrollPanel);
		
		JPanel panel = new JPanel();
		panel.setBounds(110, 100, 389, 77);
		contentPane.add(panel);
		
		JButton buttonPURR = new JButton("PURR");
	
		
		JTextArea blogPurrArea = new JTextArea();
		blogPurrArea.setLineWrap(true);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(307, Short.MAX_VALUE)
					.addComponent(buttonPURR)
					.addContainerGap())
				.addComponent(blogPurrArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addComponent(blogPurrArea, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPURR, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		buttonPURR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String blogMessage = blogPurrArea.getText();
				user.myPurrToDtbs(blogMessage);
				blogPurrArea.setText(null);
				
				
				
				
			}
		});
		
		
	}
	
	
	public void updateFriendsList() {
		DefaultTableModel clearModel = (DefaultTableModel) friendListTable.getModel();
		clearModel.setRowCount(0);
		try {
			System.out.print("UpdataFriendin içindeiyim");
			for(int i = 0;i<user.getFriendList().size();i++) {
				friendsArray[0] = user.getFriendList().get(i);
				friendModel.addRow(friendsArray);
				
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
