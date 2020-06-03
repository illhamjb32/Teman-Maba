package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditMatkul extends AppCompatActivity {
    private EditText edtJam1,edtJam2,edtMatkul,edtDosen,edtRuang;
    private SharedPreferences sharedPrefs;
    private String ambilJam1,ambilJam2,idMatkul;
    private String jam1,jam2,matkul,dosen,ruang,email,hari;
    private String savedUsername;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;
    Spinner shari;
    DatePickerDialog.OnDateSetListener date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_matkul);
        edtJam1=(EditText) findViewById(R.id.edtJam1);
        edtJam2=(EditText) findViewById(R.id.edtJam2);
        edtMatkul=(EditText) findViewById(R.id.edtMatkul);
        edtDosen=(EditText)findViewById(R.id.edtDosen);
        edtRuang=(EditText) findViewById(R.id.edtRuang);
        shari=(Spinner) findViewById(R.id.spinnerHari);
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
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getId = getIntent().getExtras().getString("id");
        final String getHari = getIntent().getExtras().getString("hari");
        final String getMatkul = getIntent().getExtras().getString("matkul");
        final String getJam1 = getIntent().getExtras().getString("jam1");
        final String getJam2 = getIntent().getExtras().getString("jam2");
        final String getRuang = getIntent().getExtras().getString("ruang");
        final String getDosen = getIntent().getExtras().getString("dosen");
        edtMatkul.setText(getMatkul);
        edtDosen.setText(getDosen);
        edtJam1.setText(getJam1);
        edtJam2.setText(getJam2);
        edtRuang.setText(getRuang);
        edtDosen.setText(getDosen);
        ambilJam1=getJam1;
        ambilJam2=getJam2;
        shari.setSelection(getIndex(shari,getHari));
        idMatkul=getId;
    }
    public void pilihjam(View view) {
        String[] pisahJam1 = ambilJam1.split(":");
        int hour = Integer.parseInt(pisahJam1[0]);
        int minute = Integer.parseInt(pisahJam1[1]);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EditMatkul.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtJam1.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }
    public void pilihjam2(View view) {
        String[] pisahJam2 = ambilJam1.split(":");
        int hour = Integer.parseInt(pisahJam2[0]);
        int minute = Integer.parseInt(pisahJam2[1]);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(EditMatkul.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                edtJam2.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();
    }

    public void editMatkul(View view) {
        matkul = edtMatkul.getText().toString();
        hari = shari.getSelectedItem().toString();
        jam1=edtJam1.getText().toString();
        jam2= edtJam2.getText().toString();
        dosen = edtDosen.getText().toString();
        ruang=edtRuang.getText().toString();

        if (matkul.isEmpty() || hari.isEmpty() || jam1.isEmpty() || jam2.isEmpty() || dosen.isEmpty()||ruang.isEmpty() ) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            int a = dbHelper.updateMatkul(idMatkul,hari,matkul,jam1,jam2,ruang,dosen);
            if (a <= 0) {
                Message.message(getApplicationContext(), "Unsuccessful");
            } else {
                Message.message(getApplicationContext(), "Updated");
                showLoadingIndicator();
                edtMatkul.setText("");
                edtRuang.setText("");
                edtDosen.setText("");
                edtJam1.setText("");
                edtJam2.setText("");
                shari.setSelection(0);
                EditMatkul.super.onBackPressed();
                finish();
            }
        }
        }

    public void back(View view) {
        EditMatkul.super.onBackPressed();
        finish();
    }
}

