package com.example.ds;

import java.util.List;


import com.parse.*;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d("LOL", "Well");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_chart);
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "proximanova-bold-webfont.ttf");
		String defaultTab = null;
		defaultTab = getIntent().getStringExtra("activePage"); //check which tab is the default launching tab
		if(defaultTab == null){
			defaultTab = "LOL";
		}
		
		TextView competeText = (TextView) findViewById(R.id.sleepGraphText);
		TextView barGraphText = (TextView) findViewById(R.id.barGraphText);
		TextView deviationText = (TextView) findViewById(R.id.deviationText);			

		
		barGraphText.setTypeface(face);
		competeText.setTypeface(face);
		deviationText.setTypeface(face);

		ParseQuery<ParseObject> query = ParseQuery.getQuery("SleepDuration");
		query.whereEqualTo("userID", 1);
		query.setCachePolicy(ParseQuery.CachePolicy.NETWORK_ELSE_CACHE);
		query.setLimit(7);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> sleepDurationList, ParseException e) {
		        if (e == null) {
		        	
		        	//Redraw SleepBarChart on success
		    		SleepBarChart sleepBarChart = (SleepBarChart) findViewById(R.id.sleepBarChart);
		    		int[] newInformation = {0, 0, 0, 0, 0, 0, 0};
		    		for(int i = 0; i < sleepDurationList.size(); ++i){
		    			ParseObject current = sleepDurationList.get(i);
		    			Integer sleepDuration = current.getInt("sleepDuration");
		    			newInformation[i] = sleepDuration;
		    		}
		    		sleepBarChart.setInformation(newInformation);
		    		sleepBarChart.invalidate();
		    		
		    		//Redraw SleepOverlapChart on success		    		
		    		SleepOverlapChart sleepOverlapChart = (SleepOverlapChart) findViewById(R.id.sleepOverlapChart);
		    		sleepOverlapChart.setInformation(sleepDurationList);
		    		sleepOverlapChart.invalidate();
		    		
		        } else {
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }
		});

		Log.d("LOL",defaultTab);
		if(defaultTab.equals("2"))
		{
			Log.d("LOL", "IT's INSIDE");
			View view = findViewById(R.id.sleepBarChart);
	    	view.setVisibility(View.INVISIBLE);
	    	view = findViewById(R.id.sleepOverlapChart);
	    	view.setVisibility(View.VISIBLE);
		}
		
		final TextView tv = (TextView) findViewById(R.id.sleepGraphText);
        tv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View tv) {
				// TODO Auto-generated method stub
			    	View view =  findViewById(R.id.sleepBarChart);
			    	view.setVisibility(View.VISIBLE);
			    	view = findViewById(R.id.sleepOverlapChart);
			    	view.setVisibility(View.INVISIBLE);
			    	

				
			    
			}
        	
        });
        
        final TextView tv1 = (TextView) findViewById(R.id.barGraphText);
        tv1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View tv1) {
				// TODO Auto-generated method stub
				
				View view = findViewById(R.id.sleepBarChart);
		    	view.setVisibility(View.INVISIBLE);
		    	view = findViewById(R.id.sleepOverlapChart);
		    	view.setVisibility(View.VISIBLE);
			    			    
			}
        	
        });

        final TextView tv2 = (TextView) findViewById(R.id.deviationText);
        tv2.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		    	launchDeviationPage();
		    }
		});
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}
	private void launchDeviationPage(){
		Intent DeviationPage = new Intent(this, DeviationActivity.class);
		startActivity(DeviationPage);	
		finish();
	}
   
}
