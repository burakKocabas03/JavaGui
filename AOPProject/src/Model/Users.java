package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Helper.dataBaseConnection;
import View.ApplicationArea;

public class Users {
	
	
	public Users(String userName, String passWord , int id ) {
		super();
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
		
		
		
	}
	public Users() {
		
	}
	Connection conn = dataBaseConnection.connection();
	Statement stmt ;
	ResultSet rs;
	PreparedStatement ps;
	private int id;
	private String userName;
	private String passWord;
	
		 
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public ArrayList<String> getFriendList() throws SQLException{
		
		String friends;
		System.out.println("metoda girdi");
		ArrayList<String>friendList = new ArrayList<>();
		  try {
		        conn = dataBaseConnection.connection();
		         stmt = conn.createStatement();
		         rs = stmt.executeQuery("SELECT * FROM admin");
		        while(rs.next()) {
		        	System.out.println("döngünün içindeyim");
		        	System.out.println(this.userName);
		        	if(rs.getString("userName").equals(this.userName)) {
		        		System.out.println("iffe girdi");
		        		friends  = rs.getString("friend_ids");
		        		System.out.println(friends);
		        		String []arrOffStr = friends.split(",",-1);
		        		for (String a : arrOffStr) {
		        			System.out.println(a);
		        			friendList.add(a);
		        			
		        		}
		        		break;
		    
		        	}
		
		        }

	}
		  catch(SQLException e){
			  e.printStackTrace();
			  
			  
		  }
		  
		  return friendList;

	
	}
	
	public void addFriend(String friendUserName) {
	    int id_frnd = -1;
	    String friendIDS = null;

	    try {
	         conn = dataBaseConnection.connection();
	         stmt = conn.createStatement();
	         rs = stmt.executeQuery("SELECT * FROM admin");
	        while (rs.next()) {
	            String x = rs.getString("userName");
	            if (x.equals(friendUserName)) {
	                id_frnd = rs.getInt("id");
	            }

	            if (rs.getInt("id") == this.id) {
	                friendIDS = rs.getString("friend_ids");
	            }
	        }

	        if (id_frnd != -1 && friendIDS != null) {
	            if (friendIDS.contains(friendUserName)) {
	                
	                JOptionPane.showMessageDialog(null, "Bu kullanıcı zaten arkadaşlarınızda bulunmaktadır.", "Hata", JOptionPane.ERROR_MESSAGE);
	            } else {
	                if (!friendIDS.isEmpty()) {
	                    friendIDS += ",";
	                }
	                friendIDS += friendUserName;
	                String updateQuery = "UPDATE admin SET friend_ids = ? WHERE id = ?";
	                PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
	                updateStatement.setString(1, friendIDS);
	                updateStatement.setInt(2, this.id);
	                updateStatement.executeUpdate();
	                System.out.println("Arkadaş Eklendi");

	                String updateFriendQuery = "SELECT friend_ids FROM admin WHERE id = ?";
	                PreparedStatement updateFriendStatement = conn.prepareStatement(updateFriendQuery);
	                updateFriendStatement.setInt(1, id_frnd);
	                ResultSet friendResultSet = updateFriendStatement.executeQuery();
	                String friendFriendIDS = "";
	                if (friendResultSet.next()) {
	                    friendFriendIDS = friendResultSet.getString("friend_ids");
	                }
	                if (!friendFriendIDS.isEmpty()) {
	                    friendFriendIDS += ",";
	                }
	                friendFriendIDS += this.userName;
	                String updateFriendListQuery = "UPDATE admin SET friend_ids = ? WHERE id = ?";
	                PreparedStatement updateFriendListStatement = conn.prepareStatement(updateFriendListQuery);
	                updateFriendListStatement.setString(1, friendFriendIDS);
	                updateFriendListStatement.setInt(2, id_frnd);
	                updateFriendListStatement.executeUpdate();
	                JOptionPane.showMessageDialog(null, "User "+friendUserName+" added your friend list ","Info",JOptionPane.INFORMATION_MESSAGE);
	            }
	        } else {
	            System.out.println("Arkadaş eklenemedi");
	        }
	    } catch (SQLException e1) {
	        e1.printStackTrace();
	    }
	}
	
	public void myPurrToDtbs(String str) {
		
		String query  = "INSERT INTO posts (user_id,blogPosts) VALUES (?,?)" ;
		try { 
			conn = dataBaseConnection.connection();
	         stmt = conn.createStatement();
	         

	        ps = conn.prepareStatement(query);
	        ps.setInt(1,this.id );
            ps.setString(2,str);
            ps.executeUpdate();
	        
			
				
			
			
			
		
		}
		catch (SQLException e1) {
	        e1.printStackTrace();
	    }
		
	}

		
		
	} 


        
		
		
		
		
		
		
		
		




