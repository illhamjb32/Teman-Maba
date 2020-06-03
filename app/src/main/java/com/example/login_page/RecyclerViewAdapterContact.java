package com.example.login_page;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
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
public class RecyclerViewAdapterContact extends RecyclerView.Adapter<RecyclerViewAdapterContact.ViewHolder>{
    private ArrayList idList; //Digunakan untuk Nama
    private ArrayList firstList; //Digunakan untuk Nama
    private ArrayList secondList; //Digunakan untuk Jurusan
    private ArrayList noList; //Digunakan untuk Jurusan
    private ArrayList mailList;
    private Context context;
    private LinearLayout itemList;
    dbHelper dbHelper;

    //Membuat Konstruktor pada Class RecyclerViewAdapter
    RecyclerViewAdapterContact(ArrayList id,ArrayList firstList, ArrayList secondList, ArrayList noList, ArrayList mailList){
        this.idList=id;
        this.firstList = firstList;
        this.secondList = secondList;
        this.noList = noList;
        this.mailList = mailList;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView first,second,no,mail;

        ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            first = itemView.findViewById(R.id.vfirst);
            second = itemView.findViewById(R.id.vsecond);
            no = itemView.findViewById(R.id.vno);
            mail = itemView.findViewById(R.id.vmail);
            itemList=itemView.findViewById(R.id.linear);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Membuat View untuk Menyiapkan dan Memasang Layout yang Akan digunakan pada RecyclerView
        View V = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contact, parent, false);
        return new ViewHolder(V);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //Memanggil Nilai/Value Pada View-View Yang Telah Dibuat pada Posisi Tertentu
        final String first = (String) firstList.get(position);//Mengambil data (Nama) sesuai dengan posisi yang telah ditentukan
        final String second = (String) secondList.get(position);//Mengambil data (Jurusan) sesuai dengan posisi yang telah ditentukan
        final String no = (String) noList.get(position);
        final String mail = (String) mailList.get(position);
        holder.first.setText(first);
        holder.second.setText(second);
        holder.no.setText(no);
        holder.mail.setText(mail);
        itemList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                final String[] action = {"Tambah ke Kontak","Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                Intent intentC = new Intent(ContactsContract.Intents.Insert.ACTION);
                                intentC.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                                intentC.putExtra(ContactsContract.Intents.Insert.EMAIL, (String) mailList.get(position))
                                        .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                                        .putExtra(ContactsContract.Intents.Insert.PHONE, (String) noList.get(position))
                                        .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK)
                                        .putExtra(ContactsContract.Intents.Insert.NAME, (String) firstList.get(position)+' '+(String) secondList.get(position))
                            ;
                                context.startActivity(intentC);
                                break;

                            case 1:
                                Bundle bundle = new Bundle();
                                bundle.putString("id",(String) idList.get(position));
                                bundle.putString("first", (String) firstList.get(position));
                                bundle.putString("second", (String) secondList.get(position));
                                bundle.putString("no",(String) noList.get(position));
                                bundle.putString("mail",(String) mailList.get(position));
                                Intent intent = new Intent(view.getContext(), EditContact.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 2:
                                dbHelper = new dbHelper(context);
                                int a=dbHelper.deleteContact((String) idList.get(position));
                                if(a<=0){
                                    Message.message(context,"Unseccessful");
                                }
                                else
                                {
                                    Intent intenta = new Intent(view.getContext(), ActivityContact.class);
                                    context.startActivity(intenta);
                                    ((Activity)context).finish();
                                    Message.message(context,"Contact Telah Dihapus!");

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
        return idList.size();
    }

}