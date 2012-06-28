package stephane.castrec.bootcamp.activities;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import stephane.castrec.bootcamp.adapters.TweetListAdapter;
import stephane.castrec.bootcamp.object.SessionObject;
import stephane.castrec.bootcamp.object.Tweet;
import stephane.castrec.bootcamp.restrequest.RequestMaker;
import stephane.castrec.bootcamp.restrequest.RequestRunnable;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	//Defining Tweet adapter which will handle data of ListView
	private TweetListAdapter adapter;

	//TimerTask for scanning
	private TimerTask timerTask;
	private Timer timer= new Timer();
	//Handler : to execute task in UI thread -> enable refreshing list
	private final Handler handler = new Handler();
	
	private Context _context = null;
	
	//list of displayed tweet
	private List<Tweet> mTweets = null;

	//indicate if we already ask for tweets
	private Boolean mFirstTweetsRequest = true;
	//request server object
	private RequestMaker req = null;

	/**
	 * Handler that gets reqCode from the thread
	 * 		use on send message
	 */
	final Handler _Handler = new Handler(){
		public void handleMessage(Message msg) {
			updateResultsInUi(msg.arg1);
		}
	};
	


	/**
	 * UI components
	 */
	private ListView tweetsView = null;
	private Button sendBtn = null;
	private EditText tweetBox = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		_context = this;
		adapter = new TweetListAdapter(getApplicationContext(), mTweets);
		tweetsView = (ListView)findViewById(R.id.main_list);

		if (null == tweetsView ) {
			Log.d("bootcamp","list stay null");
		}
		if (null == tweetsView ) {
			Log.d("bootcamp","list stay null");
		}     
		tweetsView.setAdapter(adapter);
		tweetBox = (EditText)findViewById(R.id.main_msg);

		sendBtn = (Button) findViewById(R.id.main_send);
		sendBtn.setOnClickListener(this); 
	}


	/**
	 * stop scan if pause app
	 */
	@Override
	protected void onPause() {
		super.onPause();
		stopScan();
	}


	/**
	 * restart scan if app resume
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		startScan();
	}

	/**
	 * sendTweet : create thread to send tweet
	 */
	private void sendTweet() {
		//init thread
		Thread t = new Thread(new RequestRunnable(this, _Handler, RequestRunnable.REQ_SEND_MSG, tweetBox.getText().toString()));

		// check if internet available
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			//internet ok -> start thread
			t.start();
			tweetBox.setText("");
		} else {
			//no internet, display it
			Toast.makeText(this, "Tweet non envoyé. Problème réseau", Toast.LENGTH_LONG);
		}
	}

	/**
	 * updateResultsInUi
	 * 		display infos after tweet has been sent
	 * @param responseCode : code receive from server
	 */
	private void updateResultsInUi(int responseCode){
		try	{
			if(responseCode != 200){
				Toast.makeText(this, "le tweet n'a pu être envoyé", Toast.LENGTH_LONG);
			} 
		}
		catch (Exception e) {
			Log.e("bootcamp", " updateResultsInUi exception "+e.getMessage());
		}
	} 

	/**
	 * startScan: scanning for new tweets
	 */
	private void startScan() {
		timerTask = new TimerTask() {
			public void run() {
				handler.post(new Runnable() {
					public void run() {
						//execute AsyncTask
						new ReadTweets().execute();
					}
				});
			}};
			//start timerTask in 100 ms each 3000ms
			timer.schedule(timerTask, 100, 3000); 
	}

	/**
	 * stopScan: cancel timer
	 */
	public void stopScan(){
		if(timerTask!=null){
			timerTask.cancel();
		}
	}
	/**
	 * ReadTweets: AsyncTask to readTweet in Background and update un UI
	 * @author Stephane
	 *
	 */
	private class ReadTweets extends AsyncTask<Void, Void, Integer> {
		
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				//first get last 20 msg
				if(mFirstTweetsRequest || 
						SessionObject.getTweetList() == null || 
						SessionObject.getTweetList().size() == 0){
					//Get last 20 MSG
					req = new RequestMaker(RequestRunnable.REQ_GET_LAST_TWENTY_MSG, _context);
					mFirstTweetsRequest = false;
				} else {
					//Find new messages from last message
					req = new RequestMaker(RequestRunnable.REQ_GET_MSG, _context);
				}
				//execute request and return result
				return req.doRequest(null);
			} catch (Exception e) {
				Log.e("bootcamp", "doInBackground exception", e);
			}
			return -1;
		}
		@Override
		protected void onPostExecute( Integer result )  {
			try	{
				//update only if correct
				if(result == 200){
					//update list in adapters
					adapter.updateList(SessionObject.getTweetList());
					//notify adapters that list has been changed
					adapter.notifyDataSetChanged();
				}
			} catch (Exception e) {
				Log.e("bootcamp", "onPostExecute exception", e);
			}
		}
	}

	/**
	 * listen for onClick event
	 */
	public void onClick(View v) {
		switch (v.getId()){
		case R.id.main_send://if post it
			sendTweet();
			break;		
		}
	}
}