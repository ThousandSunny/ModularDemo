package com.thousandsunny.mymodular;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.thousandsunny.router_annotation.annotation.annotaion.Route;
import com.thousandsunny.router.RouterList;

@Route(name = "LoginActivity", group = "app")
public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
    }

    private void assignViews() {
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this, RouterList.getActivity("LoginActivity"));
                MainActivity.this.startActivity(intent);
            }
        });
    }


}
