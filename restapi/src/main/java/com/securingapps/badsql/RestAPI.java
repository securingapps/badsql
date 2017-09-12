package com.securingapps.badsql;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
 
@Path("/")
public class RestAPI {
	
	private static ApplicationContext context = null;
	
	private static ApplicationContext getContext() {
		if(context==null) {
			context = new ClassPathXmlApplicationContext("spring.xml");
		}
	   	return context;
	}
	
	@POST
	@Path("/getUserProfile")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserProfile(UserLogin userLogin)
	{
		UserProfile result = BadSqlHelper.getUserProfileFromLogin(userLogin);
		return Response.status(200).entity(result).build();
	}
	
	@POST
	@Path("/getUserSports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserSports(UserLogin userLogin)
	{
		SpringJdbcHelper jdbcHelper = (SpringJdbcHelper) getContext().getBean("springJDBCHelper");
		List<String> sports = jdbcHelper.getSportsFromLogin(userLogin.getLogin());
		return Response.status(200).entity(sports).build();
	}
	
	@POST
	@Path("/getMultiUserSports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMultiUserSports(List<UserLogin> userLogins)
	{
		SpringJdbcHelper jdbcHelper = (SpringJdbcHelper) getContext().getBean("springJDBCHelper");
		List<String> logins = new ArrayList<>();
		for(UserLogin userLogin:userLogins) {
			logins.add(userLogin.getLogin());
		}
		
		List<String> sports = jdbcHelper.getSportsFromMultiLogins(logins);
		return Response.status(200).entity(sports).build();
	}
	
	@POST
	@Path("/getUserSports2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserSports2(UserLogin userLogin)
	{
		List<String> sports = HibernateHelper.getSportsFromLogin(userLogin.getLogin());
		System.out.println(sports);
		return Response.status(200).entity(sports).build();
	}
	
	@POST
	@Path("/getMultiUserSports2")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMultiUserSports2(List<UserLogin> userLogins)
	{
		List<String> logins = new ArrayList<>();
		for(UserLogin userLogin:userLogins) {
			logins.add(userLogin.getLogin());
		}
		
		List<String> sports = HibernateHelper.getSportsFromMultiLogins(logins);
		return Response.status(200).entity(sports).build();
	}

	

	

}
