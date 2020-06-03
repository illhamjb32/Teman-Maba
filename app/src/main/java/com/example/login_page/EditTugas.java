package com.example.login_page;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditTugas extends AppCompatActivity {
    private EditText edtJudul,edtDeadline,edtDeskripsi;
    private SharedPreferences sharedPrefs;
    private String judul,deadline,deskripsi,idTugas,email;
    private String savedUsername;
    dbHelper dbHelper;
    ProgressDialog loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tugas);
        edtJudul=(EditText) findViewById(R.id.edtJudulTugas);
        edtDeadline=(EditText) findViewById(R.id.edtDeadline);
        edtDeskripsi=(EditText) findViewById(R.id.edtDeskripsi);
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
        final String getJudul = getIntent().getExtras().getString("judul");
        final String getDeadline = getIntent().getExtras().getString("deadline");
        final String getDeskripsi = getIntent().getExtras().getString("deskripsi");
        edtJudul.setText(getJudul);
        edtDeadline.setText(getDeadline);
        edtDeskripsi.setText(getDeskripsi);
        idTugas=getId;
    }

    public void editTugas(View view) {
        judul = edtJudul.getText().toString();
        deadline = edtDeadline.getText().toString();
        deskripsi=edtDeskripsi.getText().toString();

        if (judul.isEmpty() || deadline.isEmpty() || deskripsi.isEmpty()) {
            Message.message(getApplicationContext(), "Masukkan Semua Data");
        } else {
            int a = dbHelper.updateTugas(idTugas,judul,deadline,deskripsi);
            if (a <= 0) {
                Message.message(getApplicationContext(), "Unsuccessful");
            } else {
                Message.message(getApplicationContext(), "Updated");
                showLoadingIndicator();
                edtJudul.setText("");
                edtDeskripsi.setText("");
                edtDeadline.setText("");
                EditTugas.super.onBackPressed();
                finish();
            }
        }
    }

    public void back(View view) {
        EditTugas.super.onBackPressed();
        finish();
    }
}

