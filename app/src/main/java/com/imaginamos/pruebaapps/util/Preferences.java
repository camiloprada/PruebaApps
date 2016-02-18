package com.imaginamos.pruebaapps.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kmilopr on 15/02/2016.
 */
public class Preferences {

    Activity activity;

    public Preferences(Activity activity){
        this.activity = activity;
        SharedPreferences sharedPref = activity.getSharedPreferences("firstrun", Context.MODE_PRIVATE);
    }

    public void writeFirtsRunPreference(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isFirtsRun", false);
        editor.commit();
    }

    public Boolean readFirtsRunPreference(){
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        Boolean defaultValue = true;
        Boolean isFirtsRun = sharedPref.getBoolean("isFirtsRun", defaultValue);
        return isFirtsRun;
    }
}
