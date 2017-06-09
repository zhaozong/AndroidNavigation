package com.slightech.androidnavigation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Rokey on 2017/6/9.
 */

public class NavigationService extends Service {
    private NavigationBar navigationBar;
    @Override
    public void onCreate() {
        super.onCreate();
        navigationBar = NavigationBar.getInstance();
        startService(new Intent(this, MyAccessibilityService.class));
        Intent settingsIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        settingsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(settingsIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        if (navigationBar!=null){
            navigationBar.showNavigationBar();
        }else {
            navigationBar = NavigationBar.getInstance();
            navigationBar.showNavigationBar();
        }
    }

    @Override
    public void onDestroy() {
        if (navigationBar!=null){
            navigationBar.hideNavigationBar();
        }
        super.onDestroy();
    }
}
