package org.finistgdg.bootcamp.android.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.finistgdg.bootcamp.android.model.data.Message;
import org.finistgdg.bootcamp.android.model.data.MessageList;
import org.finistgdg.bootcamp.android.model.exception.MessageException;

import com.google.gson.Gson;

public class MessageDAO {

	private static Log logger = LogFactory.getLog(MessageDAO.class);
	private Gson gson =new Gson();
	
	private MessageList list = new MessageList();
	
	/**
	 * Singletons private instance variable
	 */
	private static MessageDAO messageDAO;
	
	/**
	 * Singleton's private constructor
	 */
	private MessageDAO() {
		
	}
	/**
	 * Singleton's instance getter
	 * @return
	 */
	public static MessageDAO getInstance() {
		if (null == messageDAO) {
			messageDAO = new MessageDAO();
		}
		return messageDAO;
	}
	
	
	public long putMessage(Message msg) throws MessageException{
		long timestamp;
		try {
			timestamp = list.put(msg);
			logger.info(gson.toJson(msg));
		} catch (Exception ex) {
			throw new MessageException("Error while adding message");
		}
		return timestamp;
	}
	
	public List<Message> getLast20() throws MessageException {
		try {
			return list.getLastMessages(20);
		} catch (Exception ex) {
			throw new MessageException("Error while retrieving messages");
		}
	}
	
	public List<Message> getMessageFromTimestamp(long timestamp) throws MessageException {
		try {
			return list.getFromTimestamp(timestamp);
		} catch (Exception ex) {
			throw new MessageException("Error while retrieving messages");
		}
	}
}
