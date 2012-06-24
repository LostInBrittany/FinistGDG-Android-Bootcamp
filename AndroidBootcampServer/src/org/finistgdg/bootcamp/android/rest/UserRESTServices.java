package org.finistgdg.bootcamp.android.rest;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finistgdg.bootcamp.android.model.data.User;
import org.finistgdg.bootcamp.android.model.exception.SignInException;
import org.finistgdg.bootcamp.android.model.exception.SignUpException;
import org.finistgdg.bootcamp.android.model.services.UserServices;

/**
 *  POJO, no interface no extends
 *  
 *  The class registers its methods for the HTTP GET request using the @GET annotation.
 *  Using the @Produces annotation, it defines that it can deliver several MIME types,
 *  text, XML and HTML. 
 *  
 *  The browser requests per default the HTML MIME type.
 *  @author horacio
 *
 */
//Sets the path to base URL + /user
@Path("/user")
public class UserRESTServices {


	private static Log logger = LogFactory.getLog(UserRESTServices.class);
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String signUpPut(@DefaultValue("") @QueryParam("username") String username, @DefaultValue("") @QueryParam("password") String password) {
		return signUp(username,password);
	}	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String signInPost(@DefaultValue("") @QueryParam("username") String username, @DefaultValue("") @QueryParam("password") String password) {
		return signIn(username,password);
	}	
		
	private String signUp(String username, String password){	
		Response response;
			
		UserServices service = UserServices.getInstance();
		User user = new User();
		
		logger.info("Sign-up : Here we go!");
		
		user.setUsername(username);
		user.setPassword(password);
		
		try {
			service.signUp(user);
		} catch (SignUpException e) {
			response = Response.status(Status.BAD_REQUEST). entity(e.getMessage()+"\n").type(MediaType.APPLICATION_JSON).build();
			throw new WebApplicationException(response);
		}
		logger.info("User signed-up");
		return("O.K.\n");
	}
	
	private String signIn(String username, String password){	
		Response response;
			
		UserServices service = UserServices.getInstance();
		User user = new User();
		
		logger.info("Sign-in : Here we go!");
		
		user.setUsername(username);
		user.setPassword(password);
		
		String key;
		
		try {
			key = service.signIn(user);
		} catch (SignInException e) {
			response = Response.status(Status.BAD_REQUEST). entity(e.getMessage()+"\n").type(MediaType.APPLICATION_JSON).build();
			throw new WebApplicationException(response);
		}
		logger.info("User signed-up");
		return "\"token\":\""+key+"\"";
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {
		return ("To sign-up use PUT or POST method with 'username' and 'password'\n");
	}
		
}
