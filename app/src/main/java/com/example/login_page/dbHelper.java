package com.example.login_page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME = "kampus.db";
    private static final String TABLE_NAME = "mahasiswa";
    private static final String TABLE_NAME_MATKUL = "matakuliah";
    private static final String TABLE_NAME_TUGAS = "tugas";
    private static final String TABLE_NAME_CONTACT = "contact";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE ="CREATE TABLE "+TABLE_NAME+
            "(email VARCHAR(50) PRIMARY KEY,"+
            "password VARCHAR(50),"+
            "nama VARCHAR(50),"+
            "no_telp VARCHAR(20),"+
            "alamat VARCHAR(60),"+
            "jenis_kelamin VARCHAR(20),"+
            "tanggal_lahir DATE,"+
            "pertanyaan VARCHAR(60),"+
            "jawaban VARCHAR(60));";
    private static final String CREATE_TABLE_MATKUL ="CREATE TABLE "+TABLE_NAME_MATKUL+
            "(id_matkul INTEGER  PRIMARY KEY AUTOINCREMENT,"+
            "hari VARCHAR(20),"+
            "nama_matkul VARCHAR(70),"+
            "jam_mulai VARCHAR(20),"+
            "jam_selesai VARCHAR(20),"+
            "ruang VARCHAR(20),"+
            "nama_dosen VARCHAR(70),"+
            "email VARCHAR(50));";
    private static final String CREATE_TABLE_TUGAS ="CREATE TABLE "+TABLE_NAME_TUGAS+
            "(id_tugas INTEGER  PRIMARY KEY AUTOINCREMENT,"+
            "nama_tugas VARCHAR(70),"+
            "deadline DATE,"+
            "deskripsi TEXT,"+
            "email VARCHAR(50));";
    private static final String CREATE_TABLE_CONTACT ="CREATE TABLE "+TABLE_NAME_CONTACT+
            "(id_contact INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "first_name VARCHAR(70),"+
            "last_name VARCHAR(70),"+
            "no_telp VARCHAR(30),"+
            "email_dosen VARCHAR(30),"+
            "email VARCHAR(50));";
    private static final String DROP_TABLE="DROP TABLE IF EXISTS "+TABLE_NAME;
    private static final String DROP_TABLE_MATKUL="DROP TABLE IF EXISTS "+TABLE_NAME_MATKUL;
    private static final String DROP_TABLE_CONTACT="DROP TABLE IF EXISTS "+TABLE_NAME_CONTACT;
    private static final String DROP_TABLE_TUGAS="DROP TABLE IF EXISTS "+TABLE_NAME_TUGAS;
    private Context context;

    public dbHelper(@Nullable Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }
    @Override
    public void onCreate (SQLiteDatabase db){
        try{
            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE_MATKUL);
            db.execSQL(CREATE_TABLE_TUGAS);
            db.execSQL(CREATE_TABLE_CONTACT);
        } catch (Exception e){
            Message.message(context,"error "+e);
        }
    }
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion,int newVersion){
        try{
            Message.message(context,"onUpgrade");
            db.execSQL(DROP_TABLE);
            db.execSQL(DROP_TABLE_MATKUL);
            db.execSQL(DROP_TABLE_TUGAS);
            db.execSQL(DROP_TABLE_CONTACT);
            onCreate(db);
        }catch (Exception e){
            Message.message(context,""+e);
        }
    }
    public long insertData (String email,String password,String nama,String phone, String jk,String pertanyaan,String jawaban){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        values.put("password",password);
        values.put("nama",nama);
        values.put("no_telp",phone);
        values.put("jenis_kelamin",jk);
        values.put("pertanyaan",pertanyaan);
        values.put("jawaban",jawaban);

        long id=db.insert(TABLE_NAME,null,values);
        return  id;
    }
    public long insertDataMatkul (String hari,String nama_matkul,String jam_mulai, String jam_selesai,String ruang,String nama_dosen, String email){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("hari",hari);
        values.put("nama_matkul",nama_matkul);
        values.put("jam_mulai",jam_mulai);
        values.put("jam_selesai",jam_selesai);
        values.put("ruang",ruang);
        values.put("nama_dosen",nama_dosen);
        values.put("email",email);

        long id=db.insert(TABLE_NAME_MATKUL,null,values);
        return  id;
    }
    public long insertDataTugas(String nama_tugas,String deadline, String deskripsi,String email){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("nama_tugas",nama_tugas);
        values.put("deadline",deadline);
        values.put("deskripsi",deskripsi);
        values.put("email",email);

        long id=db.insert(TABLE_NAME_TUGAS,null,values);
        return  id;
    }

    public long insertDataContact(String first_name,String last_name, String no_telp,String email_dosen,String email){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("first_name",first_name);
        values.put("last_name",last_name);
        values.put("no_telp",no_telp);
        values.put("email_dosen",email_dosen);
        values.put("email",email);

        long id=db.insert(TABLE_NAME_CONTACT,null,values);
        return  id;
    }

    public int delete (String email){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs = {email};
        int count = db.delete(TABLE_NAME,"email= ?",whereArgs);
        return count;
    }

    public int deleteMatkul (String id){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs = {id};
        int count = db.delete(TABLE_NAME_MATKUL,"id_matkul= ?",whereArgs);
        return count;
    }

    public int deleteTugas (String id){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs = {id};
        int count = db.delete(TABLE_NAME_TUGAS,"id_tugas= ?",whereArgs);
        return count;
    }
    public int deleteContact (String id){
        SQLiteDatabase db=getWritableDatabase();
        String[] whereArgs = {id};
        int count = db.delete(TABLE_NAME_CONTACT,"id_contact= ?",whereArgs);
        return count;
    }

    public int update (String email,String nama,String no_telp,String alamat,
                       String jenis_kelamin,String tanggal_lahir,String pertanyaan,String jawaban)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        values.put("nama",nama);
        values.put("no_telp",no_telp);
        values.put("alamat",alamat);
        values.put("jenis_kelamin",jenis_kelamin);
        values.put("tanggal_Lahir",tanggal_lahir);
        values.put("pertanyaan",pertanyaan);
        values.put("jawaban",jawaban);
        String[] whereArgs={email};
        int count = db.update(TABLE_NAME,values,"email=?",whereArgs);
        return count;
    }

    public int updateMatkul(String id, String hari,String matkul,String jam1,String jam2,
                       String ruang,String dosen)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("hari",hari);
        values.put("nama_matkul",matkul);
        values.put("jam_mulai",jam1);
        values.put("jam_selesai",jam2);
        values.put("ruang",ruang);
        values.put("nama_dosen",dosen);
        String[] whereArgs={id};
        int count = db.update(TABLE_NAME_MATKUL,values,"id_matkul=?",whereArgs);
        return count;
    }

    public int updateMatkulEmail(String email,String old_email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        String[] whereArgs={old_email};
        int count = db.update(TABLE_NAME_MATKUL,values,"email=?",whereArgs);
        return count;
    }

    public int updateTugas(String id, String judul,String deadline,String deskripsi)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("nama_tugas",judul);
        values.put("deadline",deadline);
        values.put("deskripsi",deskripsi);
        String[] whereArgs={id};
        int count = db.update(TABLE_NAME_TUGAS,values,"id_tugas=?",whereArgs);
        return count;
    }

    public int updateTugasEmail(String email,String old_email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        String[] whereArgs={old_email};
        int count = db.update(TABLE_NAME_TUGAS,values,"email=?",whereArgs);
        return count;
    }


    public int updateContact(String id, String first,String second,String no,String mail)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("first_name",first);
        values.put("last_name",second);
        values.put("no_telp",no);
        values.put("email_dosen",mail);
        String[] whereArgs={id};
        int count = db.update(TABLE_NAME_CONTACT,values,"id_contact=?",whereArgs);
        return count;
    }

    public int updateContactEmail(String email, String old_email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        String[] whereArgs={old_email};
        int count = db.update(TABLE_NAME_CONTACT,values,"email=?",whereArgs);
        return count;
    }


    public int updateSame(String email,String nama,String no_telp,String alamat,
                       String jenis_kelamin,String tanggal_lahir,String pertanyaan,String jawaban, String old_email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("email",email);
        values.put("nama",nama);
        values.put("no_telp",no_telp);
        values.put("alamat",alamat);
        values.put("jenis_kelamin",jenis_kelamin);
        values.put("tanggal_Lahir",tanggal_lahir);
        values.put("pertanyaan",pertanyaan);
        values.put("jawaban",jawaban);
        String[] whereArgs={old_email};
        int count = db.update(TABLE_NAME,values,"email=?",whereArgs);
        return count;
    }

    public int updatePass(String password, String email)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("password",password);
        String[] whereArgs={email};
        int count = db.update(TABLE_NAME,values,"email=?",whereArgs);
        return count;
    }

    public boolean checkUserExist(String email, String password){
        SQLiteDatabase db=getWritableDatabase();
        String[] columns = {"email"};

        String selection = "email=? and password=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }
    public boolean checkEmailExist(String email){
        SQLiteDatabase db=getWritableDatabase();
        String[] columns = {"email"};

        String selection = "email=?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        cursor.close();
        close();

        if(count > 0){
            return true;
        } else {
            return false;
        }
    }
}
