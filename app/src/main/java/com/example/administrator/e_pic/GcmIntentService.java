
/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.e_pic;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    public GcmIntentService() {
        super("GcmIntentService");
    }
    public static final String TAG = "GCM Demo";
    private static final String TAG_RECEIVED_FROM = "From";
    private static final String TAG_NOTIFICATION_TYPE = "NotificationType";
    private static final int CODE_ADD_SNEEZE = 0;
    private static final int CODE_FRIEND_REQUEST = 1;
    private static final int CODE_OTHER_LOGIN = 2;
    private static final int CODE_ACCEPT_FRIENDS = 3;

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        String from = extras.getString(TAG_RECEIVED_FROM);
        int notType = Integer.parseInt(extras.getString(TAG_NOTIFICATION_TYPE));
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
            /*
             * Filter messages based on message type. Since it is likely that GCM will be
             * extended in the future with new message types, just ignore any message types you're
             * not interested in, or that you don't recognize.
             */
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                //sendNotification("Send error: " + extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                //sendNotification("Deleted messages on server: " + extras.toString());
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                /*for (int i = 0; i < 5; i++) {
                    Log.i(TAG, "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }*/
                Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(from, notType);
                Log.i(TAG, "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String from, int notificationType) {
        int requestID = (int) System.currentTimeMillis();



        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);


        Intent notificationIntent = null;

        long[] vibrate = {130, 100, 130, 100};
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.sneeze_icon)
                        .setContentTitle("iSneeze");
        switch (notificationType){
            case CODE_ADD_SNEEZE:
                mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(from + " heeft geniesd."))
                        .setContentText(from + " heeft geniesd.")
                        .setTicker(from + " heeft geniesd.");
                notificationIntent = new Intent(getApplicationContext(), iSneezeActivity.class);

                //contentIntent = PendingIntent.getActivity(this, 0,
                  //      new Intent(this.getApplicationContext(), iSneezeActivity.class), 0);

                break;
            case CODE_ACCEPT_FRIENDS:
                mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(from + " heeft je vriendschapsverzoek aanvaard."))
                        .setContentText(from + " heeft je vriendschapsverzoek aanvaard.")
                        .setTicker(from + " heeft je vriendschapsverzoek aanvaard.");
                notificationIntent = new Intent(getApplicationContext(), MyFriendsActivity.class);

                //contentIntent = PendingIntent.getActivity(this, 0,
                //      new Intent(this.getApplicationContext(), iSneezeActivity.class), 0);

                break;
            case CODE_FRIEND_REQUEST:
                mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(from + " heeft je een vriendschapsverzoek gestuurd."))
                        .setContentText(from + " heeft je een vriendschapsverzoek gestuurd.")
                        .setTicker(from + " heeft je een vriendschapsverzoek gestuurd.");
                SaveSharedPreference.setNrOfSneezes(getApplicationContext(), SaveSharedPreference.getNrOfSneezes(getApplicationContext()) + 1);
                notificationIntent = new Intent(getApplicationContext(), FriendRequestsActivity.class);

                //contentIntent = PendingIntent.getActivity(this, 0,
                  //      new Intent(this.getApplicationContext(), iSneezeActivity.class), 0);
                break;
            case CODE_OTHER_LOGIN:
                SaveSharedPreference.clear(this);
                if(RandomShit.isAppOnForeground(this)) {
                    Intent i = new Intent(getApplicationContext(), RealMainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            default: return;
        }
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        mBuilder.setAutoCancel(true);



        mBuilder.setContentIntent(contentIntent);
        mBuilder.setVibrate(vibrate);


        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


}