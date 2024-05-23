package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GroupPanelFeed implements FeedStrategy {
	private int groupId;
	  
	  public GroupPanelFeed(int groupId) {
		 this.groupId = groupId;
	  }
	  
	   
  public int getGroupId() {
		return groupId;
	}


	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}


public ResultSet fetchFeedItems(Connection conn) throws SQLException {
  	 String query = "SELECT user.userName, groupPost.groupPost, groupPost.time, groupPost.groupId " +
               "FROM groupPost " +
               "JOIN user ON groupPost.user_id = user.id " +
               "ORDER BY groupPost.time DESC";
      Statement stmt = conn.createStatement();
      return stmt.executeQuery(query);
  }
}


