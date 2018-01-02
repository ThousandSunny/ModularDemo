package com.thousandsunny.module_mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.thousandsunny.router_annotation.annotation.annotaion.Route;

/**
 * Created by Steve on 2017/2/27.
 */
@Route(group = "mine")
public class MineProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_profile);
    }
}
