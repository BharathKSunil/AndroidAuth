package com.bharathksunil.androidauthmvp;

import android.app.Application;

import timber.log.Timber;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
    }
}
