package com.slightech.androidnavigation;

import android.app.Application;
import android.content.Context;

/**
 * Created by Rokey on 2017/5/8.
 */

public class MyApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
}
