package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Helper.dataBaseConnection;
import Model.Groups;
import Model.Users;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;

public class GroupGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static private Users user = new Users();
	static private Groups group = new Groups() ;
	private JTable memberTable;
    private DefaultTableModel memberTableModel;
    

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GroupGUI frame = new GroupGUI(user,group);
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
	public GroupGUI(Users user,Groups group) {
		this.user = user;
		this.group = group;
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 775, 614);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane feedScrollPane = new JScrollPane();
		feedScrollPane.setBounds(189, 225, 401, 361);
		contentPane.add(feedScrollPane);

		JPanel feedPanel = new JPanel();
		feedScrollPane.setViewportView(feedPanel);
		feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
		
		JLabel lblNewLabel = new JLabel(group.getGroupName().toUpperCase() + " GROUP");
		lblNewLabel.setBounds(284, 6, 233, 76);
		contentPane.add(lblNewLabel);
		
		
		JPanel PurrPanel = new JPanel();
		PurrPanel.setBounds(189, 125, 396, 101);
		contentPane.add(PurrPanel);
		
		JButton buttonPURR = new JButton("PURR");
		
			
			JTextArea blogPurrArea = new JTextArea();
			blogPurrArea.setPreferredSize(new Dimension(blogPurrArea.getPreferredSize().width, 70));
			blogPurrArea.setLineWrap(true);
		GroupLayout gl_PurrPanel = new GroupLayout(PurrPanel);
		gl_PurrPanel.setHorizontalGroup(
			gl_PurrPanel.createParallelGroup(Alignment.TRAILING)
				.addComponent(blogPurrArea, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
				.addGroup(gl_PurrPanel.createSequentialGroup()
					.addContainerGap(314, Short.MAX_VALUE)
					.addComponent(buttonPURR)
					.addContainerGap())
		);
		gl_PurrPanel.setVerticalGroup(
			gl_PurrPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_PurrPanel.createSequentialGroup()
					.addComponent(blogPurrArea, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPURR, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE)
					.addContainerGap())
		);
		PurrPanel.setLayout(gl_PurrPanel);
		
		
		buttonPURR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String blogMessage = blogPurrArea.getText();
				String query  = "INSERT INTO groupPost (groupId,user_id,groupPost) VALUES (?,?,?)" ;
				try { 
					Connection conn;
					Statement stmt;
					PreparedStatement ps;
					
					conn = dataBaseConnection.connection();
			         stmt = conn.createStatement();
			         

			        ps = conn.prepareStatement(query);
			        ps.setInt(1,group.getGroupId() );
			        ps.setInt(2,user.getId());
		            ps.setString(3,blogMessage);
		            ps.executeUpdate();
		            feedPanel.removeAll();
		            feedProccess(feedPanel);
			        blogPurrArea.setText(null);
					
						
					
					
					
				
				}
				catch (SQLException e1) {
			        e1.printStackTrace();
			    }
				
				
				
				
				
				
				
				
				
			}
		});
		
		
		
		
		feedProccess(feedPanel);
		memberTableModel = new DefaultTableModel(new String[]{"		USERNAME"},0);
		memberTable = new JTable(memberTableModel);
		loadGroupMembers();
		
		JScrollPane memberScrollPane = new JScrollPane();
		memberScrollPane.setBounds(6, 225, 171, 361);
		memberScrollPane.setViewportView(memberTable);
		contentPane.add(memberScrollPane);
		
		JButton turnMainPageButton = new JButton("Return Main Page");
		turnMainPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationArea app = new ApplicationArea(user);
				app.setVisible(true);
				dispose();
			}
		});
		turnMainPageButton.setBounds(594, 20, 175, 29);
		contentPane.add(turnMainPageButton);
		
		
		
		
	}
	
	public static JPanel createFeedItem(String username, String message , String time) {
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
        JLabel timelabel = new JLabel(time);
        feedItemPanel.add(timelabel, BorderLayout.SOUTH);

        messageArea.setPreferredSize(new Dimension(messageArea.getPreferredSize().width, 100));
        feedItemPanel.add(messageArea, BorderLayout.CENTER);
        feedItemPanel.add(messageArea, BorderLayout.CENTER);
        
        
        
        
        
        
        
        
        
        
        
        

     
        

        return feedItemPanel;
    }
	
	public void feedProccess(JPanel mainpanel) {
		JPanel feedItemPanel = new JPanel();
		
		
try {
        Connection conn = dataBaseConnection.connection();

        Statement stmt = conn.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT admin.userName, groupPost.groupPost, groupPost.time " +
                "FROM groupPost " +
                "JOIN admin ON groupPost.user_id = admin.id " +
                "ORDER BY groupPost.time DESC");

        
        while(rs.next()) {
        	String username = rs.getString("username");
        	String groupStr = rs.getString("groupPost");
        	String time = rs.getString("time");
        	
        	feedItemPanel = createFeedItem(username,groupStr,time);
        		mainpanel.add(feedItemPanel);
        
        	
 
        
}
}

catch(SQLException e1) {
	e1.printStackTrace();
}
	}

private void loadGroupMembers() {
    String query = "SELECT admin.id, admin.userName " +
                   "FROM groupUserMember " +
                   "JOIN admin ON groupUserMember.userID = admin.id "+
    				"WHERE groupUserMember.groupId = ?";
                   

    try {Connection connection = dataBaseConnection.connection();
         PreparedStatement preparedStatement = connection.prepareStatement(query);
         preparedStatement.setInt(1,group.getGroupId());
         System.out.println("Grup id "+ group.getGroupId());

        
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String userName = resultSet.getString("userName");
            memberTableModel.addRow(new Object[]{userName});
            System.out.println("Member : "+userName);
        }
         }
    catch (SQLException e) {
        e.printStackTrace();
    }
}
}
