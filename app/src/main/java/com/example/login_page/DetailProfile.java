package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailProfile extends AppCompatActivity {
    private TextView textNama, textJk, textAlamat, textTTL, textEmail, textTelp;
    private String jenisKelamin;
    private ImageView imgPP;
    protected Cursor cursor;
    dbHelper dbHelper;
    private String savedUsername;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);
       textNama=(TextView) findViewById(R.id.viewNama);
       textJk=(TextView) findViewById(R.id.viewJk);
       textAlamat=(TextView) findViewById(R.id.viewAlmt);
       textTTL=(TextView) findViewById(R.id.viewTgl);
       textEmail=(TextView) findViewById(R.id.viewEmail);
       textTelp=(TextView) findViewById(R.id.viewTelp);
       imgPP=(ImageView) findViewById(R.id.imgProfile);
        dbHelper = new dbHelper(this);
        imgPP=(ImageView) findViewById(R.id.detailPP);
        // Inisialisasi SharedPreferences
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        tampilNama();
        changePicture();
    }



    public void tampilNama() {
        try {
            savedUsername = this.sharedPrefs.getString("username", null);
            String email = savedUsername;
            String nama;
            String[] emailargs = {email};
            dbHelper = new dbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
            cursor.moveToNext();
            cursor.moveToPosition(0);
            jenisKelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"));
            textNama.setText(cursor.getString(cursor.getColumnIndex("nama")));
            textJk.setText(jenisKelamin);
            textAlamat.setText(cursor.getString(cursor.getColumnIndex("alamat")));
            textTTL.setText(cursor.getString(cursor.getColumnIndex("tanggal_lahir")));
            textEmail.setText(cursor.getString(cursor.getColumnIndex("email")));
            textTelp.setText(cursor.getString(cursor.getColumnIndex("no_telp")));
        } catch (Exception e){Message.message(getApplicationContext(),""+e);}}

    public void editProfile(View view) {
        Intent intent = new Intent(DetailProfile.this, EditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        tampilNama();
        changePicture();
    }

    public void changePicture(){
        if(jenisKelamin.matches("Laki-laki")) {
            imgPP.setImageResource(R.drawable.men);
        }else{
            imgPP.setImageResource(R.drawable.woman);
        }
    }

    public void back(View view) {
        DetailProfile.super.onBackPressed();
        finish();
    }
}
