package stephane.castrec.bootcamp.activities;

import stephane.castrec.bootcamp.object.User;
import stephane.castrec.bootcamp.restrequest.RequestMaker;
import stephane.castrec.bootcamp.restrequest.RequestRunnable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SigninActivity extends Activity implements OnClickListener{
	
	//ui elements
	private Button signinBtn;
	private Button signupBtn;
	private EditText signinName;
	private EditText signinPwd;
	
	//progress dialog fo waiting
	private static ProgressDialog _Dialog;
	
	private User u = null;
	/**
	 * Handler that gets reqCode from the thread
	 */
	final Handler _Handler = new Handler(){
		public void handleMessage(Message msg) {
			updateResultsInUi(msg.arg1);
		}
	};
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        //Get UI elements
        signinName = (EditText) findViewById(R.id.signin_name);
        signinPwd = (EditText) findViewById(R.id.signin_pwd);
        
        signinBtn = (Button) findViewById(R.id.signin_login);
        signinBtn.setOnClickListener(this);//add listener to the class
        signupBtn = (Button) findViewById(R.id.signin_signup);
        signupBtn.setOnClickListener(this);
    }
    
    /**
     * onCLick : listener on click
     */
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.signin_login:
			//display loading dialog
			_Dialog = ProgressDialog.show(this, "", "Login en cours" , true);
			u = new User();
			u.setName(signinName.getText().toString());
			u.setPwd(signinPwd.getText().toString());
			//start login request
			Thread t = new Thread(new RequestRunnable(this, _Handler, RequestRunnable.REQ_SIGNIN, u));
			t.start();
			break;
		case R.id.signin_signup:
			//display signup page
			startActivity(new Intent(this, SignupActivity.class));
			break;
		}

	}
	
	/**
	 * updateResultsInUi : update ui from receiving server response
	 * @param pRespCode
	 */
	private void updateResultsInUi(int pRespCode){
		try{
			if(pRespCode != 200) {
				//nok, display error
				Toast.makeText(this, "signin error", Toast.LENGTH_LONG);
				return;
			}
			if(_Dialog != null)
				_Dialog.dismiss();
			//login ok, start mainactivity
			Intent i = new Intent(this, MainActivity.class);
			startActivity(i);
		} catch(Exception e){
			
		}		
	}
}