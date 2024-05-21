package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import Helper.dataBaseConnection;
import Model.Groups;
import Model.Users;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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

public class ApplicationArea extends JFrame {
	private DefaultTableModel friendModel = null;
	private String [] friendsArray = null;
	
	
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
		friendListPane.setBounds(687, 272, 130, 334);
		contentPane.add(friendListPane);
		
		friendListTable = new JTable(friendModel);
		friendListPane.setViewportView(friendListTable);

		JLabel friendListLabel = new JLabel("Friends List");
		friendListLabel.setHorizontalAlignment(SwingConstants.CENTER);
		friendListLabel.setFont(new Font("Unispace", Font.PLAIN, 15));
		friendListLabel.setBackground(Color.BLACK);
		friendListLabel.setBounds(687, 237, 130, 35);
		contentPane.add(friendListLabel);
		
		JPanel friendlistPanel = new JPanel();
		friendlistPanel.setBorder(new MatteBorder(3, 3, 3, 3, (Color) new Color(0, 0, 0)));
		friendlistPanel.setBackground(Color.PINK);
		friendlistPanel.setBounds(687, 237, 130, 35);
		contentPane.add(friendlistPanel);
		
		
		JScrollPane feedScrollPane = new JScrollPane();
		feedScrollPane.setBounds(257, 267, 389, 405);
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
				feedProccess(feedPanel);

				

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
		btnLogOut.setBounds(687, 141, 130, 29);
		contentPane.add(btnLogOut);
		
		
		JLabel purrConnectLbl = new JLabel("PURR CONNECT");
		purrConnectLbl.setFont(new Font("Unispace", Font.BOLD | Font.ITALIC, 29));
		purrConnectLbl.setToolTipText("");
		purrConnectLbl.setHorizontalAlignment(SwingConstants.CENTER);
		purrConnectLbl.setBounds(257, 684, 389, 52);
		contentPane.add(purrConnectLbl);
		
		
		
		
		

		feedProccess(feedPanel);

		
		
		
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
                    ResultSet rs = stmt.executeQuery("SELECT * FROM admin");
                    while (rs.next()) {

                        if (selectedUsername.equals(rs.getString("userName"))) {
                            Users nextuser = new Users();
                            nextuser.setId(rs.getInt("id"));
                            nextuser.setUserName(rs.getString("userName"));
                            nextuser.setPassWord(rs.getString("password"));	
                            BlogPage blgPage = new BlogPage(user,nextuser);
                            blgPage.setVisible(true);
                            dispose();
                            break;
                 
                            
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
                user.addFriend(selectedUsername);
                updateFriendsList();
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
        createGroutextPane.setBackground(Color.PINK);
        
        JButton btnNewButton = new JButton("Create Group");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		
        		
        		String query  ="INSERT INTO groupsField (groupName) VALUES (?)" ;
        		
				try {
					Connection conn2= dataBaseConnection.connection();

                   Statement stmt2 = conn2.createStatement();
                   ResultSet rs = stmt2.executeQuery("SELECT * FROM groupsField");
                   while(rs.next()) {
                   	if(rs.getString("groupName").equals(createGroutextPane .getText())) {
                   		JOptionPane.showMessageDialog(null,"This group has already been created","Message",JOptionPane.INFORMATION_MESSAGE);
                   	}else {
                   	 
   					Connection conn = dataBaseConnection.connection();
   	         		Statement stmt=conn.createStatement();
   					PreparedStatement ps = conn.prepareStatement(query);
   					ps.setString(1, createGroutextPane.getContentType());
   					ps.executeUpdate();
                   	}
                   }
                   
                   
                   
					Connection conn = dataBaseConnection.connection();
	         		Statement stmt=conn.createStatement();
					PreparedStatement ps = conn.prepareStatement(query);
					ps.setString(1, createGroutextPane .getText());
					ps.executeUpdate();
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
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
        suggestionListGroup.setSelectionForeground(Color.PINK);
        suggestionListGroup.setBackground(Color.PINK);
        suggestionListGroup.setOpaque(true);
        
        
         groupSearchScroll = new JScrollPane(suggestionListGroup);
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
         btnNewButton_1.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		
                 BlogPage blgPage = new BlogPage(user,user);
                 blgPage.setVisible(true);
                 dispose();
         		
         	}
         });
         btnNewButton_1.setBounds(6, 53, 117, 29);
         contentPane.add(btnNewButton_1);
         
         JLabel searchGroupLbl = new JLabel("Search Group");
         searchGroupLbl.setBounds(16, 168, 104, 19);
         contentPane.add(searchGroupLbl);
         
         JLabel WelcomeLabel = new JLabel("Welcome " +user.getUserName());
         WelcomeLabel.setBounds(6, 11, 180, 39);
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
         

         blockSearchCheck = new JCheckBox("Block Search");
         blockSearchCheck.setBounds(689, 89, 128, 23);
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
	                	   if(rs.getString("groupName").equals(selectedGroupName));{
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
 	                	   if(rs.getString("groupName").equals(selectedGroupName));{
 	                	    groupId = rs.getInt("groupId");
 	                	    groupName = rs.getString("groupName");
 	                	   Groups group = new Groups(groupName,groupId);
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

 		JLabel BackGroundLabel = new JLabel(new ImageIcon(getClass().getResource("catBackRound.jpg")));
 		BackGroundLabel.setOpaque(true);
 		BackGroundLabel.setFont(new Font("Unispace", Font.PLAIN, 18));
 		
 		BackGroundLabel.setHorizontalAlignment(SwingConstants.CENTER);
 		BackGroundLabel.setBounds(0, 0, 899, 837);
 		contentPane.add(BackGroundLabel);
 		
	}
	
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
                ResultSet rs = stmt.executeQuery("SELECT * FROM admin");

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
	
	public static JPanel createFeedItem(String username, String message , String time) {
        JPanel feedItemPanel = new JPanel();
        feedItemPanel.setBackground(Color.PINK);
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
		feedItemPanel.setBackground(Color.PINK);
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
        	
        	feedItemPanel = createFeedItem(username,blogStr,time);
        		mainpanel.add(feedItemPanel);
        
        	
 
        
}
}


catch(SQLException e1) {
	
	e1.printStackTrace();
}


		
	}
	
	private void loadCheckboxState() {
        String query = "SELECT checkbox_status FROM admin WHERE id = ?";

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
        String query = "UPDATE admin SET checkbox_status = ? WHERE id = ?";
        try {Connection connection =dataBaseConnection.connection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(2,user.getId());
            preparedStatement.setBoolean(1, isChecked);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}		
	