package com.slightech.androidnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private NavigationBar navigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startService(new Intent(this, MyAccessibilityService.class));
        navigationBar = NavigationBar.getInstance();
        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
