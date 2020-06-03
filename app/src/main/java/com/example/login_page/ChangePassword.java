package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePassword extends AppCompatActivity {
    dbHelper dbHelper;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;
    ProgressDialog loadingIndicator;
    private EditText txtOldPass,txtNewPass,txtConfPass;
    private String savedUsername,oldPassword;
    protected Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        savedUsername = this.sharedPrefs.getString("username", null);
        String email = savedUsername;
        String nama;
        String[] emailargs = {email};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
        cursor.moveToNext();
        cursor.moveToPosition(0);
        oldPassword = cursor.getString(cursor.getColumnIndex("password"));
        txtNewPass=(EditText) findViewById(R.id.edtNewPassowrd);
        txtConfPass=(EditText) findViewById(R.id.edtConfirmPass);
        txtOldPass=(EditText) findViewById(R.id.edtOldPassword);
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
    public void changePassword(View view) {
        if(txtOldPass.getText().toString().isEmpty()||txtNewPass.getText().toString().isEmpty()||txtConfPass.getText().toString().isEmpty()){
            Message.message(this,"Silahkan Lengkapi Data!");
        }
        else{
            if(txtNewPass.getText().toString().equals(txtConfPass.getText().toString())){
                if(md5(txtOldPass.getText().toString()).equals(oldPassword)){
                    int a = dbHelper.updatePass(md5(txtNewPass.getText().toString()),savedUsername);
                    if (a <= 0) {
                        Message.message(getApplicationContext(), "Unsuccessful");
                        txtOldPass.setText("");
                        txtNewPass.setText("");
                        txtConfPass.setText("");
                    } else {
                        showLoadingIndicator();
                        Message.message(getApplicationContext(), "Successful Change Password!");
                        txtOldPass.setText("");
                        txtNewPass.setText("");
                        txtConfPass.setText("");
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.apply();
                        Intent i = new Intent(ChangePassword.this, SignInActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Message.message(this, "Password Lama Salah!");
                }
            }
            else{
                Message.message(this, "Password Tidak Cocok!");
            }
        }
    }


    public void back(View view) {
        ChangePassword.super.onBackPressed();
        finish();
    }
}
