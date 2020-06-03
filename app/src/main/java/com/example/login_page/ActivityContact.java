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
public class ActivityContact extends AppCompatActivity {
    //deklarasi variabel
    dbHelper dbHelper;
    private int count;
    private LottieAnimationView empty;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList id;
    private ArrayList firstList; //Digunakan untuk Nama
    private ArrayList secondList; //Digunakan untuk Jurusan
    private ArrayList noList; //Digunakan untuk Jurusan
    private ArrayList mailList;
    Cursor cursor;
    private CardView card;
    private String savedUsername;
    private SharedPreferences sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        //pengambilan data arraylist;
        id = new ArrayList<>();
        firstList = new ArrayList<>();
        secondList= new ArrayList<>();
        noList = new ArrayList<>();
        mailList = new ArrayList<>();
        dbHelper = new dbHelper(this); //dbHelper
        recyclerView = findViewById(R.id.rcView); //recylcler view
        //Menggunakan Layout Manager, Dan Membuat List Secara Vertical
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapterContact(id,firstList,secondList,noList,mailList);
        //Memasang Adapter pada RecyclerView
        recyclerView.setAdapter(adapter);
        empty=(LottieAnimationView) findViewById(R.id.empty); // plugin lotie untuk animasi
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);//sharedpreference
        getData();//fungsi tampil data


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
            cursor = db.query("contact", null, "email=?", emailargs, null, null, null);

            //Melooping Sesuai Dengan Jumlan Data (Count) pada cursor
            for (count = 0; count < cursor.getCount(); count++) {

                cursor.moveToPosition(count);//Berpindah Posisi dari no index 0 hingga no index terakhir
                id.add(cursor.getString(0));
                firstList.add(cursor.getString(1));
                secondList.add(cursor.getString(2));
                noList.add(cursor.getString(3));
                mailList.add(cursor.getString(4));
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

//berfungsi merefresh activity ketika lifecycle restart
    @Override
    protected void onRestart() {
        super.onRestart();
        id.clear();
        firstList.clear();
        secondList.clear();
        noList.clear();
        mailList.clear();
        getData();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }

//intent
    public void openFormContact(View view) {
        Intent intent = new Intent(ActivityContact.this, ActivityTambahContact.class);
        startActivity(intent);
    }
//intent
    public void back(View view) {
        ActivityContact.super.onBackPressed();
        finish();
    }
}