package org.bhvanesh.google.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.NoSuchPaddingException;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.oauth.security.AESSecurityProvider;

public class TestMain {

	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, FileNotFoundException, IOException  {
		Calendar cal = Calendar.getInstance();
		System.out.println(cal.getTimeInMillis());
		cal.setTimeInMillis(cal.getTimeInMillis()+(5*60*1000));
		System.out.println(cal.getTimeInMillis());
		Date date = new Date();
		System.out.println(date.toString());
		
		
		
	}
	
	private void security() throws NoSuchAlgorithmException, NoSuchPaddingException{
		AESSecurityProvider provider = new AESSecurityProvider();
		System.out.println(provider.getEncodedKey());

		DBConnectionManager dbconnection = new DBConnectionManager();
		StringBuilder query = new StringBuilder();
		try {
			query.append("SELECT type from userinfo where username='");
			query.append("a");
			query.append("' and password='");
			query.append("1");
			query.append("'");
			ResultSet rs = dbconnection.executeQuery(query);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
