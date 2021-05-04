package com.purnendu.PocketNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.animation.Animator;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity  {


    private ImageView newsLogo;
    private TextView pocketNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Setting dark mode will work or not
        SharedPreferences sharedPreferences =getSharedPreferences("switch", Context.MODE_PRIVATE);
        boolean isNightMode = sharedPreferences.getBoolean("nightMode", false);
        if (isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            ChangeStatusBarColor(Color.BLACK);
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            ChangeStatusBarColor(Color.WHITE);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Objects.requireNonNull(getSupportActionBar()).hide(); //Hiding Original Action Bar

        //Setting up animation
        newsLogo=findViewById(R.id.newsLogo);
        pocketNews=findViewById(R.id.pocketNews);
        LottieAnimationView lottieAnimation = findViewById(R.id.lottieAnimation);
        Animation animation_down = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_down);
        Animation animation_up = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.move_up);

       //Starting animation
        newsLogo.startAnimation(animation_down);
        pocketNews.startAnimation(animation_up);
        lottieAnimation.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                FirstTimeTrendingLaunch firstTimeTrendingLaunch=new FirstTimeTrendingLaunch(SplashActivity.this);
                firstTimeTrendingLaunch.UpToDateDatabase();
                newsLogo.clearAnimation();
                pocketNews.clearAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }

    private   void ChangeStatusBarColor(int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }
}