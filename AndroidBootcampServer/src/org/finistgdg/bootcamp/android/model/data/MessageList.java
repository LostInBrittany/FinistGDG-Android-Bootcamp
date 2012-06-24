package org.finistgdg.bootcamp.android.model.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MessageList {
	
	private static Log logger = LogFactory.getLog(MessageList.class);
	private HashMap<String,Message> theList = new HashMap<String,Message>();;
	private HashMap<String,String> timestampIndex = new HashMap<String,String>();
	private HashMap<String,List<String>> usernameIndex = new HashMap<String,List<String>>();
	private ArrayList<String> timestampHeap = new ArrayList<String>();
	private long msg_id = 0;
	
	
	public MessageList() {
		
	}
	
	public synchronized long put(Message msg) {
		Date date= new Date();
		long timestamp = date.getTime();
		long id = msg_id++;
		
		msg.setId(id);
		msg.setTimestamp(timestamp);
		
		timestampHeap.add(Long.toString(timestamp));
		theList.put(Long.toString(id), msg);
		timestampIndex.put(Long.toString(timestamp), Long.toString(id));
		if (!usernameIndex.containsKey(msg.getUser())) {			
			usernameIndex.put(msg.getUser(), new ArrayList<String>());
		}
		usernameIndex.get(msg.getUser()).add(Long.toString(id));
		return timestamp;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Message get(long id) {
		return theList.get(Long.toString(id));
	}
	
	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public Message getByTimestamp(long timestamp) {
		
		if (!timestampIndex.containsKey(Long.toString(timestamp))) {
			return null;
		}
		return theList.get(timestampIndex.get(Long.toString(timestamp)));
	}

	
	public List<Message> getFromTimestamp(long timestamp) {
		
		logger.info("Timestamp searched : "+timestamp);
		
		ArrayList<Message> list = new ArrayList<Message>();
		
		
		
		int i = timestampHeap.size()-1;
		
		logger.info("Timestamp searched : "+timestamp + ", i : " + i + ", Stack : "+ Long.decode(timestampHeap.get(i)));
		while ((Long.decode(timestampHeap.get(i)) > timestamp) && i>=0){
			list.add(theList.get(timestampIndex.get(timestampHeap.get(i))));
			i--;
		}
		
		return list;
	}
	
	public List<Message> getLastMessages(int i) {
		ArrayList<Message> list = new ArrayList<Message>();
		
		String timestamp;
		
		for (int j=0; (j<i) && (timestampHeap.size()-j-1 >= 0) ; j++) {
			timestamp = timestampHeap.get(timestampHeap.size()-j-1);
			list.add(theList.get(timestampIndex.get(timestamp)));
		}
		return list;	
	}

		
}
