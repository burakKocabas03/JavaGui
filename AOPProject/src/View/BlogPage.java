package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Helper.dataBaseConnection;
import Model.Users;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

public class BlogPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel blogAreaPane;
	static Users nextUser = new Users();
	static Users previousUser = new Users();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BlogPage frame = new BlogPage(previousUser,nextUser);
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
	public BlogPage(Users previousUser,Users nextUser) {
		this.nextUser = nextUser;
		this.previousUser = previousUser;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 629);
		blogAreaPane = new JPanel();
		blogAreaPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(blogAreaPane);
		blogAreaPane.setLayout(null);
		
		JLabel purrAreaLabel = new JLabel(this.nextUser.getUserName()+"'s  PURR AREA");
		purrAreaLabel.setBounds(6, 6, 143, 58);
		blogAreaPane.add(purrAreaLabel);
		
		JButton backHomeButton = new JButton("Back Home Page");
		backHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationArea appArea = new ApplicationArea(previousUser);
				appArea.setVisible(true);
				dispose();
			}
		});
		backHomeButton.setBounds(551, 22, 128, 29);
		blogAreaPane.add(backHomeButton);
		
		JScrollPane blogsScrollPane = new JScrollPane();
		blogsScrollPane.setBounds(164, 193, 355, 402);
		blogAreaPane.add(blogsScrollPane);
		
		JPanel blogsPane = new JPanel();
		blogsScrollPane.setViewportView(blogsPane);
		blogsPane.setLayout(new BoxLayout(blogsPane, BoxLayout.Y_AXIS));
		
		JPanel purrPanel = new JPanel();
		purrPanel.setBounds(164, 102, 355, 90);
		blogAreaPane.add(purrPanel);
		
		JTextArea blogPurrArea = new JTextArea();
		
		JButton purrButton = new JButton("PURR");
		purrButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String blogMessage = blogPurrArea.getText();
				nextUser.myPurrToDtbs(blogMessage);
				blogsPane.removeAll();
				feedProccess(blogsPane);

				

				blogPurrArea.setText(null);
				
			}
		});
		GroupLayout gl_purrPanel = new GroupLayout(purrPanel);
		gl_purrPanel.setHorizontalGroup(
			gl_purrPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(blogPurrArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
				.addGroup(gl_purrPanel.createSequentialGroup()
					.addGap(238)
					.addComponent(purrButton, GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_purrPanel.setVerticalGroup(
			gl_purrPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_purrPanel.createSequentialGroup()
					.addComponent(blogPurrArea, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(purrButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		purrPanel.setLayout(gl_purrPanel);
		feedProccess(blogsPane);
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
		ArrayList<String>blogStrArray = new ArrayList<>();
		
		
try {
        Connection conn = dataBaseConnection.connection();

        Statement stmt = conn.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT admin.userName, posts.blogPosts, posts.time " +
                "FROM posts " +
                "JOIN admin ON posts.user_id = admin.id " +
                "ORDER BY posts.time DESC");

        
        while(rs.next()) {
        	String username = rs.getString("username");
        	String blogStr = rs.getString("blogPosts");
        	String time = rs.getString("time");
        	if(username.equals(nextUser.getUserName())){
        	feedItemPanel = createFeedItem(username,blogStr,time);
        		mainpanel.add(feedItemPanel);
        	}
        	
 
        
}
}


catch(SQLException e1) {
	
	e1.printStackTrace();
}

		
	}
		
}
