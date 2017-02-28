package com.thousandsunny.module_login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.thousandsunny.router.RouterList;
import com.steve.NameGenerate;

@NameGenerate
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_login);
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(LoginActivity.this, RouterList.getActivity("MineActivity"));
                LoginActivity.this.startActivity(intent);
            }
        });
    }
}
