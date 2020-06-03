package com.example.login_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class DashboardApp extends AppCompatActivity {
    private TextView textNama, textStatus,textInfo;
    private CardView cardMaps,cardMatkul,cardTugas,cardContact;
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
        setContentView(R.layout.activity_dashboard_app);
        textNama=(TextView) findViewById(R.id.txtNama) ;
        textStatus=(TextView)  findViewById(R.id.txtStatus);
        textInfo=(TextView) findViewById(R.id.txtInfo);
        dbHelper = new dbHelper(this);
        imgPP=(ImageView) findViewById(R.id.imgPict);
        cardMaps=(CardView) findViewById(R.id.cardMaps);
        cardMatkul=(CardView) findViewById(R.id.cardMatkul);
        cardTugas=(CardView) findViewById(R.id.cardTugas);
        cardContact=(CardView) findViewById(R.id.cardContact);
        // Inisialisasi SharedPreferences
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        tampilNama();
        cekData();
        changePicture();
    }

    public void showProfile(View view) {
        Intent intent = new Intent(DashboardApp.this, DetailProfile.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.apply();
                        DashboardApp.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilNama();
        cekData();
        changePicture();
    }

    public void changePicture(){
        if(jenisKelamin.matches("Laki-laki")) {
            imgPP.setImageResource(R.drawable.men);
        }else{
            imgPP.setImageResource(R.drawable.woman);
        }
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
            nama = cursor.getString(2);
            jenisKelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"));
            String[] kata = nama.split(" ");
            String namaDepan = kata[0];
            textNama.setText(namaDepan.toUpperCase());
        } catch (Exception e){Message.message(getApplicationContext(),""+e);}}

    public void logout(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.apply();
                        DashboardApp.super.onBackPressed();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
    }

    public void cekData(){
        String email = savedUsername;
        String alamat;
        String tanggal_lahir;
        String[] emailargs = {email};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
        cursor.moveToNext();
        cursor.moveToPosition(0);

        alamat = cursor.getString(4);
        tanggal_lahir = cursor.getString(6);
        if(TextUtils.isEmpty(alamat)&&TextUtils.isEmpty(tanggal_lahir)){
            textStatus.setText("Belum Lengkap!");textStatus.setTextColor(getResources().getColor(R.color.red));
            textInfo.setText("Yuk lengkapi dulu!");
            cardMaps.setClickable(false);cardMaps.setCardBackgroundColor(getResources().getColor(R.color.disable));
            cardMatkul.setClickable(false);cardMatkul.setCardBackgroundColor(getResources().getColor(R.color.disable));
            cardTugas.setClickable(false);cardTugas.setCardBackgroundColor(getResources().getColor(R.color.disable));
            cardContact.setClickable(false);cardContact.setCardBackgroundColor(getResources().getColor(R.color.disable));
        }
        else{
            textStatus.setText("Belum Lengkap!");textStatus.setTextColor(getResources().getColor(R.color.red));
            textInfo.setText("Yuk lengkapi dulu!");
            cardMaps.setClickable(true);cardMaps.setCardBackgroundColor(getResources().getColor(R.color.white));
            cardMatkul.setClickable(true);cardMatkul.setCardBackgroundColor(getResources().getColor(R.color.white));
            cardTugas.setClickable(true);cardTugas.setCardBackgroundColor(getResources().getColor(R.color.white));
            cardContact.setClickable(true);cardContact.setCardBackgroundColor(getResources().getColor(R.color.white));
            textStatus.setText("Sudah Lengkap!");textStatus.setTextColor(getResources().getColor(R.color.blue_sky));
            textInfo.setText("Selamat Beraktifitas :D");
        }


    }

    @Override
    protected void onRestart(){
        super.onRestart();
        tampilNama();
        cekData();
        changePicture();
    }

    public void openMaps(View view) {
        Intent intent = new Intent(DashboardApp.this, MapsActivity.class);
        startActivity(intent);
    }

    public void openMatkul(View view) {
        Intent intent = new Intent(DashboardApp.this, MatkulActivity.class);
        startActivity(intent);
    }

    public void openTugas(View view) {
        Intent intent = new Intent(DashboardApp.this, ActivityTampilTugas.class);
        startActivity(intent);
    }

    public void openContact(View view) {
        Intent intent = new Intent(DashboardApp.this, ActivityContact.class);
        startActivity(intent);
    }

    public void openAbout(View view) {
        Intent intent = new Intent(DashboardApp.this, ActivityAbout.class);
        startActivity(intent);
    }
}
