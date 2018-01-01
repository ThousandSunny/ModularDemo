package com.thousandsunny.module_login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.thousandsunny.router_annotation.annotation.annotaion.Route;

@Route(name = "LoginActivity")
public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}
