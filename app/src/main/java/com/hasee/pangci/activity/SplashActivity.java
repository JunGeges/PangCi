package com.hasee.pangci.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasee.pangci.R;

public class SplashActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTextView;
    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
