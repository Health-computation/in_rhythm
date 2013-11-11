package com.example.ds;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;
import android.os.Bundle;
 

public class AlarmReceiver extends BroadcastReceiver {
	
	 @Override
	 public void onReceive(Context context, Intent intent) {
	   try {
	     Log.d("NOTIFY", "NOTIFYING");
	     Toast.makeText(context, "ARGHHHHH", Toast.LENGTH_SHORT).show();
	 	
			NotificationCompat.Builder mBuilder =
			        new NotificationCompat.Builder(context)
			        .setSmallIcon(R.drawable.icon)
			        .setContentTitle("My notification")
			        .setContentText("Hello World!");
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
			Intent resultIntent = new Intent(context, MainActivity.class);
			// Adds the back stack for the Intent (but not the Intent itself)
			stackBuilder.addParentStack(MainActivity.class);
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
			mNotificationManager.notify(1000, mBuilder.build());
	    } catch (Exception e) {
	     Toast.makeText(context, "There was an error somewhere, but we still received an alarm", Toast.LENGTH_SHORT).show();
	     e.printStackTrace();
	 
	    }
	 }

}
