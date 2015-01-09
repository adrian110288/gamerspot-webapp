package com.lesniak.gamerspot.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import com.lesniak.gamerspot.base.Feed;

public final class DatabaseAccessor {

	private static final String databaseUrl;
	private static final String INSERT_ALL_FEEDS_STATEMENT;
	
	static{
		databaseUrl = DatabaseConfig.initializeDatabase();
		INSERT_ALL_FEEDS_STATEMENT = "INSERT INTO feeds (guid, title, link, description, pubDate, creator, service) VALUES(?, ?, ?, ?, ?, ?, ?)";
	}
	
	public static int persistFeeds(ArrayList<Feed> feedsListToStore) {
	
		int rowsInserted = 0;
		
		try (Connection conn = DriverManager.getConnection(databaseUrl)){
			
			PreparedStatement stmt = conn.prepareStatement(INSERT_ALL_FEEDS_STATEMENT);
	
			for(Feed feed : feedsListToStore) {
				
				stmt.setString(1, feed.getGuid());
		        stmt.setString(2, feed.getTitle());
		        stmt.setString(3, feed.getLink());
		        stmt.setString(4, feed.getDescription());
		        stmt.setString(5, feed.getPubDate());
		        stmt.setString(6, feed.getCreator());
		        stmt.setString(7, feed.getService());
		        
		        int success = 2;
		        success = stmt.executeUpdate();
		        
		        if(success == 1) {
		        	rowsInserted++;
		        }
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowsInserted;
	}
}
