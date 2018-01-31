package com.steve.plugin;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by SteveYan on 2018/1/31.
 */

public interface PluginInterface {

    void onCreate(Bundle saveInstance);

    void attachContext(Activity context);

    void onStart();

    void onResume();

    void onRestart();

    void onDestroy();

    void onStop();

    void onPause();
}
