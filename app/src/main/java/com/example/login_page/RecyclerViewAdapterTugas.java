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
public class RecyclerViewAdapterTugas extends RecyclerView.Adapter<RecyclerViewAdapterTugas.ViewHolder>{
    private ArrayList idList; //Digunakan untuk Nama
    private ArrayList judulList; //Digunakan untuk Nama
    private ArrayList deadlineList; //Digunakan untuk Jurusan
    private ArrayList deskripsiList; //Digunakan untuk Jurusan
    private Context context;
    private LinearLayout itemList;
    dbHelper dbHelper;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapterTugas(ArrayList id,ArrayList judulList, ArrayList deadlineList, ArrayList deskripsiList){
        this.idList=id;
        this.judulList = judulList;
        this.deadlineList = deadlineList;
        this.deskripsiList = deskripsiList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView judul,deadline,deskripsi;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            judul = itemView.findViewById(R.id.vjudul);
            deadline = itemView.findViewById(R.id.vdeadline);
            deskripsi = itemView.findViewById(R.id.vdeskripsi);
            itemList=itemView.findViewById(R.id.linear);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tugas, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String judul = (String) judulList.get(position);//Mengambil data (Nama) sesuai dengan posisi yang telah ditentukan
        final String deadline = (String) deadlineList.get(position);//Mengambil data (Jurusan) sesuai dengan posisi yang telah ditentukan
        final String deskripsi = (String) deskripsiList.get(position);
        holder.judul.setText(judul);
        holder.deadline.setText(deadline);
        holder.deskripsi.setText(deskripsi);
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
                                bundle.putString("judul", (String) judulList.get(position));
                                bundle.putString("deadline", (String) deadlineList.get(position));
                                bundle.putString("deskripsi",(String) deskripsiList.get(position));
                                Intent intent = new Intent(view.getContext(), EditTugas.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            case 1:
                                dbHelper = new dbHelper(context);
                                int a=dbHelper.deleteTugas((String) idList.get(position));
                                if(a<=0){
                                    Message.message(context,"Unseccessful");
                                }
                                else
                                {
                                    Intent intenta = new Intent(view.getContext(), ActivityTampilTugas.class);
                                    context.startActivity(intenta);
                                    ((Activity)context).finish();
                                    Message.message(context,"Tugas Telah Dihapus!");

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
        return judulList.size();
    }

}