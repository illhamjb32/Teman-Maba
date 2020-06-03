package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ForgetPassword1 extends AppCompatActivity {
    private EditText txtEmail;
    dbHelper dbHelper;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password1);
        this.sharedPrefs = this.getSharedPreferences("email_detail", Context.MODE_PRIVATE);
        sharedPrefs.contains("email");
        dbHelper = new dbHelper(this);
        txtEmail=(EditText) findViewById(R.id.edtCheckEmail);
    }
    private void saveUsername()
    {
        // Menyimpan username bila diperlukan

        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString("email", this.txtEmail.getText().toString());
        editor.apply();
    }
    public void clickQuestion(View view) {
        boolean isExist = dbHelper.checkEmailExist(txtEmail.getText().toString());
        if (isExist) {
            saveUsername();
            Intent i = new Intent(ForgetPassword1.this, ForgetPassword2.class);
            startActivity(i);
            finish();
        } else {
            Message.message(ForgetPassword1.this, "Email Tidak Terdaftar!");
        }
    }

    public void back(View view) {
            ForgetPassword1.super.onBackPressed();
            finish();

    }
}
