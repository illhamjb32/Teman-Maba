package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class SignInActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    dbHelper dbHelper;
    private CheckBox chkShow;
    ProgressDialog loadingIndicator;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        dbHelper = new dbHelper(this);
        chkShow = (CheckBox) findViewById(R.id.chkpass);
        // Inisialisasi SharedPreferences
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        sharedPrefs.contains("username");
    }

    private void showLoadingIndicator() {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Waiting Sign In...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void formSignUp(View view) {
        Intent i = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(i);
    }

    public void processSignIn(View view) {
        try {
            boolean isExist = dbHelper.checkUserExist(txtEmail.getText().toString(), md5(txtPassword.getText().toString()));
            if (isExist) {
                showLoadingIndicator();
                saveUsername();
                Intent i = new Intent(SignInActivity.this, DashboardApp.class);
                finish();
                startActivity(i);
            } else {
                txtPassword.setText(null);
                Message.message(SignInActivity.this, "Login failed. Invalid username or password.");
            }
        } catch (Exception e) {
            Message.message(SignInActivity.this, " " + e);
        }

    }

    private void saveUsername() {
        // Menyimpan username bila diperlukan

        SharedPreferences.Editor editor = this.sharedPrefs.edit();
        editor.putString("username", this.txtEmail.getText().toString());
        editor.apply();
    }

    public void forgetPassword(View view) {
        Intent i = new Intent(SignInActivity.this, ForgetPassword1.class);
        startActivity(i);
    }

    public void back(View view) {
        SignInActivity.super.onBackPressed();
        finish();
    }

    public void showpass(View view) {
        if (chkShow.isChecked()) {
            //show
            txtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            // hide password
            txtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}
