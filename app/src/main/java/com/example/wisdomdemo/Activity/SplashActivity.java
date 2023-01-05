package com.example.wisdomdemo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.example.wisdomdemo.Utils.Constant;
import com.example.wisdomdemo.R;
import com.example.wisdomdemo.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 5000;
    public ActivitySplashBinding activitySplashBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        activitySplashBinding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(activitySplashBinding.getRoot());


        Glide.with(this)
                .load(Constant.Splash_URL)
                .error(R.drawable.product_placeholder)
                .placeholder(R.drawable.product_placeholder)
                .into(activitySplashBinding.splashimg);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}