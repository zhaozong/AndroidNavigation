package com.slightech.androidnavigation;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity {

    private float moveX = 0;
    private float moveY = 0;
    private static Thread navigationThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startService(new Intent(this, MyAccessibilityService.class));
        if (navigationThread == null) {
            navigationThread = new Thread() {
                private WindowManager.LayoutParams params;
                private WindowManager wm;

                @Override
                public void run() {
                    Looper.prepare();

                    //拿到windowManager,窗口机制
                    wm = (WindowManager) MyApplication.context.getSystemService(WINDOW_SERVICE);
                    //和activity上下文没关系。
                    final View view = View.inflate(MyApplication.context, R.layout.item, null);
                    params = new WindowManager.LayoutParams();
                    //最大的层级，可以显示在其他应用的上面
                    params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
                    //设置不拦截焦点,可以将window外边的事件传递下去
                    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                    //设置布局参数
                    params.width = (int) (getResources().getDisplayMetrics().density * 60);
                    params.height = (int) (getResources().getDisplayMetrics().density * 60);
                    //设置坐标位置
                    params.gravity = Gravity.LEFT | Gravity.TOP;
                    //设置 遮罩层透明
                    params.format = PixelFormat.TRANSPARENT;
                    params.y = wm.getDefaultDisplay().getHeight() / 2;
                    wm.addView(view, params);

                    //点击返回
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post(MyAccessibilityService.BACK);
                        }
                    });

                    //长按home
                    view.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            if (moveX < 6 && moveY < 6) {
                                EventBus.getDefault().post(MyAccessibilityService.HOME);
                            }
                            return true;
                        }
                    });

                    view.setOnTouchListener(new View.OnTouchListener() {
                        private float minY;
                        private float minX;
                        private float y;
                        private float x;
                        private float startX;
                        private float startY;

                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN:
                                    moveX = 0;
                                    moveY = 0;
                                    startX = event.getRawX();
                                    startY = event.getRawY();
                                    x = event.getRawX();
                                    y = event.getRawY();
                                    return false;
                                case MotionEvent.ACTION_MOVE:
                                    minX = (event.getRawX() - x);
                                    minY = (event.getRawY() - y);
                                    params.x += minX;
                                    params.y += minY;
                                    wm.updateViewLayout(view, params);
                                    x = event.getRawX();
                                    y = event.getRawY();
                                    moveX = Math.abs(startX - event.getRawX());
                                    moveY = Math.abs(startY - event.getRawY());
                                    break;
                                case MotionEvent.ACTION_UP:
                                    if (params.x < wm.getDefaultDisplay().getWidth() / 2 - params.width / 2) {
                                        params.x = 0;
                                    } else {
                                        params.x = wm.getDefaultDisplay().getWidth() - params.width;
                                    }
                                    wm.updateViewLayout(view, params);
                                    //如果移动小于6个像素，事件下放给onClick 处理
                                    if (moveX > 6 || moveY > 6) {
                                        return true;
                                    } else {
                                        return false;
                                    }
                            }
                            return true;
                        }
                    });

                    Looper.loop();
                }
            };
            navigationThread.start();
        }

        startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));

    }
}
