package com.example.ds;

import java.util.HashSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

public class GoalSurveyActivity extends Activity {
	ImageButton mPreviousImageButton;
	HashSet<Integer> clickedButtons = new HashSet<Integer>();
	Boolean hasMetGoal = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_goal_survey);
		if(hasMetGoal){
			
		}

	}
	
	public void onClickGoal(View v){
		int viewId = ((ImageButton) v ).getId();
		ImageButton goal = (ImageButton) findViewById(viewId);
		if(mPreviousImageButton != null){
			mPreviousImageButton.setAlpha(255);
		}
		if(goal != null){
			goal.setAlpha(100);
			mPreviousImageButton = goal;
		}
	}
	
	public void onClickSubmit(View v){
		Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(mainActivity);
	
	}
	
	public void onClick(View v){
		onTextViewClick(v);
	}
	
	@SuppressLint("NewApi")
	public void onTextViewClick(View v){
		int viewId = ((TextView) v ).getId();
		int alpha = 0;
		TextView goal = (TextView) findViewById(viewId);
		Log.d("TextViewClick", Boolean.toString(clickedButtons.contains(viewId)));
		if (clickedButtons.contains(viewId)){
			goal.getBackground().setAlpha(255);
			clickedButtons.remove(viewId);
		}else{
			goal.getBackground().setAlpha(100);
			clickedButtons.add(viewId);
		}
	}

}
