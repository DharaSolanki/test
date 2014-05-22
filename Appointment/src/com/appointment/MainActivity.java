package com.appointment;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	EditText edtUsername,edtPassword;
	ImageButton btnLogin;
	String strUname,strPassword;
	String log = "MainActivity";
	Connect wifiConnection;
	GetData getData;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		edtUsername=(EditText)findViewById(R.id.edtEmail);
		edtPassword=(EditText)findViewById(R.id.edtPassword);
		btnLogin=(ImageButton)findViewById(R.id.imgGo);
		wifiConnection= new Connect(MainActivity.this);
		wifiConnection.ConnectWithWifi();
		getData=new GetData();
		strUname=edtUsername.getText().toString();
		strPassword=edtPassword.getText().toString();
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(strUname!=null ||strUname!="")
				{
					if(isValidEmail(strUname))
					{
						if(strPassword!=null ||strPassword!="")
						{
							if(strPassword.length()>3)
							{
								if(wifiConnection.isWifiConnected())
								{
									new Login().execute(0);
								}
								else 
								{
									Toast.makeText(MainActivity.this,"Please Unable wifi", Toast.LENGTH_SHORT).show();
								}
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Password length should be more than 3", Toast.LENGTH_SHORT).show();
							}
						}
						else {
							Log.i(log,"No Password");
							Toast.makeText(MainActivity.this,"Please Enter Password.",Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please enter valid email id", Toast.LENGTH_SHORT).show();
					}
				}
				else
				{
					Log.i(log,"No Username");
					Toast.makeText(MainActivity.this,"Please Enter Username.",Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}

	public final static boolean isValidEmail(CharSequence target) {
		  if (TextUtils.isEmpty(target)) {
		    return false;
		  } else {
		    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		  }
		}
	
	public class Login extends AsyncTask<Integer, Void, Integer>
	{
		
		String strResponse;
		ProgressDialog pdialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog= new ProgressDialog(MainActivity.this);
			pdialog.setMessage("Please Wait....");
			pdialog.show();
			super.onPreExecute();
		}


		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			String url="";
			
			
			strResponse=getData.verifyLogin(url, strUname, strPassword);
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			
			if(strResponse!=null)
			{
				Log.i(log,"Success");
				Toast.makeText(MainActivity.this,strResponse,Toast.LENGTH_SHORT);
			}
			
			super.onPostExecute(result);
		}


	}
	

}
