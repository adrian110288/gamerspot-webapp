package com.lesniak.gamerspot.utilities;

import com.google.appengine.api.utils.SystemProperty;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

public class DatabaseConfig {

	private static final String PROJECT_ID = "gamerspot-0914";
	private static final String CLOUD_SQL_INSTANCE_NAME = "cloud-gamerspot";
	private static final String DATABASE_NAME = "gamerspot";
	private static final String CLOUD_SQL_DATABASE_USERNAME = "root";
	private static final String CLOUD_SQL_DATABASE_PASSWORD = "gamerspot15";
	private static final String LOCAL_DATABASE_USERNAME = "root";
	private static final String LOCAL_DATABASE_PASSWORD = "";
	
	private static final String JDBC_LOCAL= "jdbc:mysql://127.0.0.1:3306/"+DATABASE_NAME+"?user="+LOCAL_DATABASE_USERNAME+"&password="+LOCAL_DATABASE_PASSWORD;;
	private static final String JDBC_PRODUCTION = "jdbc:google:mysql://"+PROJECT_ID+":"+CLOUD_SQL_INSTANCE_NAME+"/"+DATABASE_NAME+"?user="+CLOUD_SQL_DATABASE_USERNAME;
	
	private static final String MYSQL_GOOGLEDRIVER_CLASS = "com.mysql.jdbc.GoogleDriver";
	private static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	
	private static boolean isInProduction;
	
	private DatabaseConfig(){
		isInProduction = inProduction();
	}
	
	public static String initializeDatabase(){
		
		String url = "";
		
		try {
			Class.forName(getMySQLDriverClassName());
			url = getDatabaseUrl();		
		} 
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		
		return url;
	}
	
	private static String getDatabaseUrl() {
			
//		return JDBC_LOCAL;
		return JDBC_PRODUCTION;
		//return (isInProduction == true) ? JDBC_PRODUCTION : JDBC_LOCAL;		
	}
	
	private static String getMySQLDriverClassName() {
		
//		return MYSQL_DRIVER_CLASS;
		return MYSQL_GOOGLEDRIVER_CLASS;
		//return (isInProduction == true) ? MYSQL_GOOGLEDRIVER_CLASS : MYSQL_DRIVER_CLASS;
	}
	
	private static boolean inProduction() {
		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
	}
}
