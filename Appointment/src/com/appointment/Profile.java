package com.appointment;

import java.util.ArrayList;
import java.util.List;

import com.appointment.login.Login;
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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Profile extends Activity {
	private final String client_id = "MJEgL7AuD2COtihOjcSOW2o88rMnAzvdFv9zzEGS";
	private final String app_id = "wnKwZraxPBKQ4Grfl8uPiSNABJZpT7UKTZrpgbra";

	EditText edtname,edtEmail;
	String strEmail;
	ArrayList<String> parseTimeStamp;
	ProgressDialog pdialog;
	Button btnLogout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		
		Parse.initialize(this, app_id, client_id);

		Bundle b1=getIntent().getExtras();
		
		strEmail=b1.getString("email").toString();
		
		edtname=(EditText)findViewById(R.id.edtName);
		edtEmail=(EditText)findViewById(R.id.edtEmail);
		btnLogout=(Button)findViewById(R.id.btnLogout);
		pdialog= new ProgressDialog(Profile.this);
		pdialog.setMessage("Please Wait....");
		pdialog.show();
		getData();
		btnLogout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent in = new Intent(Profile.this,login.class);
				startActivity(in);
				finish();
			}
		});
		//new getProfileData().execute(0);
		
	}
	public class getProfileData extends AsyncTask<Integer, Void, Integer>
	{
		
		boolean blResponse;
		ProgressDialog pdialog;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			pdialog= new ProgressDialog(Profile.this);
			pdialog.setMessage("Please Wait....");
			pdialog.show();
			super.onPreExecute();
		}


		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			
			
			getData();
			//strResponse=getData.verifyLogin(url, strUname, strPassword);
			return 0;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			pdialog.dismiss();
			
			
			super.onPostExecute(result);
		}


	}
	
	public void getData() throws ParseException {
		 parseTimeStamp = new ArrayList<String>();
	        ParseQuery<ParseObject> query = ParseQuery
	                        .getQuery("Login");
	        query.whereEqualTo("email", strEmail);
	        query.findInBackground(new FindCallback<ParseObject>() {

	               
				@Override
				public void done(List<ParseObject> item, com.parse.ParseException e) {
					
				
					      
					 
					// TODO Auto-generated method stub
					for (ParseObject itm : item) {
						Log.i("kjs",itm.getString("email").toString());
						if(itm.getString("email").equalsIgnoreCase(strEmail))
						{
							edtEmail.setText(itm.getString("email").toString());
							edtname.setText(itm.getString("username").toString());
							pdialog.dismiss();   
						}
						
					}
					 
				}
				

       });
	       
	        
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent in = new Intent(Profile.this,login.class);
		startActivity(in);
		finish();
	}
	
	

}
