package com.example.ds;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
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
		TextView competeText = (TextView) findViewById(R.id.sleepGraphText);
		TextView barGraphText = (TextView) findViewById(R.id.barGraphText);
		barGraphText.setTypeface(face);
		competeText.setTypeface(face);
		
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}
	
   
}
