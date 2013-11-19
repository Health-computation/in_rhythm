package com.example.ds;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;


public class TimeAlarm extends BroadcastReceiver {
	NotificationManager nm;
	
	@Override
	public void onReceive(Context context, Intent intent) {
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
		NotificationCompat.Builder mBuilder =
		        new NotificationCompat.Builder(context)
		        .setSmallIcon(R.drawable.icon)
		        .setContentTitle("Goal Tracking")
		        .setAutoCancel(true)
		        .setContentText("Did you meet your Sleep goal?");
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager =
		    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(100, mBuilder.build());

	}

}
