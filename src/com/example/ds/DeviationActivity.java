package com.example.ds;

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
		TextView Sleep =(TextView) findViewById(R.id.barGraphText);

		Rhythm.setTypeface(face);
	
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
		
	}