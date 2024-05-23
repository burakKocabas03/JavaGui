package Model;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import Helper.dataBaseConnection;
	
public class PremiumUser extends UserProfileDecorator {
	Connection conn;
	Statement stmt;
	ResultSet rs;
	
	
	public PremiumUser(String userName,String password,int id,UserProfile decoratedProfile) {
		super(userName,password,id,decoratedProfile);
		// TODO Auto-generated constructor stub
	}
public PremiumUser() {
	super();
		
	}
public void myPurrToDtbs(String str) {
	int count = this.postCount();
	if (count<15) {
	
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
	
	else{
		JOptionPane.showMessageDialog(null, "Reached Max Purr Count", "Hata", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
}
	}

