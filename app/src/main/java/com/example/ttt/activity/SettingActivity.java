package com.example.ttt.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.ttt.R;

/**
 * Created by 1 on 2015/10/3.
 */
public class SettingActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        setTitle("设置");
    }
}
