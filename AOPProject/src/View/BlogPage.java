package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import Helper .*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import Model.BlogPanelFeed;
import Model.FeedProcessor;
import Model.MainPanelFeed;
import Model.Users;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

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
		setBounds(100, 100, 902, 783);
		blogAreaPane = 	new JPanel();
		blogAreaPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(blogAreaPane);
		blogAreaPane.setLayout(null);
		
		JLabel purrAreaLabel = new JLabel(this.nextUser.getUserName()+"'s  PURR AREA");
		purrAreaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		purrAreaLabel.setFont(new Font("Unispace", Font.ITALIC, 28));
		purrAreaLabel.setBounds(244, 77, 355, 58);
		blogAreaPane.add(purrAreaLabel);
		
		JButton backHomeButton = new JButton("Back Home Page");
		backHomeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ApplicationArea appArea = new ApplicationArea(previousUser);
				appArea.setVisible(true);
				dispose();
			}
		});
		backHomeButton.setBounds(737, 20, 128, 29);
		blogAreaPane.add(backHomeButton);
		
		JScrollPane blogsScrollPane = new JScrollPane();
		blogsScrollPane.setBounds(219, 146, 446, 482);
		blogAreaPane.add(blogsScrollPane);
		
		JPanel blogsPane = new JPanel();
		blogsPane.setBackground(Color.PINK);
		blogsPane.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		blogsScrollPane.setViewportView(blogsPane);
		blogsPane.setLayout(new BoxLayout(blogsPane, BoxLayout.Y_AXIS));
		if(nextUser.getUserName().equals(previousUser.getUserName())){
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
				FeedProcessor userFeedProcessor = new FeedProcessor(new BlogPanelFeed(nextUser.getUserName()));
				userFeedProcessor.processFeed(blogsPane);

				

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
		
		}
		FeedProcessor userFeedProcessor = new FeedProcessor(new BlogPanelFeed(nextUser.getUserName()));
		userFeedProcessor.processFeed(blogsPane);
		
JLabel logoLbl = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));

 		logoLbl.setBounds(49, 52, 97, 120);
 		blogAreaPane.add(logoLbl);
		
		JLabel lblNewLabel = new JLabel("PURR CONNECT");
 		lblNewLabel.setFont(new Font("Unispace", Font.BOLD | Font.ITALIC, 29));
 		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
 		lblNewLabel.setBounds(261, 678, 355, 58);
 		blogAreaPane.add(lblNewLabel);
		
		JLabel BackGroundLabel = new JLabel(new ImageIcon(getClass().getResource("catBackRound.jpg")));
 		BackGroundLabel.setOpaque(true);
 		BackGroundLabel.setFont(new Font("Unispace", Font.PLAIN, 18));
 		
 		BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
 		BackGroundLabel.setBounds(0, 0, 899, 837);
 		blogAreaPane.add(BackGroundLabel);
 		

 		
	}
	
	
	
	



	
		
}
