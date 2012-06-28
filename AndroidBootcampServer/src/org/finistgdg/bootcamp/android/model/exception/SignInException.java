package org.finistgdg.bootcamp.android.model.exception;

public class SignInException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1657358483359903578L;

	public SignInException() {
		super("Unable to sign in");
	}
	
	public SignInException(String message) {
		super(message);		
	}
	
	
	
	public String toJson() {
		return "\"exception\":\""+this.getMessage()+"\""; 
	}
}
