
package com.example.ds;

import edu.cornell.SleepActivity;
import edu.cornell.SleepProbeWriter;

import android.os.Bundle;

public class SleepProbeActivity extends SleepActivity {

    /**
     * Tags needed for survey responses for a particular campaign to be uploaded to the system.
     * 
     * @see <a
     *      href="https://github.com/cens/ohmageServer/wiki/Survey-Manipulation">https://github.com/cens/ohmageServer/wiki/Survey-Manipulation</a>
     */
    private static final String CAMPAIGN_URN = "urn:campaign:moodrhythm:sleep";
    private static final String CAMPAIGN_CREATED = "2013-05-01 17:00:00";
    private static final String SURVEY_ID = "sleepDurationSurvey";
    
    /**
     * The session manager class is used to store charging duration, lock duration, off duration, and
     * dark duration.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SleepProbeApplication2 app = (SleepProbeApplication2) getApplicationContext();

        // Initialize the sleep dpu probe that will upload data to the system
        mProbeWriter = new SleepProbeWriter(this, CAMPAIGN_URN, CAMPAIGN_CREATED, SURVEY_ID);

        // Start up the probe
        //app.startSleepProbe();
        
        // End the probe
        //app.stopSleepProbe();
    }
}
