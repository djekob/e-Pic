package com.example.administrator.e_pic;

import android.app.ProgressDialog;
import android.content.Context;
import android.preference.PreferenceGroup;

/**
 * Created by Administrator on 21/02/2015.
 */
public class RandomShit {
    public static ProgressDialog progress;

    public static ProgressDialog getProgressDialog(Context context) {
        progress = new ProgressDialog(context, R.style.MyTheme);
        progress.setCancelable(false);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);

        return progress;
    }
}
