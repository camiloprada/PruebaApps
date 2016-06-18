package com.imaginamos.pruebaapps.model;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.localytics.android.Localytics;


public class PruebaAppsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Localytics.autoIntegrate(this);
        Fresco.initialize(this);
    }

}