package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_home);
    }
    public void formSignIn(View view) {
        Intent i = new Intent(WelcomeHome.this,SignInActivity.class);
        startActivity(i);
    }
    public void formSignUp(View view) {
        Intent i = new Intent(WelcomeHome.this,SignUpActivity.class);
        startActivity(i);
    }
}