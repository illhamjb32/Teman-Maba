package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class ForgetPassword2 extends AppCompatActivity {
    dbHelper dbHelper;
    protected Cursor cursor;
    private String savedUsername,answer;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;
    private Spinner spinPertanyaan;
    private EditText txtJawaban;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password2);
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("email_detail", Context.MODE_PRIVATE);
        spinPertanyaan=(Spinner) findViewById(R.id.spinnerPertanyaanForget);
        txtJawaban=(EditText)findViewById(R.id.edtCheckQestion);
        tampilSpinner();


    }

    public void clickSetNewPass(View view) {
        savedUsername = this.sharedPrefs.getString("email", null);
        String email = savedUsername;
        String[] emailargs = {email};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
        cursor.moveToNext();
        cursor.moveToPosition(0);
        answer = cursor.getString(cursor.getColumnIndex("jawaban"));
        if(txtJawaban.getText().toString().matches(answer)){
            Intent i = new Intent(ForgetPassword2.this, ForgetPassword3.class);
            startActivity(i);
            finish();
        }else{
            Message.message(this,"Jawaban Anda Salah!");
        }
    }
    public void tampilSpinner(){
        savedUsername = this.sharedPrefs.getString("email", null);
        String email = savedUsername;
        String[] emailargs = {email};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
        cursor.moveToNext();
        cursor.moveToPosition(0);
        answer = cursor.getString(cursor.getColumnIndex("pertanyaan"));
        ArrayList<String> arraySpinner= new ArrayList<>();
        arraySpinner.add(answer);
        ArrayAdapter<String> adapterarray=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,arraySpinner);
        spinPertanyaan.setAdapter(adapterarray);
        spinPertanyaan.setSelection(0);
    }
}
