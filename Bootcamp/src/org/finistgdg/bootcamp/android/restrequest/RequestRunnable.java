package org.finistgdg.bootcamp.android.restrequest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RequestRunnable implements Runnable {
	
	//Request type
	public static final int REQ_SIGNUP = 0;
	public static final int REQ_SIGNIN = 1;
	public static final int REQ_SEND_MSG = 2;
	public static final int REQ_GET_MSG = 3;
	public static final int REQ_GET_LAST_TWENTY_MSG = 4;
	
	private Context _Context;
	private int _Request=-1;
	private Object _Object;
	
	private Handler _Handler = new Handler();//handler use to update ui
	private Handler _HandlerForSendMessage;//handler use to send message that contain the Code return
	// Create runnable for posting
	//final Runnable _UpdateResults;

	/**
	 * RequestRunnable Ctor 
	 * @param pContext : context that calls this class 
	 * @param pHandler : handler to send return code from server
	 * @param pUpdateResults : Runnable to update the ui of the calling class 
	 * @param pRequest : request wanted
	 * @param pObject : object to send to server
	 */
	public RequestRunnable(Context pContext, Handler pHandler, int pReq, Object pObject){
		_Context = pContext;
		_HandlerForSendMessage = pHandler;
		_Object = pObject;
		_Request = pReq;
	}

	/**
	 * run: thread method
	 */
	public void run() {
		try	{
			RequestMaker req = new RequestMaker(_Request, _Context);
			int lReqCode = req.doRequest(_Object);
			//create message to post to Activity
			Message msg = new Message();
			msg.what = _Request;//set the 
			msg.arg1 = lReqCode;//set the _ReqCode
			msg.setTarget(_HandlerForSendMessage);//set the targer
			msg.sendToTarget();
			//_Handler.post(_UpdateResults);		//update UI to inform user about the inscription
		}
		catch (Exception e) {
			Log.e("bootcamp", "RequestRunnable run exception "+e.getMessage()+" "+e.getLocalizedMessage());
		}		
	}
}
