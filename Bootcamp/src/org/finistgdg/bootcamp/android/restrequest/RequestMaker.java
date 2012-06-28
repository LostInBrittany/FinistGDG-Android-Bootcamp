package org.finistgdg.bootcamp.android.restrequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.finistgdg.bootcamp.android.object.SessionObject;
import org.finistgdg.bootcamp.android.object.User;

import android.content.Context;
import android.util.Log;
import android.util.Xml.Encoding;

/**
 * RequestMaker : do all httpPost 
 * 				  Call JsonParser to create or decode Json object 
 * @author Stephane
 *
 */
//signup
//PUT /AndroidBootcampServer/rest/user?username=horacio&password=toto

//signin
//POST /AndroidBootcampServer/rest/user?username=horacio&password=toto

//send msg
//POST /AndroidBootcampServer/rest/message?username=horacio&token=ba7db59b5c2a7173c38bc1cc601f1f8c&content=Hello%20world!

//get msg without token -> error
//GET /AndroidBootcampServer/rest/message

//get msg  GET /AndroidBootcampServer/rest/message?timestamp=1340573987746
public class RequestMaker {

	//urls
	static private final String URL_SIGN = "http://lostinbrittany.org/java/AndroidBootcampServer/rest/user?";
	static private final String URL_MSG = "http://lostinbrittany.org/java/AndroidBootcampServer/rest/message?";
	static private final String URL_MSG_TS = "http://lostinbrittany.org/java/AndroidBootcampServer/rest/message/timestamp?";
	private int _idReq = -1;
	/**
	 * RequestMaker Ctor
	 * @param pUrl: Url to retrieve
	 * @param pContext : context of the view
	 */
	public RequestMaker(int pIdReq, Context pContext){
		_idReq = pIdReq;
	}

	/**
	 * doRequest : make the request to the server
	 * @param pObject : object to send to serveur
	 * @return : return code of the http response 
	 */
	public int doRequest(Object pObject){
		try	{

			//create HTTPClient
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse response = null;

			//adapt request to by request
			switch (_idReq){
			case RequestRunnable.REQ_GET_MSG:
				HttpGet httpgetmsg = new HttpGet(getUrl(pObject));
				Log.i("bootcamp", "message receive "+httpgetmsg.getURI());
				response = httpclient.execute(httpgetmsg);
				break;
			case RequestRunnable.REQ_GET_LAST_TWENTY_MSG:
				HttpGet httpget = new HttpGet(getUrl(pObject));
				response = httpclient.execute(httpget);
				Log.i("bootcamp", "message receive "+httpget.getURI());
				break;
			case RequestRunnable.REQ_SEND_MSG:
			case RequestRunnable.REQ_SIGNIN:
				HttpPost httppost = new HttpPost(getUrl(pObject));
				//httppost.setEntity(new UrlEncodedFormEntity(getParamsToSend(pObject)));
				//execute and get the response
				response = httpclient.execute(httppost);
				Log.i("bootcamp", "message receive "+httppost.getURI());
				break;
			case RequestRunnable.REQ_SIGNUP:
				HttpPut httpput = new HttpPut(getUrl(pObject));
				//httpput.setEntity(new UrlEncodedFormEntity(getParamsToSend(pObject)));
				//execute and get the response
				response = httpclient.execute(httpput);
				Log.i("bootcamp", "message receive "+httpput.getURI());
				break;
			}

			//if receive something
			if(response != null){
				//if response NOK
				if(response.getStatusLine().getStatusCode() != 200){
					return response.getStatusLine().getStatusCode();
				} 
				//get response
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));        
				//parsing response
				StringBuilder sb = new StringBuilder();		    
				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				String pMessage = sb.toString();
				Log.i("bootcamp", "message receive "+pMessage);
				//parse JSON response
				dispatchToJsonParser(pMessage);
				//save username if signin request done
				if(_idReq == RequestRunnable.REQ_SIGNIN){
					SessionObject.setUsername(((User)pObject).getName());
				}
				return 200;
			}
		}
		catch (Exception e) {
			Log.e("bootcamp", "RequestMaker doRequest exception "+e.getMessage());
		}
		return 1;
	}

	/**
	 * dispatchToJsonParser : parse JSON depending on request
	 * @param pMessage
	 */
	private void dispatchToJsonParser(String pMessage) {
		try{
			switch(_idReq){
			case RequestRunnable.REQ_GET_MSG:
				SessionObject.addTwettToList(JsonParser.parseJsonFromGetMsgs(pMessage));
				break;
			case RequestRunnable.REQ_GET_LAST_TWENTY_MSG:
				SessionObject.setTweetList(JsonParser.parseJsonFromGetMsgs(pMessage)); 
				break;
			case RequestRunnable.REQ_SEND_MSG:
				SessionObject.setLastTweetTS(JsonParser.parseJsonFromSendMsg(pMessage));
				break;
			case RequestRunnable.REQ_SIGNIN:
				SessionObject.setToken(JsonParser.parseJsonFromSignin(pMessage));
				break;
			case RequestRunnable.REQ_SIGNUP:
				//No json receive
				break;
			}
		}catch(Exception e){

		}
	}

	/**
	 * getUrl: get url depending on request
	 * @param pObj
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String getUrl(Object pObj) throws UnsupportedEncodingException{
		switch(_idReq){
		case RequestRunnable.REQ_GET_MSG:
			return URL_MSG_TS+"timestamp="+SessionObject.getTweetList().get(0).getTimestamp();
		case RequestRunnable.REQ_GET_LAST_TWENTY_MSG:
			return URL_MSG+"token="+SessionObject.getToken()+"&username="+SessionObject.getUsername();
		case RequestRunnable.REQ_SEND_MSG:
			String lMessage = URLEncoder.encode((String)pObj, "UTF-8");
			return URL_MSG+"token="+SessionObject.getToken()+"&username="+SessionObject.getUsername()+"&content="+lMessage;
		case RequestRunnable.REQ_SIGNIN:
		case RequestRunnable.REQ_SIGNUP:
			return URL_SIGN+"username="+((User)pObj).getName()+"&password="+((User)pObj).getName();
		}
		return null;
	}
}
