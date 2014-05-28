package com.appointment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.appointment.MultiSpinner.MultiSpinnerListener;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class ProviderSignupProfile extends Activity implements MultiSpinnerListener {
	MultiSpinner multiSpinner;
	static final int START_TIME_DIALOG_ID = 1111;
	static final int END_TIME_DIALOG_ID = 0000;
	Button btnStart,btnEnd,btnSubmit;
	TextView txtStartTime,txtEndTime;
	private int hourstart,hoursend,hour;
    private int minutestart,minuteend,minute;
    String strSelSchedule = null ,strStartTime,strEndTime ;
	List<String> items;
	 String[] array = { "Mon", "Tue", "Wed","Thu", "Fri","Sat","Sun"};  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.provider_signup_profile);
		btnStart=(Button)findViewById(R.id.btnStartTime);
		btnEnd=(Button)findViewById(R.id.btnEndTime);
		btnSubmit=(Button)findViewById(R.id.btnSubmit);
		txtStartTime=(TextView)findViewById(R.id.txtStartTime);
		txtEndTime=(TextView)findViewById(R.id.txtEndTime);
		MultiSpinner multiSpinner = (MultiSpinner) findViewById(R.id.multi_spinner);
		
		
		items= new ArrayList<String>();
		items.add("Mon");
		items.add("Tue");
		items.add("Wed");
		items.add("Thu");
		items.add("Fri");
		items.add("Sat");
		items.add("Sun");
		multiSpinner.setItems(items, getString(R.string.day_schedule_prompt),this);
		 
        
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		
		btnEnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(END_TIME_DIALOG_ID);
				
			}
		});
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				strStartTime=txtStartTime.getText().toString();
				strEndTime=txtEndTime.getText().toString();
				if(strSelSchedule!=null && !strSelSchedule.equals("0000000") && !strStartTime.isEmpty()&&!strEndTime.isEmpty())
				{
					String pattern = "HH:mm";
		            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		            try {
		                Date time1 = sdf.parse(hourstart+":"+minutestart);
		                Date time2 = sdf.parse(hoursend+":"+minuteend);

		                int time=time2.compareTo(time1);
		                Log.i("provider","Compare Time is : "+time+" Or "+time2.compareTo(time1));
		                if(time==1)
		                {
		                	
		                }
		                else
		                {
		                	Toast.makeText(getApplicationContext(), "end time should be after start time", Toast.LENGTH_SHORT).show();
		                }
		               

		            } catch (ParseException e){
		                // Exception handling goes here
		            }
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Please Enter All Requier Informations.", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	
	 
	@Override
	public void onItemsSelected(boolean[] selected) {
		// TODO Auto-generated method stub
		
		for(int i=0;i<selected.length;i++)
		{
			if(i==0)
			{
				if(selected[i])
				{
					strSelSchedule="1";
				}
				else
				{
					strSelSchedule="0";
				}
			}
			else
			{
				if(selected[i])
				{
					strSelSchedule=strSelSchedule+"1";
				}
				else
				{
					strSelSchedule=strSelSchedule+"0";
				}
			}
		}
		Log.i("Providr","Selected Schedules are :"+strSelSchedule);
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
		final Calendar c = Calendar.getInstance();
		 // Current Hour
       hour = c.get(Calendar.HOUR_OF_DAY);
       // Current Minute
       minute = c.get(Calendar.MINUTE);
        switch (id) {
        case START_TIME_DIALOG_ID:
             
            // set time picker as current time
            return new TimePickerDialog(this, sarttimePickerListener, hour, minute,
                    false);
        case END_TIME_DIALOG_ID:
        	return new TimePickerDialog(this, endtimePickerListener, hour, minute,
                    false);
            
        }
        return null;
    }

	 private TimePickerDialog.OnTimeSetListener sarttimePickerListener = new TimePickerDialog.OnTimeSetListener() {
         
		 
	        @Override
	        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
	            // TODO Auto-generated method stub
	            hour   = hourOfDay;
	            minute = minutes;
	            
	            hourstart   = hourOfDay;
	            minutestart = minutes;
	 
	            updateTime(hour,minute,"start");
	             
	         }
	 
	    };
	    
	    private TimePickerDialog.OnTimeSetListener endtimePickerListener = new TimePickerDialog.OnTimeSetListener() {
	         
			 
	        @Override
	        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
	            // TODO Auto-generated method stub
	            hour   = hourOfDay;
	            minute = minutes;
	 
	            hoursend   = hourOfDay;
	            minuteend = minutes;
	            
	            updateTime(hour,minute,"end");
	             
	         }
	 
	    };
	 
	    private static String utilTime(int value) {
	         
	        if (value < 10)
	            return "0" + String.valueOf(value);
	        else
	            return String.valueOf(value);
	    }
	     
	    // Used to convert 24hr format to 12hr format with AM/PM values
	    private void updateTime(int hours, int mins,String time) {
	         
	        String timeSet = "";
	        if (hours > 12) {
	            hours -= 12;
	            timeSet = "PM";
	        } else if (hours == 0) {
	            hours += 12;
	            timeSet = "AM";
	        } else if (hours == 12)
	            timeSet = "PM";
	        else
	            timeSet = "AM";
	 
	         
	        String minutes = "";
	        if (mins < 10)
	            minutes = "0" + mins;
	        else
	            minutes = String.valueOf(mins);
	 
	        // Append in a StringBuilder
	         String aTime = new StringBuilder().append(hours).append(':')
	                .append(minutes).append(" ").append(timeSet).toString();
	         if(time.equals("start"))
	         {
	        	 txtStartTime.setText(aTime);
	         }
	         else
	         {
	        	 txtEndTime.setText(aTime);
	         }
	          
	    }
}
