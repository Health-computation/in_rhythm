package com.example.ds;

import edu.cornell.SleepProbeApplication;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;

public class SleepProbeApplication2 extends SleepProbeApplication {
    
    public SleepProbeApplication2()
    {
    }
    
    public void onCreate() { 
    	super.onCreate();
    	ParseObject.registerSubclass(Badge.class);
        Parse.initialize(this, "tkfo8uILpr4eYocIPKQL7mrCZ0RjjtMYFEMVRCF8", "GsMQ2yZ28be5i7nyYI5XzFPBJ6bt7Kabi4ycdiPh"); 
		ParseFacebookUtils.initialize(getString(R.string.app_id));
    }

}
