package com.example.ds;
import com.parse.Parse; 
import com.parse.ParseException; 
import com.parse.ParseObject; 
import com.parse.ParseQuery;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
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
	byte[] mClicked = {0,0,0,0,0,0};
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
		int defaultSelect = 0;
		int viewId = ((ImageButton) v ).getId();
		
		ImageButton goal = (ImageButton) findViewById(viewId);
		if(mPreviousImageButton != null){
			mPreviousImageButton.setAlpha(255);
		}
		if(goal != null){
			goal.setAlpha(100);
			mPreviousImageButton = goal;
		}
		if (viewId == R.id.ImageButtonG)
		{
			defaultSelect = 1;
		}else if(viewId == R.id.ImageButtonR){
			defaultSelect = 2;
		}else{
			defaultSelect = 3;
		}
			mClicked[0]=(byte) defaultSelect;	
	}
	
	public void onClickSubmit(View v){
		Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
		startActivity(mainActivity);
		
		
		Log.d("CHECK IT OUT",Arrays.toString(mClicked));
		
		Date myDate = new Date();
		
		ParseObject deviation = new ParseObject("Deviation");
		deviation.put("user_id", this.getResources().getInteger(R.integer.user_id));
		deviation.put("reasons", mClicked);
		deviation.put("date", myDate);
		deviation.saveInBackground();
		
	}
	
	public void onClick(View v){
		onTextViewClick(v);
	}
	
	@SuppressLint("NewApi")
	public void onTextViewClick(View v){
		//int defaultSelect=0;
		int viewId = ((TextView) v ).getId();
		int alpha = 0;
		TextView goal = (TextView) findViewById(viewId);
		Log.d("TextViewClick", Boolean.toString(clickedButtons.contains(viewId)));
		if (clickedButtons.contains(viewId)){
			goal.getBackground().setAlpha(255);
			clickedButtons.remove(viewId);
			if (viewId == R.id.academics)
			{
				mClicked[1]=(byte) 0;
			}else if(viewId == R.id.social){
				mClicked[2]=(byte) 0;
			}else if(viewId == R.id.insomnia){
				mClicked[3]=(byte) 0;
			}else if(viewId == R.id.caffeine){
				mClicked[4]=(byte) 0;
			}else if(viewId == R.id.wellbeing){
				mClicked[5]=(byte) 0;
			}
		}else{
			goal.getBackground().setAlpha(100);
			clickedButtons.add(viewId);
			if (viewId == R.id.academics)
			{
				mClicked[1]=(byte) 1;
			}else if(viewId == R.id.social){
				mClicked[2]=(byte) 1;
			}else if(viewId == R.id.insomnia){
				mClicked[3]=(byte) 1;
			}else if(viewId == R.id.caffeine){
				mClicked[4]=(byte) 1;
			}else if(viewId == R.id.wellbeing){
				mClicked[5]=(byte) 1;
			}
		}
		
		
				
	}
			
	}


