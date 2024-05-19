package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontMetrics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JMenuItem;
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
import javax.swing.SwingUtilities;
import javax.swing.JComboBox;

public class ApplicationArea extends JFrame {
	private DefaultTableModel friendModel = null;
	private String [] friendsArray = null;
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	static Users user = new Users();
	private JTextField addFriendtextField;
	private JTable friendListTable;
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
		setBounds(100, 100, 671, 715);
		contentPane = 	new JPanel();
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

		JScrollPane feedScrollPane = new JScrollPane();
		feedScrollPane.setBounds(110, 282, 389, 405);
		contentPane.add(feedScrollPane);
		
		JPanel feedPanel = new JPanel();
		feedScrollPane.setViewportView(feedPanel);
		feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
		
		
		JPanel PurrPanel = new JPanel();
		PurrPanel.setBounds(110, 193, 389, 77);
		contentPane.add(PurrPanel);
		
		JButton buttonPURR = new JButton("PURR");
	
		
		JTextArea blogPurrArea = new JTextArea();
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
		btnLogOut.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				LoginGUI lgnGui = new LoginGUI();
				lgnGui.setVisible(true);
                dispose();
			}
		});
		btnLogOut.setBounds(551, 6, 117, 29);
		contentPane.add(btnLogOut);
		
		
		
		
		
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
        searchPanel.setBounds(109, 0, 390, 159);
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
                    String userName = rs.getString("userName");
                    if (userName.toLowerCase().startsWith(searchText.toLowerCase()) &&  !userName.equals(user.getUserName()) ) {
                        suggestionListModel.addElement(userName);
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
        	
        	feedItemPanel = createFeedItem(username,blogStr,time);
        		mainpanel.add(feedItemPanel);
        
        	
 
        
}
}


catch(SQLException e1) {
	
	e1.printStackTrace();
}

		
	}
		
}		
	

