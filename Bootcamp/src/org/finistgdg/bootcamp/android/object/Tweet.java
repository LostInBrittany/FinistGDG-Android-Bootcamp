package org.finistgdg.bootcamp.android.object;

/**
 * Tweet: define a message
 * @author Stephane
 *
 */
public class Tweet {
	
	private String id = null;
	private String author = null;
	private String timestamp = null;
	private String message = null;
	
	public void setId(String id) {
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthor() {
		return author;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMessage() {
		return message;
	}
}
