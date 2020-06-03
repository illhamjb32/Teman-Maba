package com.example.login_page;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private ImageView imgFoto;
    protected Cursor cursor;
    private SimpleDateFormat dateFormatter;
    private EditText txtNama, txtEmail, txtPhone, txtAnswer, txtPassword, txtConfirmPassword,txtAlamat,txtTanggal;
    String mail, namaMhs, phone, alamat, jk, tanggal_lahir, pertanyaan, jawaban,old_email;
    private RadioGroup rgJenisKelamin;
    private RadioButton rbLaki, rbPerempuan;
    private Spinner spinnerPertanyaan;
    private String savedUsername,jenisKelamin;
    dbHelper dbHelper;
    // SharedPreferences yang akan digunakan untuk menulis dan membaca data
    private SharedPreferences sharedPrefs;
    ProgressDialog loadingIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        txtNama = (EditText) findViewById(R.id.edtNama);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtAlamat=(EditText) findViewById(R.id.edtAlamat) ;
        txtTanggal=(EditText) findViewById(R.id.edtTanggalLahir);
        txtPhone = (EditText) findViewById(R.id.edtPhone);
        txtAnswer = (EditText) findViewById(R.id.edtAnswer);
        txtPassword = (EditText) findViewById(R.id.edtPassword);
        txtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        rgJenisKelamin = (RadioGroup) findViewById(R.id.rgJK);
        spinnerPertanyaan = (Spinner) findViewById(R.id.spinnerPertanyaan);
        rbLaki = (RadioButton) findViewById(R.id.rbLaki);
        rbPerempuan = (RadioButton) findViewById(R.id.rbPerempuan);
        imgFoto=(ImageView) findViewById(R.id.imgProfile) ;
        dbHelper = new dbHelper(this);
        this.sharedPrefs = this.getSharedPreferences("user_detail", Context.MODE_PRIVATE);
        tampilData();
        changePicture();
    }
    private void showLoadingIndicator()
    {
        loadingIndicator = new ProgressDialog(this);
        loadingIndicator.setMessage("Uploading user data to server...");
        loadingIndicator.setIndeterminate(false);
        loadingIndicator.setCancelable(false);
        loadingIndicator.show();
    }
    public void changePicture(){
        if(jenisKelamin.matches("Laki-laki")) {
            imgFoto.setImageResource(R.drawable.men);
        }else{
            imgFoto.setImageResource(R.drawable.woman);
        }
    }

    @Override
    public void onBackPressed() {
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
            new AlertDialog.Builder(this)
                    .setTitle("Data masih kosong!")
                    .setMessage("Apakah anda yakin ingin membatalkan?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();

        }
        else{
            EditActivity.super.onBackPressed();
        }
    }

    private void showDateDialog(){
        if(TextUtils.isEmpty(tanggal_lahir)) {
            Calendar newCalendar = Calendar.getInstance();
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    txtTanggal.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }else{
            String[] pisahTanggal = tanggal_lahir.split("/");
            Calendar newCalendar = Calendar.getInstance();
            Integer hasilBulan=Integer.parseInt(pisahTanggal[1])-1;
            datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);

                    txtTanggal.setText(dateFormatter.format(newDate.getTime()));
                }

            },Integer.parseInt(pisahTanggal[2]), hasilBulan, Integer.parseInt(pisahTanggal[0]));

            datePickerDialog.show();
        }
    }
    public void pilihTanggal(View view) {
        showDateDialog();
    }
    public void clickChange(View view) {
        Intent i = new Intent(EditActivity.this, ChangePassword.class);
        startActivity(i);
    }
    public void update(View view) {
        namaMhs = txtNama.getText().toString();
        int idJk = rgJenisKelamin.getCheckedRadioButtonId();
        switch (idJk) {
            case R.id.rbLaki:
                jk = rbLaki.getText().toString();
                break;
            case R.id.rbPerempuan:
                jk = rbPerempuan.getText().toString();
                break;
        }
        alamat = txtAlamat.getText().toString();
        tanggal_lahir = txtTanggal.getText().toString();
        mail = txtEmail.getText().toString();
        pertanyaan = spinnerPertanyaan.getSelectedItem().toString();
        jawaban = txtAnswer.getText().toString();
        savedUsername = this.sharedPrefs.getString("username", null);
        old_email = savedUsername;
        if (namaMhs.isEmpty() || mail.isEmpty() || phone.isEmpty() || jawaban.isEmpty() || alamat.isEmpty() || tanggal_lahir.isEmpty() || pertanyaan.isEmpty()) {
            Message.message(getApplicationContext(), "Silahkan lengkapi data!");
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches()) {
                Message.message(getApplicationContext(), "Please enter a Valid E-Mail Address!");
            } else {
                if (mail.matches(old_email)) {
                    int a = dbHelper.update(mail, namaMhs, phone, alamat, jk, tanggal_lahir, pertanyaan, jawaban);
                    if (a <= 0) {
                        Message.message(getApplicationContext(), "Unsuccessful");
                    } else {
                        Message.message(getApplicationContext(), "Updated");
                        showLoadingIndicator();
                        txtNama.setText("");
                        rbLaki.setChecked(true);
                        txtAlamat.setText("");
                        txtTanggal.setText("");
                        txtEmail.setText("");
                        txtAnswer.setText("");
                        tampilData();
                        changePicture();
                        EditActivity.super.onBackPressed();
                        finish();

                    }
                } else {
                    try {
                    int b = dbHelper.updateSame(mail, namaMhs, phone, alamat, jk, tanggal_lahir, pertanyaan, jawaban, old_email);
                    int c = dbHelper.updateMatkulEmail(mail, old_email);
                    int d = dbHelper.updateTugasEmail(mail,old_email);
                    int e = dbHelper.updateContactEmail(mail,old_email);

                    if (b <= 0) {
                        Message.message(getApplicationContext(), "Unsuccessful");

                    } else {
                        Message.message(getApplicationContext(), "Updated");
                        showLoadingIndicator();
                        txtNama.setText("");
                        rbLaki.setChecked(true);
                        txtAlamat.setText("");
                        txtTanggal.setText("");
                        txtEmail.setText("");
                        txtAnswer.setText("");
                        SharedPreferences.Editor editor = this.sharedPrefs.edit();
                        editor.putString("username", mail);
                        editor.apply();
                        tampilData();
                        changePicture();
                        EditActivity.super.onBackPressed();
                        finish();
                    }
                    }catch (Exception e)
                    {
Message.message(getApplicationContext(),"Berhasil");

                    }
                }
            }
        }
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }
    public void tampilData(){
        savedUsername = this.sharedPrefs.getString("username", null);
        String email = savedUsername;
        String nama;
        String[] emailargs = {email};
        dbHelper = new dbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.query("mahasiswa", null, "email=?", emailargs, null, null, null);
        cursor.moveToNext();
        cursor.moveToPosition(0);
                namaMhs = cursor.getString(cursor.getColumnIndex("nama"));
                jenisKelamin = cursor.getString(cursor.getColumnIndex("jenis_kelamin"));
                if(jenisKelamin.matches("Laki-laki")){
                    rbLaki.setChecked(true);
                }
                else{
                    rbPerempuan.setChecked(true);
                }
        alamat = cursor.getString(4);
        tanggal_lahir = cursor.getString(6);
                if(TextUtils.isEmpty(alamat)){
                    txtAlamat.setText("");
                    txtTanggal.setText("");
                }else{
                txtAlamat.setText(alamat);
                txtTanggal.setText(tanggal_lahir);
                }
                mail = cursor.getString(cursor.getColumnIndex("email"));
                phone=cursor.getString(cursor.getColumnIndex("no_telp"));
                jawaban=cursor.getString(cursor.getColumnIndex("jawaban"));
                pertanyaan=cursor.getString(cursor.getColumnIndex("pertanyaan"));
                txtNama.setText(namaMhs);
                txtEmail.setText(mail);
                txtPhone.setText(phone);
                txtAnswer.setText(jawaban);
        spinnerPertanyaan.setSelection(getIndex(spinnerPertanyaan, pertanyaan));
                }
public void delete(){
    int a=dbHelper.delete(savedUsername);
    if(a<=0){
        Message.message(getApplicationContext(),"Unseccessful");
    }
    else
    {
        showLoadingIndicator();
        Message.message(this,"Your Account Has Beed Deleted!");
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();
        Intent intent = new Intent(EditActivity.this, WelcomeHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}

    public void clickDelete(View view) {
        new AlertDialog.Builder(this)
                .setMessage("Apakah anda yakin ingin menghapus akun?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete();
                    }
                })
                .setNegativeButton("Tidak", null)
                .show();
}

    public void back(View view) {
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
            new AlertDialog.Builder(this)
                    .setTitle("Data masih kosong!")
                    .setMessage("Apakah anda yakin ingin membatalkan?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            EditActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("Tidak", null)
                    .show();

        }
        else{
            EditActivity.super.onBackPressed();
        }
    }
}
