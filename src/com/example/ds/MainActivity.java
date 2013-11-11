package com.example.ds;
import java.io.File;
import java.nio.charset.Charset;
import java.util.Calendar;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;

import com.parse.*;
import com.parse.entity.mime.HttpMultipartMode;
import com.parse.entity.mime.MultipartEntity;
import com.parse.entity.mime.content.FileBody;
import com.parse.entity.mime.content.StringBody;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.ListFragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
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
	        // Start up the probe

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
		//new PostDataTask().execute();
		
		Calendar cal = Calendar.getInstance();
		// add 5 minutes to the calendar object
		cal.add(Calendar.MINUTE, 1);
		Log.d("TEST", "Configuring notification");
		Context context = getApplicationContext();
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.icon)
		        .setContentTitle("Goal Tracking")
		        .setAutoCancel(true)
		        .setContentText("Did you meet your Sleep goal?");
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		Intent resultIntent = new Intent(context, GoalSurveyActivity.class);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(GoalSurveyActivity.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(100, mBuilder.build());
		/*
		Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
		intent.putExtra("alarm_message", "O'Doyle Rules!");
		// In reality, you would want to have a static variable for the request code instead of 192837
		PendingIntent sender = PendingIntent.getBroadcast(this, 192837, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the AlarmManager service
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), sender);
		*/
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
		View badges=(View)findViewById(R.id.competeText);
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
		
		badges.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
		    	launchBadgePage();
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
	
	
	 private class PostDataTask extends AsyncTask<Void, Void, HttpResponse> {
	     protected HttpResponse doInBackground(Void... voids) {
	    	String dataLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "edu.cornell.cs.pac.sensiphone/default/backup/ScreenProbe.csv";
	        HttpParams params = new BasicHttpParams();
	        params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
 			HttpClient httpclient = new DefaultHttpClient(params);
	    	HttpPost httppost = new HttpPost("http://ec2-50-17-54-246.compute-1.amazonaws.com/upload_data");
	 		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	 		HttpResponse response = null;
	 		try{
	 			Log.d("RESPONSE", dataLocation);
	 			Log.d("RESPONSE", "GOING");
	 			entity.addPart("title", new StringBody("title"));
	 			File myFile = new File(dataLocation);
	 			Log.d("RESPONSE", Boolean.toString(myFile.canRead()));
	 			//FileBody fileBody = new FileBody(myFile);

	 			entity.addPart("file",  new FileBody(myFile));
	 			httppost.setEntity(entity);
	
	 			Log.d("RESPONSE", "STILL");
	 			response = httpclient.execute(httppost);

	 			Log.d("RESPONSE", "Response!");
	 		}catch(Exception e){
	 			Log.d("SCRIPT", e.toString());
	 		}
	 		return response;
	     }


	     protected void onPostExecute(HttpResponse result) {
	         if(result != null){
	        	 Log.d("RESPONSE", Integer.toString(result.getStatusLine().getStatusCode()));
	         }
	     }
	 }
	 
	 public void launchBadgePage(){
			Intent badgePage = new Intent(this, BadgesActivity.class);
			startActivity(badgePage);	
			
			
		}
	 



}
