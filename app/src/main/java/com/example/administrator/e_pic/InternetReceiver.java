package com.example.administrator.e_pic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class InternetReceiver extends BroadcastReceiver {
    public InternetReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(RandomShit.haveNetworkConnection(context)){
            BigClass bigClass = BigClass.ReadData(context);
            if(bigClass!=null) {
                if(bigClass.getNotSendSneezes().size()>0){
                    while(RandomShit.haveNetworkConnection(context) && bigClass.getNotSendSneezes().size()!=0){
                        Sneeze s = bigClass.getNotSendSneezes().get(0);
                        s.setPostal(getPostal(context, s.getLatitude(), s.getLongitude()));
                        new Connections(context, s, Connections.CREATE_SNEEZE_CODE_FROM_RECEIVER);
                        bigClass.deleteFirst();
                        Log.i("broadcastreceiver", "add");
                    }
                }
                bigClass.writeData(context);
            }
        }
    }

    public int getPostal(Context context, double latitude, double longitude){
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch(IOException e) {
            e.printStackTrace();
        }
        Address address=null;
        int postcode=0;

        if (addresses != null && addresses.size() > 0) {

            for (int i = 0; i < addresses.size(); i++) {
                address = addresses.get(i);
                if (address.getPostalCode() != null) {
                    postcode = Integer.parseInt(address.getPostalCode());
                    //stad = address.getLocality();
                    //land = address.getAdminArea(); --> werkt niet
                    break;
                }

            }
        }
        return postcode;
    }
}
