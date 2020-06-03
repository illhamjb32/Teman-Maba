package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityAbout extends AppCompatActivity {
    //Deklarasi Variabel Yang Dibutuhkan
private TextView text1,text2,text3,text4,text5,text6;
private ImageView pp;
private Animation anText1,anText2,anText3,anText4,anText5,anText6,anPP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        //pengambilan variabel pada layout
        text1=(TextView) findViewById(R.id.textView7);
        text2=(TextView) findViewById(R.id.textView8);
        text3=(TextView) findViewById(R.id.textView9);
        text4=(TextView) findViewById(R.id.textView10);
        text5=(TextView) findViewById(R.id.textView11);
        text6=(TextView) findViewById(R.id.textView12);
        pp=(ImageView) findViewById(R.id.imageView5);
        //set animasi pasa masing-masing object
        anText1= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text1.setAnimation(anText1);
        anText2= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text2.setAnimation(anText2);
        anText3= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text3.setAnimation(anText3);
        anText4= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text4.setAnimation(anText4);
        anText5= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text5.setAnimation(anText5);
        anText6= AnimationUtils.loadAnimation(this,R.anim.about_anim);
        text6.setAnimation(anText6);
        anPP =AnimationUtils.loadAnimation(this,R.anim.about_anim);
        pp.setAnimation(anPP);
    }

    public void back(View view) {
        //tombol back
        ActivityAbout.super.onBackPressed();
        finish();
    }
}
