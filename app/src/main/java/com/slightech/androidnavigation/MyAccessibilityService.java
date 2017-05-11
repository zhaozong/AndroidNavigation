package com.slightech.androidnavigation;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;

/**
 * Created by Rokey on 2017/5/8.
 */

public class MyAccessibilityService extends AccessibilityService {
    public static final int BACK = 1;
    public static final int HOME = 2;
    private static final String TAG = "ICE";

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) { }

    @Override
    public void onInterrupt() {}

    @Subscribe
    public void onReceive(Integer action){
        switch (action){
            case BACK:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK);
                break;
            case HOME:
                performGlobalAction(AccessibilityService.GLOBAL_ACTION_HOME);
                break;
        }
    }

}
