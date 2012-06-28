package org.finistgdg.bootcamp.android.activities;

import org.finistgdg.bootcamp.android.object.User;
import org.finistgdg.bootcamp.android.restrequest.RequestRunnable;

import org.finistgdg.bootcamp.android.activities.R;
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

public class SignupActivity extends Activity implements OnClickListener{
	
	private Button signupBtn;
	private EditText signupName;
	private EditText signupPwd;
	
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
        setContentView(R.layout.signup);

        //ui components
        signupName = (EditText) findViewById(R.id.signup_login);
        signupPwd = (EditText) findViewById(R.id.signup_pwd);
        
        signupBtn = (Button) findViewById(R.id.signup_create);
        signupBtn.setOnClickListener(this);
    }
    
    /**
     * onClick: litener to click 
     */
	public void onClick(View v) {
		//display progress dialog
		_Dialog = ProgressDialog.show(this, "", "signup en cours" , true);
		//set user
		u = new User();
		u.setName(signupName.getText().toString());
		u.setPwd(signupPwd.getText().toString());
		//start signup thread
		Thread t = new Thread(new RequestRunnable(this, _Handler, RequestRunnable.REQ_SIGNUP, u));
		t.start();
	}
	
	/**
	 * updateResultsInUi: update ui when received response from server
	 * @param pRespCode
	 */
	private void updateResultsInUi(int pRespCode){
		try{
			if(pRespCode != 200) {
				//display error
				Toast.makeText(this, "signup error", Toast.LENGTH_LONG);
				return;
			}
			_Dialog.dismiss();
			//Back to signup
			Intent i = new Intent(this, SigninActivity.class);
			i.putExtra("name", u.getName());
			i.putExtra("pwd", u.getPwd());
			startActivity(i);
		} catch(Exception e){
			
		}		
	}
}