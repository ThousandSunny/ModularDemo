package com.steve.hook_lib;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.lang.reflect.Field;

/**
 * Created by SteveYan on 2018/1/30.
 */

public class ActivityThreadHandlerCallback implements Handler.Callback {

    private Handler mHandler;

    public ActivityThreadHandlerCallback(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public boolean handleMessage(Message message) {
        int what = message.what;
        switch (what) {
            case 100: //这里对应的是LAUNCH_ACTIVITY
                handleStartActivity(message);
                break;
        }
        mHandler.handleMessage(message);
        return true;
    }

    private void handleStartActivity(Message message) {
        Object object = message.obj;

        try {
            Field intent = object.getClass().getDeclaredField("intent");
            if (intent == null) {
                return;
            }
            intent.setAccessible(true);
            Intent rawIntent = (Intent) intent.get(object);

            Intent targetIntent = rawIntent.getParcelableExtra("extra_target_intent");
            if (targetIntent == null) {
                return;
            }
            rawIntent.setComponent(targetIntent.getComponent());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}