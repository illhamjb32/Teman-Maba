package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ActivityTambahContact extends AppCompatActivity {
    //deklarasi
    private EditText edtFirst,edtSecond,edtNo,edtMail;
    private SharedPreferences sharedPrefs;
    private String first,second,no,id_contact,mail,email;
    private String savedUsername;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_contact);
        //pengambilan data
        edtFirst=(EditText) findViewById(R.id.edtFirst);
        edtSecond=(EditText) findViewById(R.id.edtSecond);
        edtNo=(EditText) findViewById(R.id.edtNo);
        edtMail=(EditText) findViewById(R.id.edtMail);
        dbHelper = new dbHelper(this); // dbHelper
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);//shared preferences
    }

    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public void tambahContact(View view) {
        savedUsername=savedUsername = this.sharedPrefs.getString("username", null);
        first = edtFirst.getText().toString();
        second = edtSecond.getText().toString();
        no=edtNo.getText().toString();
        mail=edtMail.getText().toString();
        email = savedUsername;

        if (first.isEmpty() || second.isEmpty() || no.isEmpty()|| mail.isEmpty()  ) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            long id = dbHelper.insertDataContact(first, second,no,mail,email);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Gagal Menambahkan Tugas!");

            } else {
                showLoadingIndicator();
                Message.message(getApplicationContext(), "Berhasil Menambahkan Tugas!");
                edtFirst.setText("");
                edtSecond.setText("");
                edtNo.setText("");
                edtMail.setText("");
                ActivityTambahContact.super.onBackPressed();
                finish();
            }
        }
    }

    public void back(View view) {
        ActivityTambahContact.super.onBackPressed();
        finish();
    }
}
