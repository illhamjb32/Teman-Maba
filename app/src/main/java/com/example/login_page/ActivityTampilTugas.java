package com.example.login_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
public class ActivityTampilTugas extends AppCompatActivity {
    dbHelper dbHelper;
    private int count;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList id;
    private ArrayList judulList; //Digunakan untuk Nama
    private ArrayList deadlineList; //Digunakan untuk Jurusan
    private ArrayList deskripsiList; //Digunakan untuk Jurusan
    Cursor cursor;
    private CardView card;
    private String savedUsername;
    private SharedPreferences sharedPrefs;
    private LottieAnimationView empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_tugas);
        id = new ArrayList<>();
        judulList = new ArrayList<>();
        deadlineList= new ArrayList<>();
        deskripsiList = new ArrayList<>();
        dbHelper = new dbHelper(this);
        recyclerView = findViewById(R.id.rcView);
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapterTugas(id,judulList,deadlineList,deskripsiList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
        empty=(LottieAnimationView) findViewById(R.id.empty);
        //Membuat Underline pada Setiap Item Didalam List
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        getData();

    }

    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    protected void getData() {
        try {
            savedUsername= this.sharedPrefs.getString("username", null);
            String[] emailargs = {savedUsername};
            //Mengambil Repository dengan Mode Membaca
            dbHelper = new dbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query("tugas", null, "email=?", emailargs, null, null, null);

            //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
            for (count = 0; count < cursor.getCount(); count++) {

                cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
                id.add(cursor.getString(0));
                judulList.add(cursor.getString(1));
                deadlineList.add(cursor.getString(2));
                deskripsiList.add(cursor.getString(3));
            }
            if(count==0){
                empty.setVisibility(View.VISIBLE);
            }else{
                empty.setVisibility(View.INVISIBLE);
            }
        } catch (Exception e) {
            Message.message(getApplicationContext(), "Data Masih Kosong");
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        id.clear();
        judulList.clear();
        deadlineList.clear();
        deskripsiList.clear();
        getData();
       adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void openFormTugas(View view) {
        Intent i = new Intent(ActivityTampilTugas.this, ActivityTugas.class);
        startActivity(i);
    }

    public void back(View view) {
        ActivityTampilTugas.super.onBackPressed();
        finish();
    }
}