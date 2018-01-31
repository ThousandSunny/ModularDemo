package com.steve.hook_sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {
    String TAG = "ClassLoaderTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });
    }

    private void start() {
        Intent intent = new Intent();
        intent.setPackage("com.steve.hook_plugin_sample");
        intent.setClassName(this, "com.steve.hook_plugin_sample.MainActivity");
        startActivity(intent);
    }
}
