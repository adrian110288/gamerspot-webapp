package com.lesniak.gamerspot.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.google.appengine.api.log.LogQuery;
import com.lesniak.gamerspot.base.Feed;
import com.lesniak.gamerspot.servlets.FeedFetchCronJob;

public final class DatabaseAccessor {

	private static Logger logger = Logger.getLogger(DatabaseAccessor.class.getName());
	
	private static final String databaseUrl;
	private static final String INSERT_ALL_FEEDS_STATEMENT, GET_ALL, GET_ALL_BY_SERVICE;
	
	static{
		databaseUrl = DatabaseConfig.initializeDatabase();
		INSERT_ALL_FEEDS_STATEMENT = "INSERT INTO feeds (guid, title, link, description, pubDate, creator, service) VALUES(?, ?, ?, ?, ?, ?, ?)";
		GET_ALL_BY_SERVICE = "SELECT * FROM feeds WHERE service = ?";
		GET_ALL = "SELECT * FROM feeds";
	}
	
	public static int persistFeeds(ArrayList<Feed> feedsListToStore) {
	
		int rowsInserted = 0;
		PreparedStatement stmt = null;
		
		try (Connection conn = DriverManager.getConnection(databaseUrl)){
			
			stmt = conn.prepareStatement(INSERT_ALL_FEEDS_STATEMENT);
	
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
					
			logger.info(e.getMessage());
			
			e.printStackTrace();
		} catch (Exception e) {
			logger.info(e.getMessage());
			
		}finally {
			closeStatement(stmt);
		}
		
		return rowsInserted;
	}
	
	public static ArrayList<Feed> getAll() {
		
		ArrayList<Feed> queryResult = new ArrayList<>();
		PreparedStatement stmt = null;
		
		try (Connection conn = DriverManager.getConnection(databaseUrl)){
			
			stmt = conn.prepareStatement(GET_ALL);
			ResultSet resultSet = stmt.executeQuery();
			queryResult = assembleListFromResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			closeStatement(stmt);
		}
		
		return queryResult;
		
	}
	
	public static ArrayList<Feed> getAllByService(String serviceQueried) {
		
		ArrayList<Feed> queryResult = new ArrayList<>();
		PreparedStatement stmt = null;
		
		try (Connection conn = DriverManager.getConnection(databaseUrl)){
			
			stmt = conn.prepareStatement(GET_ALL_BY_SERVICE);
			stmt.setString(1, serviceQueried.toUpperCase());
			
			ResultSet resultSet = stmt.executeQuery();
			queryResult = assembleListFromResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeStatement(stmt);
		}
		
		return queryResult;
	}
	
	private static void closeStatement(PreparedStatement stmt) {
		
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static ArrayList<Feed> assembleListFromResultSet(ResultSet resultSet) {
		
		ArrayList<Feed> list = new ArrayList<Feed>();
		
		try {
		
			while(resultSet.next()) {
				
				Feed feed = new Feed();
				feed.setGuid(resultSet.getString("guid"));
				feed.setTitle(resultSet.getString("title"));
				feed.setLink(resultSet.getString("link"));
				feed.setDescription(resultSet.getString("description"));
				feed.setPubDate(resultSet.getString("pubDate"));
				feed.setCreator(resultSet.getString("creator"));
				feed.setService(resultSet.getString("service"));
				
				list.add(feed);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}	
}
