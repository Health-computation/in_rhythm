package com.example.ds;

import edu.cornell.SleepProbeApplication;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.python27.ScriptApplication;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class SleepProbeApplication2 extends ScriptApplication {
	

    private static Context context;
    public static final String PREFS_NAME = "MyPrefsFile";
    
    public SleepProbeApplication2()
    {
    }
    
    public void onCreate() { 
    	super.onCreate();
    	ParseObject.registerSubclass(Badge.class);
        Parse.initialize(this, "tkfo8uILpr4eYocIPKQL7mrCZ0RjjtMYFEMVRCF8", "GsMQ2yZ28be5i7nyYI5XzFPBJ6bt7Kabi4ycdiPh"); 
		//ParseFacebookUtils.initialize(getString(R.string.app_id));
		SleepProbeApplication2.context = getApplicationContext();
	      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	      SharedPreferences.Editor editor = settings.edit();
	      editor.putString("currentGoal", null);
	      editor.commit();
    }
    
    public static Context getAppContext() {
        return SleepProbeApplication2.context;
    }

}
