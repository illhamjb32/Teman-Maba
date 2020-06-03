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

public class ActivityTugas extends AppCompatActivity {
    private EditText edtJudul,edtDeadline,edtDeskripsi;
    private SharedPreferences sharedPrefs;
    private String judul,deadline,deskripsi,id_tugas,email;
    private String savedUsername;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tugas);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        edtJudul=(EditText) findViewById(R.id.edtJudulTugas);
        edtDeadline=(EditText) findViewById(R.id.edtDeadline);
        edtDeskripsi=(EditText) findViewById(R.id.edtDeskripsi);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                edtDeadline.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public void tambahTugas(View view) {
        savedUsername=savedUsername = this.sharedPrefs.getString("username", null);
        judul = edtJudul.getText().toString();
        deadline = edtDeadline.getText().toString();
        deskripsi=edtDeskripsi.getText().toString();
        email = savedUsername;

        if (judul.isEmpty() || deadline.isEmpty() || deskripsi.isEmpty()  ) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            long id = dbHelper.insertDataTugas(judul, deadline,deskripsi,email);
            if (id <= 0) {
                Message.message(getApplicationContext(), "Gagal Menambahkan Tugas!");

            } else {
                showLoadingIndicator();
                Message.message(getApplicationContext(), "Berhasil Menambahkan Tugas!");
                edtJudul.setText("");
                edtDeadline.setText("");
                edtDeskripsi.setText("");
                ActivityTugas.super.onBackPressed();
                finish();
            }
        }

    }

    public void datePick(View view) {
        showDateDialog();
    }

    public void back(View view) {
        ActivityTugas.super.onBackPressed();
        finish();
    }
}
