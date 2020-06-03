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
public class EditContact extends AppCompatActivity {
    private EditText edtFirst,edtSecond,edtNo,edtMail;
    private SharedPreferences sharedPrefs;
    private String first,second,no,idContact,mail,email;
    private String savedUsername;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        edtFirst=(EditText) findViewById(R.id.edtFirst);
        edtSecond=(EditText) findViewById(R.id.edtSecond);
        edtNo=(EditText) findViewById(R.id.edtNo);
        edtMail=(EditText) findViewById(R.id.edtMail);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        getData();
    }

    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getId = getIntent().getExtras().getString("id");
        final String getFirst = getIntent().getExtras().getString("first");
        final String getSecond = getIntent().getExtras().getString("second");
        final String getNo = getIntent().getExtras().getString("no");
        final String getMail = getIntent().getExtras().getString("mail");
        edtFirst.setText(getFirst);
        edtSecond.setText(getSecond);
        edtNo.setText(getNo);
        edtMail.setText(getMail);
        idContact=getId;
    }
    public void editContact(View view) {
        first = edtFirst.getText().toString();
        second = edtSecond.getText().toString();
        no=edtNo.getText().toString();
        mail= edtMail.getText().toString();

        if (first.isEmpty() || second.isEmpty() || no.isEmpty() || mail.isEmpty()  ) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            int a = dbHelper.updateContact(idContact,first,second,no,mail);
            if (a <= 0) {
                Message.message(getApplicationContext(), "Unsuccessful");
            } else {
                Message.message(getApplicationContext(), "Berhail Mengubah Contact!");
                showLoadingIndicator();
                edtFirst.setText("");
                edtSecond.setText("");
                edtNo.setText("");
                edtMail.setText("");
                EditContact.super.onBackPressed();
                finish();
            }
        }
    }

    public void back(View view) {
        EditContact.super.onBackPressed();
        finish();
    }
}


