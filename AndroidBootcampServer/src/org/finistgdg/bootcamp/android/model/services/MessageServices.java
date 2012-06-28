package org.finistgdg.bootcamp.android.model.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finistgdg.bootcamp.android.dao.MessageDAO;
import org.finistgdg.bootcamp.android.dao.UserDAO;

public class MessageServices {
	
	private static Log logger = LogFactory.getLog(MessageServices.class);
	
	/**
	 * Singletons private instance variable
	 */
	private static MessageServices messageServices;
	
	/**
	 * Singleton's private constructor
	 */
	private MessageServices() {
		
	}
	/**
	 * Singleton's instance getter
	 * @return
	 */
	public static MessageServices getInstance() {
		if (null == messageServices) {
			messageServices = new MessageServices();
		}
		return messageServices;
	}
	
	/**
	 * The DAO
	 */
	private MessageDAO messageDAO = MessageDAO.getInstance();
	

}
