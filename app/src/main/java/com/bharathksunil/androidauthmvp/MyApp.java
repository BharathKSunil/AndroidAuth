package com.bharathksunil.androidauthmvp;

import android.app.Application;

import com.bharathksunil.utils.Debug;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Debug.setAsRelease(!BuildConfig.DEBUG);
    }
}
