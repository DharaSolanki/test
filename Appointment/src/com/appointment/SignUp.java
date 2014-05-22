package com.appointment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class SignUp extends Activity {
	private final String client_id = "MJEgL7AuD2COtihOjcSOW2o88rMnAzvdFv9zzEGS";
	private final String app_id = "wnKwZraxPBKQ4Grfl8uPiSNABJZpT7UKTZrpgbra";
	
	private EditText edtName,edtEmail,edtPhone,edtPlace,edtAltEmail,edtPassword;
	private String strName,strEmail,strPhone,strPlace,strAltemail,strPassword;
	private ImageButton imgbtnSignup;
	ArrayList<String> parseTimeStamp;
	boolean blIsUser=false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sign_up);
		edtName=(EditText)findViewById(R.id.edtName);
		edtEmail=(EditText)findViewById(R.id.edtEmail);
		edtPhone=(EditText)findViewById(R.id.edtPhone);
		edtPlace=(EditText)findViewById(R.id.edtPlace);
		edtAltEmail=(EditText)findViewById(R.id.edtAltEmail);
		edtPassword=(EditText)findViewById(R.id.edtPassword);
		imgbtnSignup=(ImageButton)findViewById(R.id.imgbtnSignup);
		
		
		
		Parse.initialize(this, app_id, client_id);
		
		imgbtnSignup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				strName=edtName.getText().toString();
				strEmail=edtEmail.getText().toString();
				strPhone=edtPhone.getText().toString();
				strPlace=edtPlace.getText().toString();
				strAltemail=edtAltEmail.getText().toString();
				strPassword=edtPassword.getText().toString();

				try {
					
					
					Log.i("demo",strEmail);
					if(!strName.isEmpty()||!strEmail.isEmpty()||!strPassword.isEmpty())
					{
						if(isValidEmail(strEmail))
						{
							if(strPassword.length()>3)
							{
								if(!strAltemail.isEmpty())
								{
									if(!isValidEmail(strAltemail))
									{
										Toast.makeText(getApplicationContext(), "Please Enter Valid Alter Email Id",Toast.LENGTH_SHORT).show();
									}
									else
									{
										signUp();
									}
								}
								else
								{
									signUp();
								}
								
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Password length should max than 3", Toast.LENGTH_SHORT).show();
							}
						}
						else
						{
							Toast.makeText(getApplicationContext(), "Please Enter Valid Email Id", Toast.LENGTH_SHORT).show();
						}
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Please Enter Requierd Informations",Toast.LENGTH_SHORT).show();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	public final static boolean isValidEmail(String target) {
		  if (TextUtils.isEmpty(target)) {
		    return false;
		  } else {
		    return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		  }
		}
	
	
	private void getTimeStampParse(String name) {
        parseTimeStamp = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery
                        .getQuery("Login");
        query.whereEqualTo("email", strEmail);
       query.findInBackground(new FindCallback<ParseObject>() {

               
				@Override
				public void done(List<ParseObject> item, com.parse.ParseException e) {
					// TODO Auto-generated method stub
					for (ParseObject itm : item) {
                        for (int i = 0; i < itm.getList("TimeStamp").size(); i++) {
                                //parseTimeStamp.add(itm.getList("menu_item").get(i));
                                Log.v("itemnprice in Arraylist", "" + parseTimeStamp);
                                // getItems_string(itemAndPrice.get(i));

                        }

                        // items_string = itemAndPrice.toArray(new
                        // String[itemAndPrice.size()]);
                }
				}

				

        });
}



	public void signUp() throws ParseException {
		 parseTimeStamp = new ArrayList<String>();
	        ParseQuery<ParseObject> query = ParseQuery
	                        .getQuery("Login");
	        query.whereEqualTo("email", strEmail);
	        query.findInBackground(new FindCallback<ParseObject>() {

	               
				@Override
				public void done(List<ParseObject> item, com.parse.ParseException e) {
					
				
					       /*  if (e != null) {
					        	 blIsUser=true;
									Toast.makeText(getApplicationContext(), "Email is registerd",Toast.LENGTH_SHORT).show();
					         }*/
					     
					 
					// TODO Auto-generated method stub
					for (ParseObject itm : item) {
						Log.i("kjs",itm.getString("email").toString());
						if(itm.getString("email").equalsIgnoreCase(strEmail))
						{
							blIsUser=true;
							Toast.makeText(getApplicationContext(), "Email is registerd",Toast.LENGTH_SHORT).show();
						}
						
					}
					 if(!blIsUser)
				        {
							ParseObject objUserDetails = new ParseObject("Login");
							objUserDetails.put("username", strName);
							objUserDetails.put("email", strEmail);
							objUserDetails.put("phone", strPhone);
							objUserDetails.put("place", strPlace);
							objUserDetails.put("alternateEmail", strAltemail);
							objUserDetails.put("password", strPassword);
					
							objUserDetails.saveInBackground();
							Intent in = new Intent(SignUp.this,Profile.class);
							in.putExtra("email", strEmail);
							startActivity(in);
							finish();
					}
				}

				

        });
	       
	       
	}

}
