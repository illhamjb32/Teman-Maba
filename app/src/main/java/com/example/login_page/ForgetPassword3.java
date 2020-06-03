package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ForgetPassword3 extends AppCompatActivity {
    dbHelper dbHelper;
    private String savedUsername;
    ProgressDialog loadingIndicator;
    private String newPass,confPass;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;
    private Spinner spinPertanyaan;
    private EditText txtNewPass,txtConfPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password3);
        txtNewPass=(EditText) findViewById(R.id.edtNewPassowrd);
        txtConfPass=(EditText) findViewById(R.id.edtConfirmPass);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("email_detail", Context.MODE_PRIVATE);
        savedUsername = this.sharedPrefs.getString("email", null);
    }
    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
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
    public void changePasswordForget(View view) {
        if(txtNewPass.getText().toString().isEmpty()||txtConfPass.getText().toString().isEmpty()){
            Message.message(this,"Silahkan Lengkapi Data!");
        }
        else{
            if(txtNewPass.getText().toString().equals(txtConfPass.getText().toString())){

                    int a = dbHelper.updatePass(md5(txtNewPass.getText().toString()),savedUsername);
                    if (a <= 0) {
                        Message.message(getApplicationContext(), "Unsuccessful");
                        txtNewPass.setText("");
                        txtConfPass.setText("");
                    } else {
                        showLoadingIndicator();
                        Message.message(getApplicationContext(), "Successful Change Password!");
                        txtNewPass.setText("");
                        txtConfPass.setText("");
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.apply();
                        Intent i = new Intent(ForgetPassword3.this, SignInActivity.class);
                        startActivity(i);
                        finish();
                    }

            }
            else{
                Message.message(this, "Password Tidak Cocok!");
            }
        }
    }
}
