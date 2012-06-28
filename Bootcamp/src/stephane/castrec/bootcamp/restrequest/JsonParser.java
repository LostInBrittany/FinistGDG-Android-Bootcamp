package stephane.castrec.bootcamp.restrequest;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import stephane.castrec.bootcamp.object.SessionObject;
import stephane.castrec.bootcamp.object.Tweet;

import android.util.Log;

public class JsonParser {
	
	/**
	 * parseJsonFromSignin: get data from signin response
	 * @param pMessage
	 * @return
	 */
	static public String parseJsonFromSignin(String pMessage){
		try{
			JSONObject jObject = new JSONObject(pMessage);
			return jObject.getString("token");
		}catch (Exception e){
			Log.e("bootcamp", "parseJsonFromSignin exception ",e);
		}	
		return null;
	}
	
	/**
	 * parseJsonFromSendMsg : get data from send msg response
	 * @param pMessage
	 * @return
	 */
	static public String parseJsonFromSendMsg(String pMessage){
		try{
			JSONObject jObject = new JSONObject(pMessage);			
			return jObject.getString("timestamp");
		}catch (Exception e){
			Log.e("bootcamp", "parseJsonFromSignin exception ",e);
		}	
		return "";
	}
	
	/**
	 * parseJsonFromSendMsg : get data from get msg response
	 * @param pMessage
	 * @return
	 */
	static public List<Tweet> parseJsonFromGetMsgs(String pMessage){
		try{
			Log.d("Bootcamp", "parseJsonFromGetMsgs "+ pMessage);
			JSONArray jArray = new JSONArray(pMessage);
			LinkedList<Tweet> lTweets = new LinkedList<Tweet>();
			Tweet t = null;
			// parse all json
			for(int i =0; i<jArray.length(); i++){
				t = new Tweet();
				t.setAuthor(jArray.getJSONObject(i).get("user").toString());
				t.setId(jArray.getJSONObject(i).get("id").toString());
				t.setTimestamp(jArray.getJSONObject(i).get("timestamp").toString());
				t.setMessage(jArray.getJSONObject(i).get("content").toString());
				lTweets.add(t);
			}
			return lTweets;
		} catch (Exception e){
			Log.d("Bootcamp", "parseJsonFromGetMsgs exception"+ pMessage);
		}
		return null;
	}


}
