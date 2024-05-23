package View;





import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Helper.dataBaseConnection;
import Model.FeedProcessor;
import Model.Groups;
import Model.MainPanelFeed;
import Model.Users;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import Helper.Helper;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;
import javax.swing.JTextPane;


interface GroupSubject {
	void registerObserverFriend(GroupObserver observer);
	void removeObserverFriend(GroupObserver observer);
	 void notifyObserversFriend(String newMemberName, String groupName);
    void registerObserver(GroupObserver observer);
    void removeObserver(GroupObserver observer);
    void notifyObservers(String newMemberName, String groupName);
}
class ConcreteGroupSubject implements GroupSubject {
    private List<GroupObserver> observers = new ArrayList<>();
    private List<GroupObserver> observersFriend = new ArrayList<>();
    

    @Override
    public void registerObserver(GroupObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GroupObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String newMemberName, String groupName) {
        for (GroupObserver observer : observers) {
            observer.update(newMemberName, groupName);
        }
    }

	@Override
	public void registerObserverFriend(GroupObserver observer) {
		observersFriend.add(observer);
		
	}

	@Override
	public void removeObserverFriend(GroupObserver observer) {
		observersFriend.remove(observer);
		
	}

	@Override
	public void notifyObserversFriend(String senderName, String receiverName) {
		for(GroupObserver observer:observersFriend) {
			observer.updateFriend(senderName, receiverName);
			System.out.println("Observer updateFriend");
		}
		
	}
}
interface GroupObserver {
	void updateFriend(String senderName,String receiverName);
    void update(String newMemberName, String groupName);
}
class ConcreteGroupObserver implements GroupObserver {
    private String userName;

    public ConcreteGroupObserver(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(String newMemberName, String groupName) {
    	
    	
    	String query1 = "SELECT id FROM user WHERE userName = ?";
    	 String query2 ="INSERT INTO notifications (user_id,notification) VALUES (?,?)" ;
        String notification =userName + " received notification: " + newMemberName + " has joined the " + groupName + " group.";
        
        try {
        	Connection conn = dataBaseConnection.connection();
        
             PreparedStatement preparedStatement = conn.prepareStatement(query1) ;
             
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
            {	int id = resultSet.getInt("id");
            
               
            Connection conn2  = dataBaseConnection.connection();
            PreparedStatement preparedStatement2 = conn2.prepareStatement(query2) ;
            preparedStatement2.setInt(1, id);
            preparedStatement2.setString(2,notification);
            preparedStatement2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
		}
        
}

	@Override
	public void updateFriend(String senderName, String receiverName) {
		
		 String request =senderName + " want friend with you "; 
		 String query ="INSERT INTO friendRequest (senderName,receiverName,request) VALUES (?,?,?)" ;

	        try {
	        	Connection conn = dataBaseConnection.connection();
	        
	             PreparedStatement preparedStatement = conn.prepareStatement(query) ;
	             
	            preparedStatement.setString(1,senderName);
	            preparedStatement.setString(2, receiverName);
	            preparedStatement.setString(3,request);
	            preparedStatement.executeUpdate();
	        }
	       
	        
	        
	        
	        
	        catch(SQLException e1) {
	        	e1.printStackTrace();
	        }
	        
	        
		 
		 
	        
		
		
	}
}



public class ApplicationArea extends JFrame {
	
	
	
	private DefaultTableModel friendModel = null;
	private String [] friendsArray = null;
	
	private ConcreteGroupSubject groupSubject = new ConcreteGroupSubject();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Users user = new Users();
	private JTable friendListTable;
	
	//deff for block search
    private JCheckBox blockSearchCheck;

	
	//Definitions For SearchGroup
	
	JScrollPane groupSearchScroll;
	private JTextField searchFieldGroup;
	private DefaultListModel<String> suggestionListModelGroup;
	private JList<String> suggestionListGroup;

	
	//definitons group pop menu 
	private JPopupMenu popupMenuGroup;
	private JMenuItem viewGroupPage;
	private JMenuItem joinGroup;
	
	//has group table
	
	private DefaultTableModel hasGroupModel;
	private JTable hasGroupTable;
	
	
	
	
	
	// Definitions For SearchField
		JScrollPane searchScrollPane ;
	  private JTextField searchField;
	  private DefaultListModel<String> suggestionListModel;
	  private JList<String> suggestionList;
	  
	//definitions for popup menu
	  private JPopupMenu popupMenu;
	    private JMenuItem viewProfileItem;
	    private JMenuItem addFriendItem;
	   
	   
	  //definitions for requestList
	    private JList friendRequestList;
	    private DefaultListModel<String>requestListModel;	
	  
	  
	  
    
	
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
		
		Helper.chooseObject(user);
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
		setBounds(100, 100, 902, 783);
		setTitle("MAIN MENU");
		contentPane = 	new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane friendListPane = new JScrollPane();
		friendListPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, Color.PINK, Color.PINK, Color.PINK, Color.PINK));
		friendListPane.setToolTipText("");
		friendListPane.setBounds(687, 128, 130, 334);
		contentPane.add(friendListPane);
		
