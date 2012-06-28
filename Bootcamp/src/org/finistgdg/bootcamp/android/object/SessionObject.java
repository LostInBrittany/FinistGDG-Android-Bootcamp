package org.finistgdg.bootcamp.android.object;

import java.util.LinkedList;
import java.util.List;

public class SessionObject {
	
	static private String token = null; //connection token
	static private String username = null;//username 
	static private String lastTweetTS = null;//lastTweet Timestamp sent
	static private List<Tweet> tweetList = null; //list of received tweets

	static public void setToken(String ptoken) {
		token = ptoken;
	}

	static public String getToken() {
		return token;
	}

	public static void setUsername(String username) {
		SessionObject.username = username;
	}

	public static String getUsername() {
		return username;
	}

	public static void setLastTweetTS(String lastTweetTS) {
		SessionObject.lastTweetTS = lastTweetTS;
	}

	public static String getLastTweetTS() {
		return lastTweetTS;
	}

	public static void setTweetList(List<Tweet> tweetList) {
		SessionObject.tweetList = tweetList;
	}

	public static List<Tweet> getTweetList() {
		return tweetList;
	}
	
	public static void addTwettToList(List<Tweet> pTweets) {
		if(null == tweetList){
			tweetList = new LinkedList<Tweet>();
		}
		tweetList.addAll(0, pTweets);
	}

}
