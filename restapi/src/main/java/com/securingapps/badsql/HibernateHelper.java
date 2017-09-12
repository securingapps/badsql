package com.securingapps.badsql;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//https://www.mkyong.com/hibernate/quick-start-maven-hibernate-mysql-example/
public class HibernateHelper {
	
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            return new Configuration().configure().buildSessionFactory();
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static List<String> getSportsFromLogin(String login) {
    	//don't want to bother writing this query in HQL language
    	//cf https://www.mkyong.com/hibernate/hibernate-native-sql-queries-examples/
		String sql = "select SPORTS.sport FROM SPORTS "
				+ "INNER JOIN USERS ON SPORTS.userid=USERS.id "
				+ "WHERE USERS.login = :login";
    	Session session = sessionFactory.openSession();
    	
    	Query query = session.createSQLQuery(sql).setParameter("login",login);
    	List<String> results = query.list();
    	
    	return results;
    	
    }
    
    public static List<String> getSportsFromMultiLogins(List<String> logins) {
		String sql = "select SPORTS.sport FROM SPORTS "
				+ "INNER JOIN USERS ON SPORTS.userid=USERS.id "
				+ "WHERE USERS.login IN :logins";
    	Session session = sessionFactory.openSession();
    	
    	String inClause = SpringJdbcHelper.prepareSelectIn(logins);
    	//Query query = session.createSQLQuery(sql).setParameter("logins", inClause);
    	//=> special characters errors
    	Query query = session.createSQLQuery(sql.replace(":logins", inClause));
    	List<String> results = query.list();
    	
    	return results;
    	
    }
}
