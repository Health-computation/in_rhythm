package com.example.ds;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.TimePicker;

public class GoalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "proximanova-bold-webfont.ttf");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_goal);
		
		TextView sleepGoalTitle =(TextView) findViewById(R.id.sleepGoalTitle);
		TextView setBedtimeGoal=(TextView) findViewById(R.id.setBedtimeGoal);
		TextView wakeTimeGoalTitle =(TextView) findViewById(R.id.setWaketimeGoal);
		
		sleepGoalTitle.setTypeface(face);
		setBedtimeGoal.setText("Bedtime:");
		setBedtimeGoal.setTypeface(face);
				
		wakeTimeGoalTitle.setText("Wakeup:");
		wakeTimeGoalTitle.setTypeface(face);

		

		TimePicker wakeUpTimer=(TimePicker) findViewById(R.id.wakeGoalTimePicker);
		wakeUpTimer.setIs24HourView(true);
		TimePicker setBedTimeGoalPicker =(TimePicker) findViewById(R.id.setBedTimeGoalPicker);
		setBedTimeGoalPicker.setIs24HourView(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.goal, menu);
		return true;
	}
	
	public void onWakeUpTimeSet(View v){
		Intent mainPage = new Intent(this, MainActivity.class);
		startActivity(mainPage);	
	}

}
