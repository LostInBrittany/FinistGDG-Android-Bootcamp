package org.finistgdg.bootcamp.android.dao;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finistgdg.bootcamp.android.model.data.User;
import org.finistgdg.bootcamp.android.model.exception.SignInException;
import org.finistgdg.bootcamp.android.model.exception.SignUpException;
import org.finistgdg.bootcamp.android.tools.RandomString;

import com.google.gson.Gson;

public class UserDAO {


	private static Log logger = LogFactory.getLog(UserDAO.class);
	private Gson gson =new Gson();
	
	private HashMap<String, User> users = new HashMap<String, User>();
	private HashMap<String, String> tokens = new HashMap<String,String>();
	
	/**
	 * Singletons private instance variable
	 */
	private static UserDAO userDAO;
	
	/**
	 * Singleton's private constructor
	 */
	private UserDAO() {
		
	}
	/**
	 * Singleton's instance getter
	 * @return
	 */
	public static UserDAO getInstance() {
		if (null == userDAO) {
			userDAO = new UserDAO();
		}
		return userDAO;
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public boolean doesUserExist(User user) {
		return users.containsKey(user.getUsername());
	}
	
	/**
	 * 
	 * @param user
	 * @return
	 */
	public synchronized void signUp(User user) throws SignUpException  {
		logger.info("User Sign-up service : Received user : " + gson.toJson(user));
		if ((null == user.getUsername())||("".equals(user.getUsername())) ||
			(null == user.getPassword())||("".equals(user.getPassword()))	) {
			throw new SignUpException("User and pasword must not be empty");
		}
		if  (users.containsKey(user.getUsername())) {
			throw new SignUpException("User already exists");
		}
		users.put(user.getUsername(), user);
	}
	
	public String signIn(User user) throws SignInException {
		logger.info("User Sign-in service : Received user : " + gson.toJson(user));
		if ((null == user.getUsername())||("".equals(user.getUsername())) ||
				(null == user.getPassword())||("".equals(user.getPassword()))	) {
				throw new SignInException("User and pasword must not be empty");
			}
		if  ((!users.containsKey(user.getUsername())) || 
				(!users.get(user.getUsername()).getPassword().equals(user.getPassword()))) {
			throw new SignInException("Wrong user and/or password");
		}
		String key = RandomString.randomWithMD5(user.getUsername());
		logger.info("User : "+user.getUsername()+" Token : "+key);
		tokens.put(key,user.getUsername());
		return key;
	}

	public boolean verifyUser(String username, String token) {
		if (!tokens.containsKey(token)) {
			return false;
		}
		if (!tokens.get(token).equals(username)) {
			return false;
		}
		return true;
	}
}
