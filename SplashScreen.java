package com.example.khalil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN= 500;

    //variables
    Animation topAnim,bottomAnim;
    ImageView image;
    TextView logo,slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        getWindow ().setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView (R.layout.activity_main);





        //Animation
        topAnim = AnimationUtils.loadAnimation (this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation (this,R.anim.bottom_animation);


        //Hooks
        image = findViewById (R.id.imageView);
        logo = findViewById (R.id.textView);
        slogan = findViewById (R.id.textView2);


        image.setAnimation (topAnim);
        image.setAnimation (bottomAnim);
        slogan.setAnimation (bottomAnim);


        TimerTask toLoginTask = new TimerTask() {
            @Override
            public void run() {
                Intent GO = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(GO);
                finish ();
            }
        };
        Timer timer = new Timer();
        timer.schedule(toLoginTask,2000);
    }
}

