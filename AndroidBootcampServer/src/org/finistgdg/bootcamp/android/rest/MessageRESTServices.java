package org.finistgdg.bootcamp.android.rest;

import java.util.List;

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
import org.finistgdg.bootcamp.android.dao.MessageDAO;
import org.finistgdg.bootcamp.android.dao.UserDAO;
import org.finistgdg.bootcamp.android.model.data.Message;
import org.finistgdg.bootcamp.android.model.exception.MessageException;

import com.google.gson.Gson;

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
//Sets the path to base URL + /message
@Path("/message")
public class MessageRESTServices {
	
	private static Log logger = LogFactory.getLog(MessageRESTServices.class);
	private Gson gson =new Gson();
	UserDAO userDAO = UserDAO.getInstance();
	MessageDAO messageDAO = MessageDAO.getInstance();
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String addMessagePut(@DefaultValue("") @QueryParam("username") String username, 
									@DefaultValue("") @QueryParam("token") String token,
									@DefaultValue("") @QueryParam("content") String content) {
		return addMessage(username,token,content);
	}	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addMessagePost(@DefaultValue("") @QueryParam("username") String username, 
									@DefaultValue("") @QueryParam("token") String token,
									@DefaultValue("") @QueryParam("content") String content) {
		return addMessage(username,token,content);
	}	
	
	
	public String addMessage(String username, 
								String token,
								String content) {
			
			Response response;
			if (!userDAO.verifyUser(username, token)) {
				response = Response.status(Status.BAD_REQUEST). entity("Bad token\n").type(MediaType.APPLICATION_JSON).build();
				throw new WebApplicationException(response);
			}
			Message msg = new Message();
			msg.setUser(username);
			msg.setContent(content);
			
			long timestamp;
			try {
				timestamp=messageDAO.putMessage(msg);
			} catch (MessageException e) {
				response = Response.status(Status.BAD_REQUEST). entity(e.getMessage()+"\n").type(MediaType.APPLICATION_JSON).build();
				throw new WebApplicationException(response);
			}
			logger.info("Message added");
			return("{\"result\":\"ok\", \"timestamp\":"+timestamp+"}");
	}
	
	@GET
	@Path("/{timestamp}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getMessageFromTimestamp(@DefaultValue("") @QueryParam("timestamp") String ts) {
		
		Response response;
		List<Message> list;
		long timestamp;
		
		try {
			timestamp = Long.parseLong(ts);
		} catch (NumberFormatException ex) {
			response = Response.status(Status.BAD_REQUEST). entity("Parameter must be a timestamp\n").type(MediaType.APPLICATION_JSON).build();
			throw new WebApplicationException(response);
		}
		
		try {
			 list = messageDAO.getMessageFromTimestamp(timestamp);
		} catch (MessageException e) {
			response = Response.status(Status.BAD_REQUEST). entity(e.getMessage()+"\n").type(MediaType.APPLICATION_JSON).build();
			throw new WebApplicationException(response);
		}
		return gson.toJson(list);
	}
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public String getLast20Messages() {
		
		Response response;
		List<Message> list;
		
		try {
			 list = messageDAO.getLast20();
		} catch (MessageException e) {
			response = Response.status(Status.BAD_REQUEST). entity(e.getMessage()+"\n").type(MediaType.APPLICATION_JSON).build();
			throw new WebApplicationException(response);
		}
		return gson.toJson(list);
	}

}
