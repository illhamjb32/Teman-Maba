package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityTambahMatkul extends AppCompatActivity {
private EditText edtJam1,edtJam2,edtMatkul,edtDosen,edtRuang;
        private SharedPreferences sharedPrefs;
        private String jam1,jam2,matkul,dosen,ruang,email,hari;
    private String savedUsername;
        dbHelper dbHelper;
        ProgressDialog loadingIndicator;
Spinner shari;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_matkul);
        edtJam1=(EditText) findViewById(R.id.edtJam1);
        edtJam2=(EditText) findViewById(R.id.edtJam2);
        edtMatkul=(EditText) findViewById(R.id.edtMatkul);
        edtDosen=(EditText)findViewById(R.id.edtDosen);
        edtRuang=(EditText) findViewById(R.id.edtRuang);
        shari=(Spinner) findViewById(R.id.spinnerHari);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
    }

    public void pilihjam(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ActivityTambahMatkul.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtJam1.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    public void pilihjam2(View view) {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(ActivityTambahMatkul.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtJam2.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }


    public void tambahMatkul(View view) {
        savedUsername=this.sharedPrefs.getString("username", null);
        matkul = edtMatkul.getText().toString();
        hari = shari.getSelectedItem().toString();
        jam1=edtJam1.getText().toString();
        jam2= edtJam2.getText().toString();
        dosen = edtDosen.getText().toString();
        ruang=edtRuang.getText().toString();
        email = savedUsername;

        if (matkul.isEmpty() || hari.isEmpty() || jam1.isEmpty() || jam2.isEmpty() || dosen.isEmpty()||ruang.isEmpty() ) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
                    long id = dbHelper.insertDataMatkul(hari, matkul,jam1,jam2,ruang,dosen,email);
                    if (id <= 0) {
                        Message.message(getApplicationContext(), "Register Unsuccessfull");

                    } else {
                        showLoadingIndicator();
                        Message.message(getApplicationContext(), "Register Successful");
                        edtMatkul.setText("");
                        edtRuang.setText("");
                        edtDosen.setText("");
                        edtJam1.setText("");
                        edtJam2.setText("");
                        shari.setSelection(0);
                        ActivityTambahMatkul.super.onBackPressed();
                        finish();
                        finish();
                    }
                }

            }

    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }

    public void back(View view) {
        ActivityTambahMatkul.super.onBackPressed();
        finish();
    }
}