		friendListTable = new JTable(friendModel);
		friendListPane.setViewportView(friendListTable);
		
		JPanel friendlistPanel = new JPanel();
		friendlistPanel.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		friendlistPanel.setBackground(Color.PINK);
		friendlistPanel.setBounds(687, 93, 130, 35);
		contentPane.add(friendlistPanel);
		
				JLabel friendListLabel = new JLabel("Friends List");
				friendlistPanel.add(friendListLabel);
				friendListLabel.setHorizontalAlignment(SwingConstants.CENTER);
				friendListLabel.setFont(new Font("Unispace", Font.PLAIN, 15));
				friendListLabel.setBackground(Color.BLACK);
		
		
		JScrollPane feedScrollPane = new JScrollPane();
		feedScrollPane.setAutoscrolls(true);
		feedScrollPane.setBounds(257, 267, 389, 405);
		JScrollBar vertical = feedScrollPane.getVerticalScrollBar();
		vertical.setValue( vertical.getMinimum() );
		contentPane.add(feedScrollPane);
		
		JPanel feedPanel = new JPanel();
		feedPanel.setBackground(Color.PINK);
		feedScrollPane.setViewportView(feedPanel);
		feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
		
		
		JPanel PurrPanel = new JPanel();
		PurrPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		PurrPanel.setBackground(Color.BLACK);
		PurrPanel.setBounds(257, 182, 389, 77);
		contentPane.add(PurrPanel);
		
