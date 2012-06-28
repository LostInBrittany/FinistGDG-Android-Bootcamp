package org.finistgdg.bootcamp.android.model.exception;

public class SignUpException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7756103186960834376L;

	public SignUpException() {
		super("Unable to create user");
	}
	
	public SignUpException(String message) {
		super(message);		
	}
	
	
	
	public String toJson() {
		return "\"exception\":\""+this.getMessage()+"\""; 
	}
}
