package Model;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface FeedStrategy {
    ResultSet fetchFeedItems(Connection conn) throws SQLException;
   
}
  


  
  
