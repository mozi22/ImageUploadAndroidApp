package com.main.junaidstore.libraries;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.main.junaidstore.R;
import com.main.junaidstore.activities.MainActivity;

/**
 * Created by Muazzam on 4/16/2017.
 */

public class GeneralFunctions {
    public static String getSessionValue(Activity activity, String key){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return preferences.getString(key, "");
    }
    public static void setSessionValues(Activity activity, String userid,String access_token, String user_type){
        GeneralFunctions.addSessionValue(activity,activity.getResources().getString(R.string.access_token), access_token);
        GeneralFunctions.addSessionValue(activity,activity.getResources().getString(R.string.userid),userid);
        GeneralFunctions.addSessionValue(activity,activity.getResources().getString(R.string.usertype),user_type);
    }

    public static void addSessionValue(Activity activity,String key,String value){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }
}
