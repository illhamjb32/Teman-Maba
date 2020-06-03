package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUpActivity extends AppCompatActivity {
    private EditText txtNama, txtEmail, txtPhone, txtAnswer, txtPassword, txtConfirmPassword;
    String email, password, nama, phone, alamat, jk, tanggal_lahir, pertanyaan, jawaban;
    private RadioGroup rgJenisKelamin;
    private RadioButton rbLaki, rbPerempuan;
    private Spinner spinnerPertanyaan;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtNama = (EditText) findViewById(R.id.edtNama);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtPhone = (EditText) findViewById(R.id.edtPhone);
        txtAnswer = (EditText) findViewById(R.id.edtAnswer);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        rgJenisKelamin = (RadioGroup) findViewById(R.id.rgJK);
        spinnerPertanyaan = (Spinner) findViewById(R.id.spinnerPertanyaan);
        rbLaki = (RadioButton) findViewById(R.id.rbLaki);
        rbPerempuan = (RadioButton) findViewById(R.id.rbPerempuan);
        dbHelper = new dbHelper(this);
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

    public void insert(View view) {
        nama = txtNama.getText().toString();
        email = txtEmail.getText().toString();
        phone = txtPhone.getText().toString();
        pertanyaan = spinnerPertanyaan.getSelectedItem().toString();
        jawaban = txtAnswer.getText().toString();
        password = md5(txtPassword.getText().toString());
        int idJk = rgJenisKelamin.getCheckedRadioButtonId();
        switch (idJk) {
            case R.id.rbLaki:
                jk = rbLaki.getText().toString();
                break;
            case R.id.rbPerempuan:
                jk = rbPerempuan.getText().toString();
                break;
        }
        if (nama.isEmpty() || email.isEmpty() || phone.isEmpty() || jawaban.isEmpty() || password.isEmpty()) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                Message.message(getApplicationContext(), "Please enter a Valid E-Mail Address!");
            } else {
                if (txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {

                    long id = dbHelper.insertData(email, password, nama, phone, jk, pertanyaan, jawaban);
                    if (id <= 0) {
                        Message.message(getApplicationContext(), "Register Unsuccessfull");
                        txtNama.setText("");
                        txtEmail.setText("");
                        txtPhone.setText("");
                        txtAnswer.setText("");
                        txtPassword.setText("");
                        txtConfirmPassword.setText("");
                    } else {
                        showLoadingIndicator();
                        Message.message(getApplicationContext(), "Register Successful");
                        txtNama.setText("");
                        txtEmail.setText("");
                        txtPhone.setText("");
                        txtAnswer.setText("");
                        txtPassword.setText("");
                        txtConfirmPassword.setText("");
                        Intent i = new Intent(SignUpActivity.this, WelcomeHome.class);
                        startActivity(i);
                        finish();
                    }
                }
                else{
                    Message.message(getApplicationContext(), "Password didnt match!");
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                }
                }
                }

        }

    public void back(View view) {
        SignUpActivity.super.onBackPressed();
        finish();
    }
}

