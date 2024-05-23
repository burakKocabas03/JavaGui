package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BlogPanelFeed implements FeedStrategy {
 private String username;
	 
	 public BlogPanelFeed (String username) {
		    this.username=username;
	 }

	 
    public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public ResultSet fetchFeedItems(Connection conn) throws SQLException {
        String query = "SELECT user.userName, posts.blogPosts, posts.time " +
                "FROM posts " +
                "JOIN user ON posts.user_id = user.id " +
                "ORDER BY posts.time DESC";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }
}


