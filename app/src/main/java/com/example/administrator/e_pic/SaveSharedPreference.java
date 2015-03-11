package com.example.administrator.e_pic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 21/02/2015.
 */
public class SaveSharedPreference
{
    public static final String TAG_USERNAME= "username";
    public static final String TAG_FIRSTNAME = "firstname";
    public static final String TAG_NAME= "name";
    public static final String TAG_REG_ID = Connections.GCMRegister.PROPERTY_REG_ID;
    public static final String TAG_NR_OF_SNEEZES = "nrOfSneezes";
    public static final String TAG_LATITUDE = "latitude";
    public static final String TAG_LONGITUDE = "longitude";
    public static final String TAG_POSTCODE = "postcode";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPostcode(Context context, int postcode) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(TAG_POSTCODE, postcode);
        editor.commit();
    }

    public static int getPostcode(Context context) {
        return getSharedPreferences(context).getInt(TAG_POSTCODE, -1);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TAG_USERNAME, userName);
        editor.commit();
    }

    public static void setLatitude(Context ctx, double latitude) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TAG_LATITUDE, latitude +"");
        editor.commit();
    }

    public static double getLatitude(Context ctx) {
        return Double.valueOf(getSharedPreferences(ctx).getString(TAG_LATITUDE, "-1"));
    }

    public static void setLongitude(Context ctx, double longitude) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TAG_LONGITUDE, longitude +"");
        editor.commit();
    }

    public static double getLongitude(Context ctx) {
        return Double.valueOf(getSharedPreferences(ctx).getString(TAG_LONGITUDE, "-1"));
    }

    public static void addSneeze(Context ctx)
    {
        setNrOfSneezes(ctx, getNrOfSneezes(ctx));
    }

    public static void setNrOfSneezes(Context ctx, int nr)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(TAG_NR_OF_SNEEZES, nr);
        editor.commit();
    }

    public static int getNrOfSneezes(Context context) {
        return getSharedPreferences(context).getInt(TAG_NR_OF_SNEEZES, -1);
    }
    public static void setRegid(Context context, String regid) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TAG_REG_ID, regid);
        editor.commit();
    }
    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(TAG_USERNAME, "");
    }
    public static String getRegid(Context context) {
        return getSharedPreferences(context).getString(TAG_REG_ID, "");
    }

    public static void setName(Context context, String name) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TAG_NAME, name);
        editor.commit();
    }
    public static void setFirstName(Context context, String firstname) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(TAG_FIRSTNAME, firstname);
        editor.commit();
    }

    public static String getFirstname(Context ctx) {
        return getSharedPreferences(ctx).getString(TAG_FIRSTNAME, "");
    }

    public static String getName(Context ctx) {
        return getSharedPreferences(ctx).getString(TAG_NAME, "");
    }

    public static void clear(Context context) {
        SharedPreferences.Editor editor= getSharedPreferences(context).edit();
        editor.putString(TAG_FIRSTNAME, "");
        editor.putString(TAG_NAME, "");
        editor.putString(TAG_USERNAME, "");
        editor.putInt(TAG_NR_OF_SNEEZES, -1);
        editor.putString(TAG_LATITUDE, "");
        editor.putString(TAG_LONGITUDE, "");
        editor.commit();
    }

    public static User getUser(Context context){
        return new User(getUserName(context), getFirstname(context), getName(context));
    }
    /*public static void setId(Context ctx, int id) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(TAG_ID, id);
        editor.commit();
    }

    public static int getId(Context ctx) {
        return getSharedPreferences(ctx).getInt(TAG_ID, -1);
    }*/

}