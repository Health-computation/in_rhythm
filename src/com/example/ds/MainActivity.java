package com.example.ds;
import com.parse.*;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import edu.cornell.SleepActivity;
import edu.cornell.SleepProbeWriter;

import edu.cornell.SleepActivity;
import edu.cornell.SleepProbeWriter;

public class MainActivity extends SleepActivity{

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
	private static final int USER_DATA = 1;
	private static final int BADGES = 2;
	
    private static final String CAMPAIGN_URN = "urn:campaign:moodrhythm:sleep";
    private static final String CAMPAIGN_CREATED = "2013-05-01 17:00:00";
    private static final String SURVEY_ID = "sleepDurationSurvey";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.d("LOL", "COOL STORY");
		
		 SleepProbeApplication2 app = (SleepProbeApplication2) getApplicationContext();

	        // Initialize the sleep dpu probe that will upload data to the system
	        mProbeWriter = new SleepProbeWriter(this, CAMPAIGN_URN, CAMPAIGN_CREATED, SURVEY_ID);
	        // Start up the probe
	        app.startSleepProbe();

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.activity_main);
		// Set up the action bar to show a dropdown list.
		//final ActionBar actionBar = getActionBar();
		//actionBar.setDisplayShowTitleEnabled(false);
		//actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		TextView score = (TextView) findViewById(R.id.score);
		TextView goal = (TextView) findViewById(R.id.goal);
		TextView competeText = (TextView) findViewById(R.id.competeText);
		DonutChartView donut = (DonutChartView) findViewById(R.id.donut);
		donut.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		    	launchChartPage();
		    }
		});
        DisplayMetrics metrics = new DisplayMetrics();
        //donut.setDensity(metrics.densityDpi);
		Typeface face = Typeface.createFromAsset(getAssets(),
		            "proximanova-bold-webfont.ttf");
		View setGoal = (View) findViewById(R.id.setGoal);
		View recentData = (View) findViewById(R.id.recentData);
		recentData.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				launchChartPage();
		    }
		});
		
		
		setGoal.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		    	launchGoalPage();
		    }
		});
		//goal.setOnClickListener(goalListener());
		
		score.setTypeface(face);
		goal.setTypeface(face);
		competeText.setTypeface(face);

		
		

		// Set up the dropdown list navigation in the action bar.
		/*actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				new ArrayAdapter<String>(getActionBarThemedContextCompat(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3), }), this);*/
		
	}
	
    public void launchDataPage(){
		Intent chartPage = new Intent(this, ChartActivity.class);
		startActivity(chartPage);		
    	
    }
	private View.OnClickListener goalListener(){
		View.OnClickListener mCorkyListener = new OnClickListener() {
			@Override
		    public void onClick(View v) {
				Log.d("LOL", "Well");
		    	launchChartPage();
		    }
		};
		return mCorkyListener;
	}
	
	private void launchChartPage(){
		Intent chartPage = new Intent(this, ChartActivity.class);
		startActivity(chartPage);		
	}
	
	private void launchGoalPage(){
		Intent goalPage = new Intent(this, PickGoalActivity.class);
		startActivity(goalPage);		
	}



}
