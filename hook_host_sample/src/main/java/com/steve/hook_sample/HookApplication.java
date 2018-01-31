package com.steve.hook_sample;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 参考文章： http://pingguohe.net/2017/12/25/android-plugin-practice-part-1.html
 * <p>
 * Created by SteveYan on 2018/1/30.
 */

public class HookApplication extends Application {
    private String apk_name = "hook_plugin_sample-debug.apk";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        HookHelper.loadApk(base, Environment.getExternalStorageDirectory().getPath() + File.separator + apk_name);
        HookHelper.hookActivityManagerService(getClassLoader());
        HookHelper.hookActivityThreadHandler();
    }

}
