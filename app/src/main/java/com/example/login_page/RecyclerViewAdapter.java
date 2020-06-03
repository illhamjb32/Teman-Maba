package com.example.login_page;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//Class Adapter ini Digunakan Untuk Mengatur Bagaimana Data akan Ditampilkan
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private ArrayList idList; //Digunakan untuk Nama
    private ArrayList hariList; //Digunakan untuk Nama
    private ArrayList matkulList; //Digunakan untuk Jurusan
    private ArrayList jam1List; //Digunakan untuk Jurusan
    private ArrayList jam2List; //Digunakan untuk Jurusan
    private ArrayList dosenList; //Digunakan untuk Jurusan
    private ArrayList ruangList; //Digunakan untuk Jurusan
    private Context context;
    private LinearLayout itemList;
    dbHelper dbHelper;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapter(ArrayList id,ArrayList hariList, ArrayList matkulList, ArrayList jam1List,ArrayList jam2List,ArrayList dosenList,ArrayList ruangList){
        this.idList=id;
        this.hariList = hariList;
        this.matkulList = matkulList;
        this.jam1List = jam1List;
        this.jam2List = jam2List;
        this.dosenList = dosenList;
        this.ruangList = ruangList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView hari,matkul,jam1,jam2,dosen,ruang;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            hari = itemView.findViewById(R.id.vhari);
            matkul = itemView.findViewById(R.id.vmatkul);
            jam1 = itemView.findViewById(R.id.vjam1);
            jam2 = itemView.findViewById(R.id.vjam2);
            dosen = itemView.findViewById(R.id.vDosen);
            ruang = itemView.findViewById(R.id.vRuang);
            itemList=itemView.findViewById(R.id.linear);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_matkul, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String hari = (String) hariList.get(position);//Mengambil data (Nama) sesuai dengan posisi yang telah ditentukan
        final String matkul = (String) matkulList.get(position);//Mengambil data (Jurusan) sesuai dengan posisi yang telah ditentukan
        final String jam1 = (String) jam1List.get(position);
        final String jam2 = (String) jam2List.get(position);
        final String dosen = (String) dosenList.get(position);
        final String ruang = (String) ruangList.get(position);
        final String id=(String) idList.get(position);

        holder.hari.setText(hari);
        holder.matkul.setText(matkul);
        holder.jam1.setText(jam1);
        holder.jam2.setText(jam2);
        holder.dosen.setText(dosen);
        holder.ruang.setText(ruang);
        itemList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Bundle bundle = new Bundle();
                                bundle.putString("id",(String) idList.get(position));
                                bundle.putString("hari", (String) hariList.get(position));
                                bundle.putString("matkul", (String) matkulList.get(position));
                                bundle.putString("jam1",(String) jam1List.get(position));
                                bundle.putString("jam2",(String) jam2List.get(position));
                                bundle.putString("ruang",(String) ruangList.get(position));
                                bundle.putString("dosen",(String) dosenList.get(position));
                                Intent intent = new Intent(view.getContext(), EditMatkul.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            case 1:
                                dbHelper = new dbHelper(context);
                                int a=dbHelper.deleteMatkul((String) idList.get(position));
                                if(a<=0){
                                    Message.message(context,"Unseccessful");
                                }
                                else
                                {
                                    Intent intenta = new Intent(view.getContext(), MatkulActivity.class);
                                    context.startActivity(intenta);
                                    ((Activity)context).finish();
                                    Message.message(context,"Matakuliah Telah Dihapus!");

                                }
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });



    }



    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
        return matkulList.size();
    }

}