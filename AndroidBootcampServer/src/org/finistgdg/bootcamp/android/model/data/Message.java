package org.finistgdg.bootcamp.android.model.data;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
// JAX-RS supports an automatic mapping from JAXB annotated class to XML and JSON
// Isn't that cool?
//If you want you can define the order in which the fields are written
//Optional
public class Message implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7955501874505869234L;

	/**
	 * The ID
	 */
	private long id;
	
	/**
	 * The Timestamp
	 */
	private long timestamp;
	
	/**
	 * The user
	 */
	private String user;
	
	/**
	 * The content
	 */
	private String content;

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
