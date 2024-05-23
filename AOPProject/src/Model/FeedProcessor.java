package Model;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Helper.RoundedBorder;
import Helper.dataBaseConnection;

public class FeedProcessor {

	    private FeedStrategy strategy;

	    public FeedProcessor(FeedStrategy strategy) {
	        this.strategy = strategy;
	    }

	    public void processFeed(JPanel mainPanel) {
	        JPanel feedItemPanel = new JPanel();
	        feedItemPanel.setBorder(BorderFactory.createEmptyBorder());

	        try {
	            Connection conn = dataBaseConnection.connection();
	            ResultSet rs = strategy.fetchFeedItems(conn);
	           
	            
	            
	            if (strategy.getClass()==GroupPanelFeed.class) {
	            	GroupPanelFeed groupStrategy = (GroupPanelFeed)strategy;
	            	 if (rs.next()) {
	            	do {
	                    int groupId = rs.getInt("groupId");
	                    String username = rs.getString("userName")	;
	                    String groupStr = rs.getString("groupPost");
	                    String time = rs.getString("time");

	                    if (groupId == groupStrategy.getGroupId()) {
	                        feedItemPanel = createFeedItem(username, groupStr, time);
	                        mainPanel.add(feedItemPanel);
	                    }
	                } while (rs.next());
	            }
	            	
	            	
	            	
	            }
	            
	            else {
	            while (rs.next()) {
	                String username = rs.getString("username");
	                String blogStr = rs.getString("blogPosts");
	                String time = rs.getString("time");
	                 
	                if (strategy.getClass()==BlogPanelFeed.class) {
	                	BlogPanelFeed strategyy = (BlogPanelFeed) strategy;
	                	if(strategyy.getUsername().equals(username)) {
	              
	                    feedItemPanel = createFeedItem(username, blogStr, time);
	                    mainPanel.add(feedItemPanel);
	                	}
	                }
	                else {

	                    feedItemPanel = createFeedItem(username, blogStr, time);
	                    mainPanel.add(feedItemPanel);
	                }
	                }
	            }
	            
	                
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    private JPanel createFeedItem(String username, String message, String time) {
	        JPanel feedItemPanel = new JPanel();
	        feedItemPanel.setLayout(new BorderLayout());
	        feedItemPanel.setBorder(new RoundedBorder(30));
	        feedItemPanel.setBackground(Color.WHITE);

	        JLabel userLabel = new JLabel(username);
	        userLabel.setFont(new Font("Arial", Font.BOLD, 14));
	        feedItemPanel.add(userLabel, BorderLayout.NORTH);

	        JTextArea messageArea = new JTextArea(message);
	        messageArea.setLineWrap(true);
	        messageArea.setWrapStyleWord(true);
	        messageArea.setEditable(false);
	        messageArea.setOpaque(false);

	        JPanel messagePanel = new JPanel(new BorderLayout());
	        messagePanel.setBorder(new RoundedBorder(30));
	        messagePanel.setBackground(Color.WHITE);
	        messagePanel.add(messageArea, BorderLayout.CENTER);
	        feedItemPanel.add(messagePanel, BorderLayout.CENTER);

	        JLabel timeLabel = new JLabel(time);
	        feedItemPanel.add(timeLabel, BorderLayout.SOUTH);

	        return feedItemPanel;
	    }
	}

