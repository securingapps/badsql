package com.securingapps.badsql;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//example from https://www.mkyong.com/spring/maven-spring-jdbc-example/
public class SpringJdbcHelper {
	
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<String> getSportsFromLogin(String login) {
		List<String> results = new ArrayList<>();
		
		String sql = "select SPORTS.sport FROM SPORTS "
				+ "INNER JOIN USERS ON SPORTS.userid=USERS.id "
				+ "WHERE USERS.login=?";
		
		//try with resources
		//cf https://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc
		try (Connection conn = dataSource.getConnection()) {		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1,login);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				results.add(rs.getString(1));
			}
		} catch(Exception e) {
			  e.printStackTrace();
		}
		
		return results;
	}
	
	public List<String> getSportsFromMultiLogins(List<String> logins) {
		List<String> results = new ArrayList<>();
		
		String sql = "select SPORTS.sport FROM SPORTS "
				+ "INNER JOIN USERS ON SPORTS.userid=USERS.id "
				+ "WHERE USERS.login IN ?";
		
		//try with resources
		//cf https://stackoverflow.com/questions/8066501/how-should-i-use-try-with-resources-with-jdbc
		try (Connection conn = dataSource.getConnection()) {
			
			PreparedStatement ps = conn.prepareStatement(sql);
			//ps.setString(1,prepareSelectIn(logins)); DOESN'T WORK AS IS  because of special characters
			// => solution below
			ps = conn.prepareStatement(sql.replace("?", prepareSelectIn(logins)));
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				results.add(rs.getString(1));
			}
		} catch(Exception e) {
			  e.printStackTrace();
		}
		
		return results;
	}
	
	public static String prepareSelectIn(List<String> logins) {
		if(logins==null || logins.size() == 0) {
			return "('DUMMY')";
		}
		
		String result = "(";
		for(String login:logins) {
			result += "'"+login +"',";
		}
		
		return result +"'DUMMY')";
	}
	
}
