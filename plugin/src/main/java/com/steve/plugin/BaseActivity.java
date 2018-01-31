package com.steve.plugin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by SteveYan on 2018/1/31.
 */

public class BaseActivity extends AppCompatActivity implements PluginInterface {

    //注意：这里命名为protected，以便于子类使用
    protected  Activity thisContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void setContentView(int layoutResID) {
        thisContext.setContentView(layoutResID);
    }

    @Override
    public void setContentView(View view) {
        thisContext.setContentView(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        thisContext.setContentView(view, params);
    }

    @Override
    public LayoutInflater getLayoutInflater() {
        return thisContext.getLayoutInflater();
    }

    @Override
    public Window getWindow() {
        return thisContext.getWindow();
    }

    @Override
    public View findViewById(int id) {
        return thisContext.findViewById(id);
    }

    @Override
    public void attachContext(Activity context) {
        thisContext = context;
    }

    @Override
    public ClassLoader getClassLoader() {
        return thisContext.getClassLoader();
    }

    @Override
    public WindowManager getWindowManager() {
        return thisContext.getWindowManager();
    }


    @Override
    public ApplicationInfo getApplicationInfo() {
        return thisContext.getApplicationInfo();
    }

    @Override
    public void finish() {
        thisContext.finish();
    }


    public void onStart() {

    }

    public void onResume() {

    }

    @Override
    public void onRestart() {

    }

    public void onPause() {

    }

    public void onStop() {

    }

    public void onDestroy() {

    }

    public void onSaveInstanceState(Bundle outState) {

    }

    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    public void onBackPressed() {
        thisContext.onBackPressed();
    }

    @Override
    public void startActivity(Intent intent) {
        thisContext.startActivity(intent);
    }


}
