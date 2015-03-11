package com.example.administrator.e_pic;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class SneezesInBuurtIntentService extends IntentService {

    public static final String TAG_LATITUDE = "Latitude";
    public static final String TAG_LONGITUDE = "Longitude";
    String time = null;

    public SneezesInBuurtIntentService() {
        super("SneezesInBuurtIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            while(true) {
                if (time == null || RandomShit.halfHourPassed(time)) {
                    if(RandomShit.haveNetworkConnection(getApplicationContext())){
                        time = RandomShit.getTimestamp();
                        double latitude = intent.getDoubleExtra(TAG_LATITUDE, 0);
                        double longitude = intent.getDoubleExtra(TAG_LONGITUDE, 0);
                        new Connections(getApplicationContext(), latitude, longitude, Connections.GET_ALL_SNEEZES_IN_BUURT_CODE);
                        Log.i("intentservice", "werkt");
                    }
                    else{
                        try {
                            wait(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        wait(60000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
