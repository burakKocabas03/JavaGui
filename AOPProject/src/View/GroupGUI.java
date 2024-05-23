package View;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Helper.RoundedBorder;
import Helper.dataBaseConnection;
import Model.BlogPanelFeed;
import Model.FeedProcessor;
import Model.GroupPanelFeed;
import Model.Groups;
import Model.Users;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
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

	
	 
	    public GroupGUI(Users user, Groups group) {
	        this.user = user;
	        this.group = group;

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setBounds(100, 100, 902, 783);
	        contentPane = new JPanel();
	        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	        setContentPane(contentPane);
	        contentPane.setLayout(null);

	        JScrollPane feedScrollPane = new JScrollPane();
			feedScrollPane.setBackground(Color.PINK);
			feedScrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 3));
			feedScrollPane.setBounds(239, 225, 401, 361);
			contentPane.add(feedScrollPane);

	        JPanel feedPanel = new JPanel();
	        feedPanel.setBackground(Color.PINK);
			feedScrollPane.setColumnHeaderView(feedPanel);
	        feedScrollPane.setViewportView(feedPanel);
	        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));

	        JLabel lblNewLabel = new JLabel(group.getGroupName().toUpperCase() + " GROUP");
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			lblNewLabel.setFont(new Font("Unispace", Font.ITALIC, 22));
			lblNewLabel.setBounds(320, 41, 233, 76);
			contentPane.add(lblNewLabel);
			
			
			
			
			try {
				Connection conn = dataBaseConnection.connection();
				PreparedStatement ps = conn.prepareStatement("SELECT userID FROM groupUserMember WHERE groupName = ? AND userID = ?");
				ps.setString(1, group.getGroupName());
				ps.setInt(2, user.getId());
				ResultSet rs = ps.executeQuery();
				
				if (rs.next()) {

					JPanel PurrPanel = new JPanel();
					PurrPanel.setBorder( new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.PINK, Color.PINK));
					PurrPanel.setBounds(239, 127, 396, 101);
					contentPane.add(PurrPanel);

			        JButton buttonPURR = new JButton("PURR");

			        JTextArea blogPurrArea = new JTextArea();
			        blogPurrArea.setFont(new Font("Courier New", Font.BOLD, 19));
			        blogPurrArea.setPreferredSize(new Dimension(blogPurrArea.getPreferredSize().width, 70));
			        blogPurrArea.setLineWrap(true);
			        buttonPURR.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent e) {
			                String blogMessage = blogPurrArea.getText();
			                user.myPurrToDtbsGroup(group, blogMessage);
			                System.out.println("database güncellendi");
			                feedPanel.removeAll();
			                System.out.println("feed temizlendi");
			                FeedProcessor userFeedProcessor = new FeedProcessor(new GroupPanelFeed(group.getGroupId()));
			    			userFeedProcessor.processFeed(feedPanel);
			                System.out.println("feed yenilendi");
			                blogPurrArea.setText(null);

			                // JPanel bileşenlerini yeniden çiz
			                feedPanel.revalidate();
			                feedPanel.repaint();
			            }
			        });

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
					
					
					
					
					
				}
				
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
				
			
		
			
			
	      

	        FeedProcessor userFeedProcessor = new FeedProcessor(new GroupPanelFeed(group.getGroupId()));
			userFeedProcessor.processFeed(feedPanel);

	        memberTableModel = new DefaultTableModel(new String[]{" USERNAME"}, 0);
	        memberTable = new JTable(memberTableModel);
	        memberTable.setBackground(Color.PINK);
	        loadGroupMembers();

	        JScrollPane memberScrollPane = new JScrollPane();
	        memberScrollPane.setBackground(Color.PINK);
	        memberScrollPane.setOpaque(false);
	        memberScrollPane.setViewportBorder(new LineBorder(new Color(0, 0, 0), 2));
			memberScrollPane.setBounds(6, 225, 171, 361);
	    
	        memberScrollPane.setViewportView(memberTable);
	        contentPane.add(memberScrollPane);

	        JButton turnMainPageButton = new JButton("Return Main Page");
	        turnMainPageButton.setFont(new Font("Tahoma", Font.PLAIN, 17));
	        turnMainPageButton.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                ApplicationArea app = new ApplicationArea(user);
	                app.setVisible(true);
	                dispose();
	            }
	        });
	        turnMainPageButton.setBounds(703, 24, 175, 29);
	        contentPane.add(turnMainPageButton);
	        
	        JLabel logoLbl = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));
	 		
	 		logoLbl.setBounds(49, 52, 97, 120);
	 		contentPane.add(logoLbl);
			
	 		JLabel lblNewLabel1 = new JLabel("PURR CONNECT");
	 		lblNewLabel1.setFont(new Font("Unispace", Font.BOLD | Font.ITALIC, 29));
	 		lblNewLabel1.setHorizontalAlignment(SwingConstants.CENTER);
	 		lblNewLabel1.setBounds(261, 678, 355, 58);
	 		contentPane.add(lblNewLabel1);
	 		
	 		JLabel BackGroundLabel = new JLabel(new ImageIcon(getClass().getResource("catBackRound.jpg")));
	 		BackGroundLabel.setOpaque(true);
	 		BackGroundLabel.setFont(new Font("Unispace", Font.PLAIN, 18));
	 		
	 		BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
	 		BackGroundLabel.setBounds(0, 0, 899, 837);
	 		contentPane.add(BackGroundLabel);
			
			
	        
	        
	        
	        
	    }

	    

	    private void loadGroupMembers() {
	        String query = "SELECT user.id, user.userName " +
	                       "FROM groupUserMember " +
	                       "JOIN user ON groupUserMember.userID = user.id " +
	                       "WHERE groupUserMember.groupId = ?";

	        try {
	            Connection connection = dataBaseConnection.connection();
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, group.getGroupId());
	            System.out.println("Grup id " + group.getGroupId());

	            ResultSet resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()) {
	                String userName = resultSet.getString("userName");
	                memberTableModel.addRow(new Object[]{userName});
	                System.out.println("Member : " + userName);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	
}