package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
private ImageView teman,maba;
private Animation animTeman,animMaba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        teman=(ImageView) findViewById(R.id.teman);
        maba=(ImageView) findViewById(R.id.maba);
        animTeman= AnimationUtils.loadAnimation(this,R.anim.teman_anim);
        teman.setAnimation(animTeman);
        animMaba= AnimationUtils.loadAnimation(this,R.anim.maba_anim);
        maba.setAnimation(animMaba);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, WelcomeSlideInfo.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
