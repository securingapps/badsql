package com.securingapps.badsql;
import java.sql.*;

public class BadSqlHelper {
	
	  static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	  static final String DB_URL = "jdbc:mysql://localhost/BADSQL";
	  static final String USER = "root"; //Really bad
	  static final String PASS = "BadSQL"; 
	   
	  //cf https://www.tutorialspoint.com/jdbc/jdbc-sample-code.htm
	  //google search: java mysql example
	  public static UserProfile getUserProfileFromLogin(UserLogin userLogin) {
		  UserProfile result = new UserProfile();
		  String email = "NA";
		  Connection conn = null;
		  Statement stmt = null;
		  
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
			  stmt = conn.createStatement();
			  
			  //MD5 as string and not as function ....
			  String sql = "SELECT email FROM USERS where login='" + userLogin.getLogin() +
			  	"' AND password='MD5(" + userLogin.getPassword()+")'"; 
			  ResultSet rs = stmt.executeQuery(sql);
			  
			  //Really unsafe, don't even have a look at the number of results
			  if(rs.next()) {
				  email = rs.getString("email"); 
			  }

			  
			   rs.close();
			      stmt.close();
			      conn.close();
		  } catch(Exception e) {
			  e.printStackTrace();
			  return new UserProfile();
		  }
		  
		  result.setLogin(userLogin.getLogin());
		  result.setEmail(email);
		  return result;
	  }
}
