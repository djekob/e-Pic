package com.example.administrator.e_pic;

import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 21/02/2015.
 */
public class RandomShit {
    public static ProgressDialog progress;

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final String timeFormat = "yyyy-MM-dd kk:mm:ss";

    public static ProgressDialog getProgressDialog(Context context) {
        progress = new ProgressDialog(context, R.style.MyTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        return progress;
    }






    public static boolean haveNetworkConnection(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public static String getTimestamp(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
        System.out.println(sdf.format(c.getTime()));
        String s =  sdf.format(c.getTime());
        if(s.contains(" 24:")) s = s.replace(" 24:", " 00:");
        return s;
    }

    public static boolean halfHourPassed(String tijd){
        Timestamp timestamp = Timestamp.valueOf(tijd);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());
        Calendar calendar1 = Calendar.getInstance();
        if(calendar1.getTimeInMillis()-calendar.getTimeInMillis()>30*60*1000){
            return true;
        }
        return false;
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null) {
            return false;
        }
        final String packageName = context.getPackageName();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.processName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static ArrayList<Float> generateShadeColors(int amount, int start,int end){
        if(amount!=0 && end>start) {
            float step = (end-start)/amount;
            ArrayList<Float> list = new ArrayList<>();
            for (int i = 0; i < amount; i++) {
                float c = end-i*step;
                list.add(c);
            }
            return list;
        }
        return null;
    }

    /*public static String RGBtoHex(int R,int G,int B) {
        return "#" + toHex(R)+toHex(G)+toHex(B);
    }

    public static String toHex(int N) {
        if (N==0) return "00";
        N=Math.max(0,N);
        N=Math.min(N,255);
        N=Math.round(N);
        return "0123456789ABCDEF".charAt((N-N%16)/16) +  "0123456789ABCDEF".charAt(N%16) + "";
    }*/
}
