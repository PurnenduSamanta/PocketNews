package com.purnendu.PocketNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //Setting dark mode will work or not
        SharedPreferences sharedPreferences =getSharedPreferences("switch", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("nightMode", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide(); //Hiding Original Action Bar

        //Phone will go full screen mode with no status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            final WindowInsetsController insetsController = getWindow().getInsetsController();
            if (insetsController != null) {
                insetsController.hide(WindowInsets.Type.statusBars());
            }
        } else {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            );
        }

        //Creating Animation
        ImageView newsLogo=findViewById(R.id.newsLogo);
        TextView pocketNews=findViewById(R.id.pocketNews);
        Animation animation_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_down);
        Animation animation_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_up);
        newsLogo.setAnimation(animation_down);
        pocketNews.setAnimation(animation_up);

        //Creating delay
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },3000);

    }
}