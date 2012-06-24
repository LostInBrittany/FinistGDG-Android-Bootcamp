package org.finistgdg.bootcamp.android.model.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finistgdg.bootcamp.android.dao.UserDAO;
import org.finistgdg.bootcamp.android.model.data.User;
import org.finistgdg.bootcamp.android.model.exception.SignInException;
import org.finistgdg.bootcamp.android.model.exception.SignUpException;

public class UserServices {


	private static Log logger = LogFactory.getLog(UserServices.class);
	
	/**
	 * Singletons private instance variable
	 */
	private static UserServices userServices;
	
	/**
	 * Singleton's private constructor
	 */
	private UserServices() {
		
	}
	/**
	 * Singleton's instance getter
	 * @return
	 */
	public static UserServices getInstance() {
		if (null == userServices) {
			userServices = new UserServices();
		}
		return userServices;
	}
	
	/**
	 * The DAO
	 */
	private UserDAO userDAO = UserDAO.getInstance();
	
	/**
	 * User SignUp
	 * @param user
	 * @throws SignUpException
	 */
	public void signUp(User user) throws SignUpException {
		userDAO.signUp(user);
	}
	
	/**
	 * User SignIn
	 * @param user
	 * @throws SignInException
	 */
	public String signIn(User user) throws SignInException {
		return userDAO.signIn(user);
	}
}
