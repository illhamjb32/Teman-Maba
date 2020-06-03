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
public class MatkulActivity extends AppCompatActivity {
    dbHelper dbHelper;
    private int count;
    private LottieAnimationView empty;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList id;
    private ArrayList hariList; //Digunakan untuk Nama
    private ArrayList matkulList; //Digunakan untuk Jurusan
    private ArrayList jam1List; //Digunakan untuk Jurusan
    private ArrayList jam2List; //Digunakan untuk Jurusan
    private ArrayList dosenList; //Digunakan untuk Jurusan
    private ArrayList ruangList; //Digunakan untuk Jurusan
    Cursor cursor;
    private CardView card;
    private String savedUsername;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matkul);
        id=new ArrayList<>();
        hariList = new ArrayList<>();
        matkulList = new ArrayList<>();
        jam1List = new ArrayList<>();
        jam2List = new ArrayList<>();
       ruangList= new ArrayList<>();
        dosenList = new ArrayList<>();
        dbHelper = new dbHelper (this);
        recyclerView = findViewById(R.id.rcView);
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(id,hariList,matkulList,jam1List,jam2List,ruangList,dosenList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
        empty=(LottieAnimationView) findViewById(R.id.empty);
        //Membuat Underline pada Setiap Item Didalam List
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        getData();

    }
    //Berisi Statement-Statement Untuk Mengambi Data dari Database
    @SuppressLint("Recycle")
    protected void getData(){
        try {
            savedUsername= this.sharedPrefs.getString("username", null);
            String[] emailargs = {savedUsername};
            //Mengambil Repository dengan Mode Membaca
            dbHelper = new dbHelper(this);
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            cursor = db.query("matakuliah", null, "email=?", emailargs, null, null, null);

            //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
            for (count = 0; count < cursor.getCount(); count++) {

                cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
                id.add(cursor.getString(0));
                hariList.add(cursor.getString(1));
                matkulList.add(cursor.getString(2));
                jam1List.add(cursor.getString(3));
                jam2List.add(cursor.getString(4));
                ruangList.add(cursor.getString(5));
                dosenList.add(cursor.getString(6));
            }
            if(count==0){
                empty.setVisibility(View.VISIBLE);
            }else{
                empty.setVisibility(View.INVISIBLE);
            }
        }catch (Exception e){
            Message.message(getApplicationContext(),"Data Masih Kosong");
        }
    }
    @Override
    protected void onRestart(){
        super.onRestart();
        super.onRestart();
        id.clear();
        matkulList.clear();
        hariList.clear();
        jam1List.clear();
        jam2List.clear();
        ruangList.clear();
        dosenList.clear();
        getData();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

    public void tambahMatkulForm(View view) {
        Intent i = new Intent(MatkulActivity.this, ActivityTambahMatkul.class);
        startActivity(i);
    }

    public void back(View view) {
        MatkulActivity.super.onBackPressed();
        finish();
    }
}

