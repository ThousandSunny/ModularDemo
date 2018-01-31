package com.steve.pluginhost_sample;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by SteveYan on 2018/1/31.
 */

public class HostMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startApk();
            }
        });

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注意：使用运行时权限
                ActivityCompat.requestPermissions(HostMainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
            }
        });
    }

    public void startApk() {
        Intent intent = new Intent(this, ProxyActivity.class);
        String otherApkMainActivityName = PluginManager.getInstance().getPluginPackageArchiveInfo().activities[0].name;
        intent.putExtra("className", otherApkMainActivityName);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PluginManager.getInstance().setContext(this);
        PluginManager.getInstance().loadApk(Environment.getExternalStorageDirectory().getAbsolutePath() + "/plugin_sample-debug.apk");
    }

}
