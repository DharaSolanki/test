package com.appointment;

import java.util.ArrayList;
import java.util.List;

import com.appointment.MainActivity.Login;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class login extends Activity {
	private final String client_id = "MJEgL7AuD2COtihOjcSOW2o88rMnAzvdFv9zzEGS";
	private final String app_id = "wnKwZraxPBKQ4Grfl8uPiSNABJZpT7UKTZrpgbra";
	EditText edtUsername,edtPassword;
	ImageButton btnLogin,btnSignup;
	String strUname,strPassword;
	String log = "MainActivity";
	Connect wifiConnection;
	GetData getData;
	ArrayList<String> parseTimeStamp;
	boolean blIsUser=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		
		
		edtUsername=(EditText)findViewById(R.id.edtEmail);
		edtPassword=(EditText)findViewById(R.id.edtPassword);
		btnLogin=(ImageButton)findViewById(R.id.imgbtnLogin);
		btnSignup=(ImageButton)findViewById(R.id.imgbtnSignup);
		wifiConnection= new Connect(login.this);
		wifiConnection.ConnectWithWifi();
		getData=new GetData();
		Parse.initialize(this, app_id, client_id);
		
		btnSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent inSignup = new Intent(login.this,SignUp.class);
				startActivity(inSignup);
				finish();
			}
		});
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				strUname=edtUsername.getText().toString();
				strPassword=edtPassword.getText().toString();
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
									Toast.makeText(getApplicationContext(),"Please Unable wifi", Toast.LENGTH_SHORT).show();
								}
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Password length should be more than 3", Toast.LENGTH_SHORT).show();
							}
						}
						else {
							Log.i(log,"No Password");
							Toast.makeText(getApplicationContext(),"Please Enter Password.",Toast.LENGTH_SHORT).show();
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
					Toast.makeText(getApplicationContext(),"Please Enter Username.",Toast.LENGTH_SHORT).show();
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
		
		boolean blResponse;
		ProgressDialog pdialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog= new ProgressDialog(login.this);
			pdialog.setMessage("Please Wait....");
			pdialog.show();
			super.onPreExecute();
		}


		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			
			
			 signUp();
			//strResponse=getData.verifyLogin(url, strUname, strPassword);
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			pdialog.dismiss();
			if(blIsUser)
			{
				Log.i(log,"Success is");
				Toast.makeText(login.this,"Login is Success",Toast.LENGTH_SHORT).show();
			}
			
			super.onPostExecute(result);
		}


	}
	public void signUp() throws ParseException {
		 parseTimeStamp = new ArrayList<String>();
	        ParseQuery<ParseObject> query = ParseQuery
	                        .getQuery("Login");
	        Log.i("demo",strUname);
	        query.whereEqualTo("email", strUname);
	        blIsUser=false;
	        query.findInBackground(new FindCallback<ParseObject>() {

	               
				@Override
				public void done(List<ParseObject> item, com.parse.ParseException e) {
					// TODO Auto-generated method stub
					for (ParseObject itm : item) {
						Log.i("kjs",itm.getString("email").toString());
						if(itm.getString("email").equalsIgnoreCase(strUname) && itm.getString("password").equalsIgnoreCase(strPassword))
						{
							blIsUser=true;
							
							Toast.makeText(getApplicationContext(), "Email is registerd",Toast.LENGTH_SHORT).show();
							break;
						}
						
					}
					if(blIsUser)
					{
						Log.i(log,"Success");
						Toast.makeText(getApplicationContext(),"Login Success",Toast.LENGTH_SHORT).show();
						Intent in = new Intent(login.this,Profile.class);
						in.putExtra("email", strUname);
						startActivity(in);
						finish();
					}
					else
					{
						Log.i(log,"Not Success");
						Toast.makeText(getApplicationContext(),"Login Not Success",Toast.LENGTH_SHORT).show();
					}
					
					 
				}
				
				

       });
			
			
	       
	       
	}
	
}
