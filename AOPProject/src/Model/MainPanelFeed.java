package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainPanelFeed implements FeedStrategy {


	private String username;

    public MainPanelFeed(String username) {
        this.username = username;
    }

    @Override
    public ResultSet fetchFeedItems(Connection conn) throws SQLException {
        String query = "SELECT user.userName, posts.blogPosts, posts.time " +
                "FROM posts " +
                "JOIN user ON posts.user_id = user.id " +
                "ORDER BY posts.time DESC";
        PreparedStatement stmt = conn.prepareStatement(query);
        return stmt.executeQuery();
    }
}