		JButton buttonPURR = new JButton("PURR");
		buttonPURR.setBackground(Color.BLACK);
	
		
		JTextArea blogPurrArea = new JTextArea();
		blogPurrArea.setFont(new Font("Courier10 BT", Font.PLAIN, 18));
		blogPurrArea.setBackground(Color.WHITE);
		 blogPurrArea.setPreferredSize(new Dimension(blogPurrArea.getPreferredSize().width, 70));
		blogPurrArea.setLineWrap(true);
		GroupLayout gl_PurrPanel = new GroupLayout(PurrPanel);
		gl_PurrPanel.setHorizontalGroup(
			gl_PurrPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_PurrPanel.createSequentialGroup()
					.addContainerGap(307, Short.MAX_VALUE)
					.addComponent(buttonPURR)
					.addContainerGap())
				.addComponent(blogPurrArea, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 389, Short.MAX_VALUE)
		);
		gl_PurrPanel.setVerticalGroup(
			gl_PurrPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_PurrPanel.createSequentialGroup()
					.addComponent(blogPurrArea, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(buttonPURR, GroupLayout.PREFERRED_SIZE, 13, Short.MAX_VALUE))
		);
		PurrPanel.setLayout(gl_PurrPanel);
		
		
		buttonPURR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String blogMessage = blogPurrArea.getText();
				user.myPurrToDtbs(blogMessage);
				
				feedPanel.removeAll();
				FeedProcessor userFeedProcessor = new FeedProcessor(new MainPanelFeed("mainpanel"));
				userFeedProcessor.processFeed(feedPanel);
				feedPanel.revalidate();
                feedPanel.repaint();

				

				blogPurrArea.setText(null);
				
				
				
				
			}
		});
		
		JButton btnLogOut = new JButton("Log Out");
		btnLogOut.setFont(new Font("Unispace", Font.BOLD, 15));
		btnLogOut.setBackground(Color.BLACK);
		btnLogOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LoginGUI lgnGui = new LoginGUI();
				lgnGui.setVisible(true);
                dispose();
			}
		});
		btnLogOut.setBounds(687, 52, 130, 29);
		contentPane.add(btnLogOut);
		
		
		JLabel purrConnectLbl = new JLabel("PURR CONNECT");
		purrConnectLbl.setFont(new Font("Unispace", Font.BOLD | Font.ITALIC, 29));
		purrConnectLbl.setToolTipText("");
		purrConnectLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purrConnectLbl.setBounds(257, 684, 389, 52);
		contentPane.add(purrConnectLbl);
		
		
		
		
		

		FeedProcessor userFeedProcessor = new FeedProcessor(new MainPanelFeed("mainpanel"));
		userFeedProcessor.processFeed(feedPanel);

		
		
		
		searchField = new JTextField();
		
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
                toggleSuggestionListVisibility();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
                toggleSuggestionListVisibility();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
                toggleSuggestionListVisibility();
            }
        });
        
        
        suggestionListModel = new DefaultListModel<>();
        suggestionList = new JList<>(suggestionListModel);
        suggestionList.setVisible(false);
        
        
         searchScrollPane = new JScrollPane(suggestionList);
        
        
        JPanel searchPanel = new JPanel();
        searchPanel.setOpaque(false);
        searchPanel.setBounds(257, 11, 391, 159);
        searchPanel.setLayout(new BorderLayout());
        searchPanel.add(searchField, BorderLayout.NORTH);
        searchPanel.add(searchScrollPane, BorderLayout.SOUTH);
        
        contentPane.add(searchPanel);
        
        
        
        
        popupMenu = new JPopupMenu();
        viewProfileItem = new JMenuItem("View Profile");
        addFriendItem = new JMenuItem("Add Friend");

        // Açılır menü öğelerine tıklanma dinleyicilerini ekle
        viewProfileItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String selectedUsername = suggestionList.getSelectedValue();
            	try {

                    Connection conn = dataBaseConnection.connection();

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM user");
                    while (rs.next()) {

                        if (selectedUsername.equals(rs.getString("userName"))) {
                            Users nextuser = new Users();
                            nextuser.setId(rs.getInt("id"));
                            nextuser.setUserName(rs.getString("userName"));
                            nextuser.setPassWord(rs.getString("password"));	
                            BlogPage blgPage = new BlogPage(user,nextuser);
                            blgPage.setVisible(true);
                            dispose();
                          
                 
                            
            }
                    }
            	}
            	catch(SQLException e1) {
            		e1.printStackTrace();
            	}
            	
            	
            }
        });

        addFriendItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedUsername = suggestionList.getSelectedValue();
                ConcreteGroupObserver observer1 = new ConcreteGroupObserver(user.getUserName());
                
                groupSubject.registerObserverFriend(observer1);
               
            

            // Notify observers about the new member
            groupSubject.notifyObserversFriend(user.getUserName(), selectedUsername);
           
           
                
                
                
                
                //user.addFriend(selectedUsername);
                //updateFriendsList();
            }
        });

        // Açılır menüye öğeleri ekle
        popupMenu.add(viewProfileItem);
        popupMenu.add(addFriendItem);
        
        
        // Liste bileşenine fare dinleyicisi ekle
        suggestionList.addMouseListener((MouseListener) new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    // Fare sağ tıklaması algılandığında
                    int index = suggestionList.locationToIndex(e.getPoint());
                    suggestionList.setSelectedIndex(index); // Tıklanan öğeyi seç

                    // Açılır menüyü göster
                    popupMenu.show(suggestionList, e.getX(), e.getY());
                }
            }
        });
        
        JPanel createGrouPanel = new JPanel();
        createGrouPanel.setBackground(Color.PINK);
        createGrouPanel.setBounds(6, 106, 180, 50);
        contentPane.add(createGrouPanel);
        
        JTextPane createGroutextPane = new JTextPane();
        createGroutextPane.setBackground(Color.WHITE);
        
        JButton btnNewButton = new JButton("Create Group");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String query = "INSERT INTO groupsField (groupName) VALUES (?)";

                try {
                    // Veritabanına bağlantı oluşturma
                    Connection conn = dataBaseConnection.connection();
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM groupsField");

                    boolean groupExists = false;

                    // Mevcut grup isimlerini kontrol et
                    while (rs.next()) {
                        if (rs.getString("groupName").equals(createGroutextPane.getText())) {
                            groupExists = true;
                            break;
                        }
                    }

                    if (groupExists) {
                        // Grup ismi zaten mevcutsa, bilgilendirme mesajı göster
                        JOptionPane.showMessageDialog(null, "This group has already been created", "Message", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Grup ismi mevcut değilse, yeni grup eklenir
                        PreparedStatement ps = conn.prepareStatement(query);
                        ps.setString(1, createGroutextPane.getText());
                        ps.executeUpdate();
                        JOptionPane.showMessageDialog(null, "Group created successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        GroupLayout gl_panel = new GroupLayout(createGrouPanel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(createGroutextPane, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
        				.addComponent(btnNewButton))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addComponent(createGroutextPane)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnNewButton))
        );
        createGrouPanel.setLayout(gl_panel);
        
        
        
		
		searchFieldGroup = new JTextField();
		searchFieldGroup.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, null, null));
		
        searchFieldGroup.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            	updateSuggestionsGroup();
            	
                
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            	updateSuggestionsGroup();
            	
                
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            	updateSuggestionsGroup();
           
            }
        });
        suggestionListModelGroup = new DefaultListModel<>();
        suggestionListGroup = new JList<>(suggestionListModelGroup);
        suggestionListGroup.setSelectionForeground(Color.WHITE);
        suggestionListGroup.setBackground(Color.PINK);
        suggestionListGroup.setOpaque(true);
        
        
         groupSearchScroll = 	new JScrollPane(suggestionListGroup);
         groupSearchScroll.setBackground(Color.PINK);
         JPanel groupSearchPanel = new JPanel();
         groupSearchPanel.setBackground(Color.PINK);
         groupSearchPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
         groupSearchPanel.setBounds(6, 199, 204, 181);
         
         groupSearchPanel.setLayout(new BorderLayout());
         groupSearchPanel.add(searchFieldGroup, BorderLayout.NORTH);
         groupSearchPanel.add(groupSearchScroll, BorderLayout.CENTER);
         
         contentPane.add(groupSearchPanel);
         
         JButton btnNewButton_1 = new JButton("My Profile");
         btnNewButton_1.setFont(new Font("Dialog", Font.BOLD, 15));
         btnNewButton_1.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		
                 BlogPage blgPage = new BlogPage(user,user);
                 blgPage.setVisible(true);
                 dispose();
         		
         	}
         });
         btnNewButton_1.setBounds(3, 65, 117, 29);
         contentPane.add(btnNewButton_1);
         
         JLabel searchGroupLbl = new JLabel("Search Group");
         searchGroupLbl.setFont(new Font("Dialog", Font.BOLD, 15));
         searchGroupLbl.setBounds(16, 168, 128, 19);
         contentPane.add(searchGroupLbl);
         
         JLabel WelcomeLabel = new JLabel("Welcome " +user.getUserName());
         WelcomeLabel.setFont(new Font("Dialog", Font.BOLD, 15));
         WelcomeLabel.setBounds(6, 18, 180, 39);
         contentPane.add(WelcomeLabel);
         
         hasGroupModel = new DefaultTableModel(new String[]{"GROUP MEMBERSHIP"},0);
         hasGroupTable = new JTable(hasGroupModel);
         hasGroupTable.setSelectionForeground(Color.PINK);
         hasGroupTable.setBackground(Color.PINK);
         loadGroups();
         
         
         
         JScrollPane hasGroupScroll = new JScrollPane();
         hasGroupScroll.setBackground(Color.PINK);
         hasGroupScroll.setBounds(6, 408, 204, 273);
         hasGroupScroll.setViewportView(hasGroupTable);
         contentPane.add(hasGroupScroll);
         
         JPopupMenu popupMenu = new JPopupMenu();
         JMenuItem viewGroupItem = new JMenuItem("View Group");
         popupMenu.add(viewGroupItem);

         // MouseListener ekleme
         hasGroupTable.addMouseListener(new MouseAdapter() {
             @Override
             public void mousePressed(MouseEvent e) {
                 if (SwingUtilities.isRightMouseButton(e)) {
                     int row = hasGroupTable.rowAtPoint(e.getPoint());
                     hasGroupTable.setRowSelectionInterval(row, row);
                     showPopup(e);
                 }
             }

             private void showPopup(MouseEvent e) {
                 int selectedRow = hasGroupTable.getSelectedRow();
                 if (selectedRow != -1) {
                     popupMenu.show(e.getComponent(), e.getX(), e.getY());
                 }
             }
         });
         
         viewGroupItem.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 int groupId= 0;
        		 String groupName=null;
        		
        		 int selectedRow = hasGroupTable.getSelectedRow();
                 if (selectedRow != -1) {
                     String groupMembership = hasGroupTable.getValueAt(selectedRow, 0).toString();
              	try{
 					Connection conn= dataBaseConnection.connection();

 	                   Statement stmt = conn.createStatement();
 	                   ResultSet rs = stmt.executeQuery("SELECT * FROM groupsField");
 	                   while(rs.next()) {
 	                	   if(rs.getString("groupName").equals(groupMembership)){
 	                		   
 	                		   System.out.println("viewGroupPAge");
 	                		  System.out.println("rs: "+ rs.getString("groupName"));
 	                		  System.out.println("selectedgr" + groupMembership);
 	                		  
 	                	    
 	                	   Groups group = new Groups();
 	                	   
 	                	  groupId = rs.getInt("groupId");
	                	    groupName = rs.getString("groupName");
	                	    group.setGroupId(groupId);
	                	    group.setGroupName(groupName);
 	                	   System.out.println("Grup nesnesinin id si " +  group.getGroupId());
 	 	                  GroupGUI groupGui = new GroupGUI(user,group);
 	 	                  groupGui.setVisible(true);
 	 	                  dispose();
 	                	    break;
 	                	   
 	                	   }
 	                	  
 	                   }
 	                 
 	}
              	catch(SQLException e1) {
              		e1.printStackTrace();}
        		 
        		 
        	 }
        	 }
        	 
         });
         
         
         
         
         

         blockSearchCheck = new JCheckBox("Block Search");
         blockSearchCheck.setBounds(687, 18, 128, 23);
         contentPane.add(blockSearchCheck);
         loadCheckboxState();
         blockSearchCheck.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 saveCheckboxState(blockSearchCheck.isSelected());
             }
         });
     
         popupMenuGroup = new JPopupMenu();
         viewGroupPage = new JMenuItem("View Group Page");
         joinGroup = new JMenuItem("Join Group");
         popupMenuGroup.add(viewGroupPage);
         popupMenuGroup.add(joinGroup);
         
         suggestionListGroup.addMouseListener((MouseListener) new MouseAdapter() {
             public void mouseReleased(MouseEvent m) {
                 if (SwingUtilities.isRightMouseButton(m)) {
                     // Fare sağ tıklaması algılandığında
                     int index2 = suggestionListGroup.locationToIndex(m.getPoint());
                     suggestionListGroup.setSelectedIndex(index2); // Tıklanan öğeyi seç

                     // Açılır menüyü göster
                     popupMenuGroup.show(suggestionListGroup, m.getX(), m.getY());
                 }
             }
         });
         
         
         
         
         joinGroup.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	 int groupId= 0;
            	 
             	String selectedGroupName = suggestionListGroup.getSelectedValue();
             	try{
					Connection conn2= dataBaseConnection.connection();

	                   Statement stmt2 = conn2.createStatement();
	                   ResultSet rs = stmt2.executeQuery("SELECT * FROM groupsField");
	                   while(rs.next()) {
	                	   if(rs.getString("groupName").equals(selectedGroupName)){
	                	    groupId = rs.getInt("groupId");    	    
	                	    break;
	                	   
	                	   }
	                   }
	                   Connection conn= dataBaseConnection.connection();

	                   Statement stmt = conn.createStatement();
	                   PreparedStatement ps = conn.prepareStatement("INSERT  INTO groupUserMember(groupId,groupName,userID) VALUES (?,?,?)");
	                   ps.setInt(1,groupId);
	                   ps.setString(2, selectedGroupName);
	                   ps.setInt(3, user.getId());
	                   ps.executeUpdate();
	                   loadGroups();
	                   List<String> existingMembers = getExistingGroupMembers(groupId, user.getUserName());

	                   // Register existing group members as observers
	                   for (String memberName : existingMembers) {
	                       ConcreteGroupObserver observer = new ConcreteGroupObserver(memberName);
	                       groupSubject.registerObserver(observer);
	                   }

	                   // Notify observers about the new member
	                   groupSubject.notifyObservers(user.getUserName(), selectedGroupName);
	                  
	                   
             		
             	
             	}
             	
             	catch(SQLException e1) {
             		e1.printStackTrace();
             		}
             	}
             
         });
         
         
         viewGroupPage.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        		 int groupId= 0;
        		 String groupName=null;
        		
              	String selectedGroupName = suggestionListGroup.getSelectedValue();
              	try{
 					Connection conn= dataBaseConnection.connection();

 	                   Statement stmt = conn.createStatement();
 	                   ResultSet rs = stmt.executeQuery("SELECT * FROM groupsField");
 	                   while(rs.next()) {
 	                	   if(rs.getString("groupName").equals(selectedGroupName)){
 	                		   
 	                		   System.out.println("viewGroupPAge");
 	                		  System.out.println("rs: "+ rs.getString("groupName"));
 	                		  System.out.println("selectedgr" + selectedGroupName);
 	                		  
 	                	    
 	                	   Groups group = new Groups();
 	                	   
 	                	  groupId = rs.getInt("groupId");
	                	    groupName = rs.getString("groupName");
	                	    group.setGroupId(groupId);
	                	    group.setGroupName(groupName);
 	                	   System.out.println("Grup nesnesinin id si " +  group.getGroupId());
 	 	                  GroupGUI groupGui = new GroupGUI(user,group);
 	 	                  groupGui.setVisible(true);
 	 	                  dispose();
 	                	    break;
 	                	   
 	                	   }
 	                	  
 	                   }
 	                 
 	}
              	catch(SQLException e1) {
              		e1.printStackTrace();}
              	}
             
         });
         
         JLabel logoLbl = new JLabel(new ImageIcon(getClass().getResource("cat128.png")));
 		
 		logoLbl.setBounds(385, 50, 97, 120);
 		contentPane.add(logoLbl);
 		
 		JButton premiumBtn = new JButton("Take Premium");
 		premiumBtn.setFont(new Font("Dialog", Font.BOLD, 15));
 		premiumBtn.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e) {
 				
 				String query = "UPDATE user SET premiumMemberShip = ? WHERE id = ?";
 		        try {Connection connection =dataBaseConnection.connection();
 		             PreparedStatement preparedStatement = connection.prepareStatement(query);

 		            preparedStatement.setInt(2,user.getId());
 		            preparedStatement.setBoolean(1, true);
 		            preparedStatement.executeUpdate();

 		        } catch (SQLException e1) {
 		            e1.printStackTrace();
 		        }
 		       JOptionPane.showMessageDialog(null, "Your Membership Upgradet to Premium", "Info", JOptionPane.INFORMATION_MESSAGE);
 				
 				
 				
 			}
 		});
 		
 		try {
 		Connection conn = dataBaseConnection.connection();
 		PreparedStatement pt = conn.prepareStatement("SELECT premiumMemberShip FROM user WHERE id = ?");
 		pt.setInt(1, user.getId());
 		ResultSet rs = pt.executeQuery();
 		if (!rs.next()) {}
 		else {
 		
 		if(rs.getBoolean("premiumMemberShip")) {
 			
 		JLabel ticIcon = new JLabel(new ImageIcon(getClass().getResource("pinktic.png")));
 		ticIcon.setBounds(143, 11, 80, 46);
 		contentPane.add(ticIcon);
 		}
 		}
 		}
 		catch(SQLException e1) {
 			e1.printStackTrace();
 		}
 		
 		
 		
 		
 		premiumBtn.setBackground(Color.BLACK);
 		premiumBtn.setBounds(689, 710, 140, 39);
 		contentPane.add(premiumBtn);
 		
 		requestListModel = new DefaultListModel<>();
 		
 		
 		
 		
 		friendRequestList = new JList<>(requestListModel);
 		friendRequestList.setSelectionBackground(new Color(8, 74, 217));
 		friendRequestList.setSelectionForeground(Color.WHITE);
 		friendRequestList.setBackground(Color.PINK);
 		
 		
 		friendRequestList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Çift tıklama kontrolü
                    int index = friendRequestList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        String request = requestListModel.getElementAt(index);
                        showFriendRequestDialog(request, index);
                    }
                }
            }
        });
 		addFriendRequest();
 		JScrollPane friendRequestScroll = new JScrollPane();
 		friendRequestScroll.setBounds(687, 509, 130, 163);
 		contentPane.add(friendRequestScroll);
 		
 		friendRequestScroll.setViewportView(friendRequestList);

 		JLabel BackGroundLabel = new JLabel(new ImageIcon(ApplicationArea.class.getResource("/View/catBackRound.jpg")));
 		BackGroundLabel.setAutoscrolls(true);
 		BackGroundLabel.setOpaque(true);
 		BackGroundLabel.setFont(new Font("Unispace", Font.PLAIN, 18));
 		
 		JPanel requestPanel = new JPanel();
 		requestPanel.setBorder(new MatteBorder(0, 1, 0, 1, (Color) new Color(0, 0, 0)));
 		requestPanel.setBackground(Color.WHITE);
 		requestPanel.setBounds(687, 477, 130, 29);
 		contentPane.add(requestPanel);
 		requestPanel.setLayout(null);
 		
 		JLabel requestLabel = new JLabel("  Friend Requests");
 		requestLabel.setFont(new Font("Dialog", Font.PLAIN, 15));
 		requestLabel.setBounds(0, 0, 124, 29);
 		requestPanel.add(requestLabel);
 		
 		BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
 		BackGroundLabel.setBounds(0, 0, 899, 837);
 		contentPane.add(BackGroundLabel);
 		
 		
 		
 		
 		
 		
 		
	}
	
	
	
	//METHODS FOR OPERATIONS
	
	private void toggleSuggestionListVisibility() {
	    if (suggestionListModel.isEmpty()) {
	        suggestionList.setVisible(false);
	        searchScrollPane.setVisible(false); // Eğer arama sonuçları yoksa, scroll pane'i gizle
	    } else {
	    	 searchScrollPane.setVisible(true); 
	        suggestionList.setVisible(true);
	        int preferredHeight = Math.min(suggestionListModel.getSize() * suggestionList.getFixedCellHeight(),59);
	        suggestionList.setPreferredSize(new Dimension(suggestionList.getWidth(), preferredHeight));
	        searchScrollPane.setPreferredSize(new Dimension(searchScrollPane.getWidth(), preferredHeight));
	        // Arama sonuçları varsa, scroll pane'i göster
	        searchScrollPane.revalidate();
	        searchScrollPane.repaint();
	    }
	}

	
	private void updateSuggestions() {
        String searchText = searchField.getText().trim(); // Arama metnini al, trim() metodu ile boşlukları kaldır
        suggestionListModel.clear(); // Öneri listesini temizle

        if (!searchText.isEmpty()) { // Arama metni alanı boş değilse
            try {
                // Veritabanına bağlan
                Connection conn = dataBaseConnection.connection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM user");

                // Veritabanından kullanıcı adlarını al ve öneri listesine ekle
                while (rs.next()) {
                	if(rs.getBoolean("checkbox_status")==false) {
                    String userName = rs.getString("userName");
                    if (userName.toLowerCase().startsWith(searchText.toLowerCase()) &&  !userName.equals(user.getUserName()) ) {
                        suggestionListModel.addElement(userName);
                    }
                	}
                }

                // Kaynakları serbest bırak
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	private void updateSuggestionsGroup() {
        SwingUtilities.invokeLater(() -> {
            String searchText = searchFieldGroup.getText().trim();
            suggestionListModelGroup.clear();

            if (!searchText.isEmpty()) {
                try {
                    Connection conn = dataBaseConnection.connection();
                    PreparedStatement stmt = conn.prepareStatement("SELECT groupName FROM groupsField WHERE groupName LIKE ?");
                    stmt.setString(1, searchText + "%");
                    ResultSet rs = stmt.executeQuery();

                    while (rs.next()) {
                        String groupName = rs.getString("groupName");
                        
                        suggestionListModelGroup.addElement(groupName);
                    }

                    rs.close();
                    stmt.close();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            toggleSuggestionListVisibilityGroup();
        });
    }

    private void toggleSuggestionListVisibilityGroup() {
        if (suggestionListModelGroup.isEmpty()) {
            groupSearchScroll.setVisible(false);
        } else {
            groupSearchScroll.setVisible(true);
            groupSearchScroll.revalidate();
            groupSearchScroll.repaint();
        }
    }
    private void loadGroups() {
    	hasGroupModel.setRowCount(0); // Mevcut tüm satırları temizler
    	try {
            Connection conn = dataBaseConnection.connection();
            PreparedStatement stmt = conn.prepareStatement("SELECT groupName FROM groupUserMember WHERE userID LIKE ?");
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String groupName = rs.getString("groupName");
                
                hasGroupModel.addRow(new Object[] {groupName});
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    }

	
	
	public void updateFriendsList() {
		DefaultTableModel clearModel = (DefaultTableModel) friendListTable.getModel();
		clearModel.setRowCount(0);
		try {
			
			for(int i = 0;i<user.getFriendList().size();i++) {
				friendsArray[0] = user.getFriendList().get(i);
				friendModel.addRow(friendsArray);
				
}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}

		
	
	private List<String> getExistingGroupMembers(int groupId, String newMemberName) {
	    List<String> existingMembers = new ArrayList<>();
	    String query = "SELECT user.userName " +
	                   "FROM groupUserMember " +
	                   "JOIN user ON groupUserMember.userID = user.id " +
	                   "WHERE groupUserMember.groupId = ? " +
	                   "AND user.userName != ?";

	    try {
	        Connection connection = dataBaseConnection.connection();
	        PreparedStatement preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setInt(1, groupId);
	        preparedStatement.setString(2, newMemberName);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        while (resultSet.next()) {
	            String userName = resultSet.getString("userName");
	            existingMembers.add(userName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return existingMembers;
	}
	
	private void loadCheckboxState() {
        String query = "SELECT checkbox_status FROM user WHERE id = ?";

        try (Connection connection =dataBaseConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                boolean checkboxStatus = resultSet.getBoolean("checkbox_status");
                System.out.println(checkboxStatus);
                blockSearchCheck.setSelected(checkboxStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveCheckboxState(boolean isChecked) {
        String query = "UPDATE user SET checkbox_status = ? WHERE id = ?";
        try {Connection connection =dataBaseConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(2,user.getId());
            preparedStatement.setBoolean(1, isChecked);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void showNotifications(int userid) {
        try {
            Connection conn = dataBaseConnection.connection();
            PreparedStatement ps = conn.prepareStatement("SELECT notification FROM notifications WHERE user_id = ? AND hasNotification = ?");
            ps.setInt(1, userid);
            ps.setBoolean(2, false);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(null, "You have no notifications", "INFO", JOptionPane.INFORMATION_MESSAGE);
            } else {
                do {
                    String notification = rs.getString("notification");
                    JOptionPane.showMessageDialog(null, notification, "INFO", JOptionPane.INFORMATION_MESSAGE);
                } while (rs.next());
            }
            
            // Update hasNotification after reading all notifications
            PreparedStatement ps2 = conn.prepareStatement("UPDATE notifications SET hasNotification = ? WHERE user_id = ?");
            ps2.setBoolean(1, true);
            ps2.setInt(2, userid);
            ps2.executeUpdate();

            // Close resources
            rs.close();
            ps.close();
            ps2.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFriendRequest() {
    	try {
    	Connection conn = dataBaseConnection.connection();
        PreparedStatement ps = conn.prepareStatement("SELECT senderName FROM friendRequest WHERE receiverName = ? AND hasOpen = ?");
        ps.setString(1,user.getUserName());
        ps.setBoolean(2, false);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            
        } else {
            do {
                String senderName = rs.getString("senderName");
                requestListModel.addElement(senderName);
             
            } while (rs.next());
        }
    		
    		
    		
    		
    	}
    	catch(SQLException e1) {
    		e1.printStackTrace();
    		
    	}
    	
    	
    	
    }
    private void showFriendRequestDialog(String sender, int index) {
    	try {
    	Connection conn = dataBaseConnection.connection();
    	PreparedStatement ps = conn.prepareStatement("UPDATE friendRequest SET hasOpen = ? WHERE senderName = ? AND receiverName = ?");
        ps.setBoolean(1, true);
        ps.setString(2, sender);
        ps.setString(3, user.getUserName());
        ps.executeUpdate();
    	}
    	catch(SQLException e1) {
    		e1.printStackTrace();
    	}
    	
    	
        String message = "You have a friend request from " + sender + ". Do you accept?";
        String[] options = {"Accept", "Decline"};

        int response = JOptionPane.showOptionDialog(this, message, "Friend Request",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, options, options[0]);

        if (response == 0) {
            // Accept seçeneği seçildi
            requestListModel.remove(index);
            JOptionPane.showMessageDialog(this, "You have accepted the friend request from " + sender);
            user.addFriend(sender);
            updateFriendsList();
            
            
            
        } else if (response == 1) {
            // Decline seçeneği seçildi
            requestListModel.remove(index);
            JOptionPane.showMessageDialog(this, "You have declined the friend request from " + sender);
        }
    }
}
