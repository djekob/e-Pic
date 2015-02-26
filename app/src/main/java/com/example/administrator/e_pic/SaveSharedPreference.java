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

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(TAG_USERNAME, userName);
        editor.commit();
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