package com.example.ds;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
//import android.widget.TimePicker;

public class DeviationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "proximanova-bold-webfont.ttf");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_deviation);
		
		TextView Rhythm =(TextView) findViewById(R.id.sleepGraphText);
		Rhythm.setTypeface(face);
		TextView Sleep =(TextView) findViewById(R.id.barGraphText);
		Sleep.setTypeface(face);
		TextView deviationText = (TextView) findViewById(R.id.deviationText);
		deviationText.setTypeface(face);
		deviationText.getBackground().setAlpha(200);
		Rhythm.getBackground().setAlpha(255);
		Sleep.getBackground().setAlpha(255);
		
		
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Deviation");
		//query.whereEqualTo("user_id", 3);
		query.findInBackground(new FindCallback<ParseObject>() {
		public void done(List<ParseObject> reasonsList, ParseException e) {
		    if (e == null) {
		    	Hashtable<Integer, Integer> reasonAggregation = new Hashtable<Integer, Integer>();

		    	//List of all textview IDs to update
		    	int[] textViews = {R.id.AcademicsValueTextView, R.id.SocialValueTextView, 
		    			R.id.InsomniaValueTextView, R.id.CaffeineValueTextView, R.id.PhysicalValueTextView };
		    	for(int i = 0; i < reasonsList.size(); ++i){
			    	byte[] current = reasonsList.get(i).getBytes("reasons");
			    	Log.d("CURRENT ", Integer.toString(current.length));
		    		for(int reason = 1; reason < current.length; ++reason){
		    			//Make reason value equal value of reason selection
				    	//Log.d("HERE IS THE BYTE: ", Integer.toString(Integer.parseInt(Byte.toString(current[reason]))));
		    			int reasonValue = Integer.parseInt(Byte.toString(current[reason]));
				    	//Log.d("HERE IS THE BYTE: ", Integer.toString(reasonValue));
		    			if(!reasonAggregation.containsKey(reason)){
		    				reasonAggregation.put(reason, 1);
		    			}else{
		    				int currentCount = reasonAggregation.get(reason) + reasonValue;
			    			reasonAggregation.put(reason, currentCount);	
		    			}

				    //	Log.d("HERE IS THE BYTE: ", Integer.toString(reasonValue));
		    		}		    		
		    	}
		    	Log.d("LOL", "COOL AGGREGATION DONE");
		    	for(int textView = 0; textView < textViews.length; ++textView){
		    		int reasonCount = 0;
	    			if(reasonAggregation.containsKey(textView + 1)){
	    				reasonCount = reasonAggregation.get(textView + 1);
	    			}	
    				TextView currentTextView = (TextView) findViewById(textViews[textView]);
    				currentTextView.setText(Integer.toString(reasonCount));
		    	}
		    	
		    	
		    	

		    	//byte[] b = hexStringToByteArray(current);

		    } else {
		      // something went wrong
		    	Log.d("IT DOESNT WORK",":(");
		    }
		  }
		

		
		});
		
	
		Rhythm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchNormalChartPage();
				
			}
		});
		Sleep.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				launchChartPage();	
				
			}
		});
		
		
		}
		
	private void launchChartPage(){
		Intent chartPage = new Intent(this, ChartActivity.class);
		chartPage.putExtra("activePage", "2");
		startActivity(chartPage);
		finish();
	}	
	private void launchNormalChartPage(){
		Intent chartPage = new Intent(this, ChartActivity.class);
		chartPage.putExtra("activePage", "1");
		startActivity(chartPage);
		finish();
	}	
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
		
	}