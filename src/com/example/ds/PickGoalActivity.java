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
//import android.widget.TimePicker;

public class PickGoalActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Typeface face = Typeface.createFromAsset(getAssets(),
	            "proximanova-bold-webfont.ttf");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.pick_goal);
		
		TextView WakeEarlier =(TextView) findViewById(R.id.WakeEarlier);
		TextView SleepEarlier=(TextView) findViewById(R.id.SleepEarlier);
		TextView SleepMore =(TextView) findViewById(R.id.SleepMore);
		TextView LabelView =(TextView) findViewById(R.id.LabelView);
		
		WakeEarlier.setTypeface(face);
		SleepEarlier.setTypeface(face);
		SleepMore.setTypeface(face);
	
		/*WakeEarlier.setOnClickListener(new View.OnClickListener() {
			public void OnClick(View v)
			{
				Intent goalPage = new Intent(getApplicationContext(), GoalActivity.class);
				startActivity(goalPage);
			}

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		*/
		
		}
		
	public void onClick(View v)
	{
		int viewId = ((TextView) v).getId();
		Intent goalPage = new Intent(getApplicationContext(), GoalActivity.class);
		TextView clickedView = (TextView) findViewById(viewId);
		startActivity(goalPage);
		
		
	}
			

		
	}

	


