package org.bhavnesh.google.db;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;

public class DBConnectionManager {
	private static final String FILENAME = "DBConfig.properties";

	private Connection conn = null;

	public DBConnectionManager() {
		initialiseDB();
	}

	public void initialiseDB() {
		// Create db connection
		Properties prop = new Properties();
		try {
			new FileInputStream(FILENAME);
		} catch (Exception e) {
			prop.setProperty("DBConnectionString", "jdbc:mysql://localhost:3306/oauth2_0");
			prop.setProperty("Driver", "com.mysql.jdbc.Driver");
			prop.setProperty("Username", "root");
			prop.setProperty("Password", "root");
			prop.setProperty("step", "5");
			try {
				prop.store(new FileOutputStream(FILENAME), null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		try {
			prop.load(new FileInputStream(FILENAME));
			String DBConnectionString = prop.getProperty("DBConnectionString");
			String Driver = prop.getProperty("Driver");
			String Username = prop.getProperty("Username");
			String Password = prop.getProperty("Password");
			Class.forName(Driver);
			conn = DriverManager.getConnection(DBConnectionString, Username, Password);
		} catch (Exception ex) {
			System.out.println("Exception Found while creating DBConnection : " + ex);
		}
		// Create tables
		executeUpdate(createDatabase());
		executeUpdate(createUserTableInitializationQuery());
		executeUpdate(createClientTableInitializationQuery());
		System.out.println("******************TABLE SCHEMAS CREATED********************");
	}

	public int executeUpdate(StringBuilder query) {
		int rs = -1;
		try {
			rs = conn.createStatement().executeUpdate(query.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	public ResultSet executeQuery(StringBuilder query) {
		ResultSet rs = null;
		try {
			rs = conn.createStatement().executeQuery(query.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	private StringBuilder createDatabase() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE DATABASE oauth2_0;");
		return sb;
	}

	private StringBuilder createUserTableInitializationQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE oauth2_0.google_user (Id int UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY, FirstName varchar(25) NOT NULL, LastName varchar(25) NOT NULL, EMail varchar(50) UNIQUE NOT NULL, Password varchar(25) NOT NULL, Age int , Gender ENUM('Male', 'Female') NOT NULL, Address varchar(255), Phone varchar(10));");
		return sb;
	}

	private StringBuilder createClientTableInitializationQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE oauth2_0.google_client (Id int UNIQUE NOT NULL AUTO_INCREMENT PRIMARY KEY, ClientName varchar(25) NOT NULL, ClientUsername varchar(25) UNIQUE NOT NULL, ClientPassword varchar(25) NOT NULL, ClientAddress varchar(255), ClientEmail varchar(25) NOT NULL, ClientSecret varchar(255) NOT NULL);");
		return sb;
	}
}
