package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.dataBaseConnection;
import Model.Users;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;

public class ApplicationArea extends JFrame {
	private DefaultTableModel friendModel = null;
	private String [] friendsArray = null;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Users user = new Users();
	private JTextField addFriendtextField;
	private JTable friendListTable;
	private JComboBox<String> comboBox;
    private DefaultComboBoxModel<String> comboBoxModel;
	
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
		// bu kısım friendList içindi
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
		
		try {
			ArrayList<String>userNames = new ArrayList<>();
			Connection conn = dataBaseConnection.connection();
	        Statement stmt;
	        ResultSet rs;
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery("SELECT * FROM admin");
	        while(rs.next()) {
	        	userNames.add(rs.getString("userName"));
	        }
	        comboBoxModel = new DefaultComboBoxModel<>(new Vector<>(userNames));
	        comboBox = new JComboBox<>(comboBoxModel);
	        
	        comboBox.setEditable(true);
	        
	        
	       JTextField comboBoxTextField = (JTextField)comboBox.getEditor().getEditorComponent();
	       comboBoxTextField.addKeyListener(new KeyAdapter(){
	            @Override
	            public void keyReleased(KeyEvent e) {
	                String input = comboBoxTextField.getText();
	                if (input.isEmpty()) {
	                    setModel(new DefaultComboBoxModel<>(new Vector<>(userNames)), "");
	                } else {
	                    DefaultComboBoxModel<String> filteredModel = getFilteredModel(userNames, input);
	                    if (filteredModel.getSize() > 0) {
	                        setModel(filteredModel, input);
	                        comboBox.showPopup();
	                    } else {
	                        comboBox.hidePopup();
	                    }
	                }
	            }
	        });	
	       
	       

	        
	        
	        
	        
	        
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        JTextField comboBoxtextField = (JTextField) comboBox.getEditor().getEditorComponent();
        

        
		
		
		
		
		
		
		
		
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(110, 176, 389, 405);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		
		JPanel panel = new JPanel();
		panel.setBounds(110, 100, 389, 77);
		contentPane.add(panel);
		
		JButton buttonPURR = new JButton("PURR");
	
		
		JTextArea blogPurrArea = new JTextArea();
		 blogPurrArea.setPreferredSize(new Dimension(blogPurrArea.getPreferredSize().width, 70));
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
		feedProccess(panel_1);
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LoginGUI lgnGui = new LoginGUI();
				lgnGui.setVisible(true);
                dispose();
			}
		});
		btnLogOut.setBounds(551, 6, 117, 29);
		contentPane.add(btnLogOut);
		
		
		
		
	
	}
	
	
	private void updateFriendsList() {
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
	
	private static JPanel createFeedItem(String username, String message) {
        JPanel feedItemPanel = new JPanel();
        feedItemPanel.setLayout(new BorderLayout());
        feedItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Kullanıcı adı
        JLabel userLabel = new JLabel(username);
        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
        feedItemPanel.add(userLabel, BorderLayout.NORTH);

        // Mesaj
        JTextArea messageArea = new JTextArea(message);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setEditable(false);

        messageArea.setPreferredSize(new Dimension(messageArea.getPreferredSize().width, 100));
        feedItemPanel.add(messageArea, BorderLayout.CENTER);
        feedItemPanel.add(messageArea, BorderLayout.CENTER);

     
        

        return feedItemPanel;
    }
	
	public void feedProccess(JPanel mainpanel) {
		JPanel feedItemPanel = new JPanel();
		ArrayList<String>blogStrArray = new ArrayList<>();
		
		
try {
        Connection conn = dataBaseConnection.connection();

        Statement stmt = conn.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT* FROM admin");

        
        while(rs.next()) {
        	String username = rs.getString("userName");
        	String blogStr = rs.getString("my_blog");
        	String []strArray = blogStr.split(",");
        	if (!blogStr.equals("") ) {
        		System.out.println("ife girdin mi  girdiysen evet yaz");
        	for(String a : strArray) {
        		System.out.println(a);
        		blogStrArray.add(a);
        	}
        	
        	
        	for(int i = 0; i<blogStrArray.size();i++) {
        		
        		feedItemPanel = createFeedItem(username,blogStrArray.get(i));
        		mainpanel.add(feedItemPanel);
        	}
        	blogStrArray.clear();
  
        
}
}
}
catch(SQLException e1) {
	
	e1.printStackTrace();
}
		
	}
	private void setModel(DefaultComboBoxModel<String> mdl, String str) {
		JTextField textField = null;
        comboBox.setModel(mdl);
        textField.setText(str);
        comboBox.setSelectedIndex(-1);
        textField.setSelectionStart(str.length());
        textField.setSelectionEnd(str.length());
    }

    private DefaultComboBoxModel<String> getFilteredModel(ArrayList<String> items, String input) {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (String item : items) {
            if (item.toLowerCase().startsWith(input.toLowerCase())) {
                model.addElement(item);
            }
        }
        return model;
    }
}
	

