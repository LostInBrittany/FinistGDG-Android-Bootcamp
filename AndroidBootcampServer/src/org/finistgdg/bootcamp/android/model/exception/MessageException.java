package org.finistgdg.bootcamp.android.model.exception;

public class MessageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1963367508921673801L;
	
	public MessageException() {
		super("Problem with your message request");
	}
	
	public MessageException(String message) {
		super(message);		
	}
	
	
	
	public String toJson() {
		return "\"exception\":\""+this.getMessage()+"\""; 
	}

}
