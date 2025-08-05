package com.example.contactsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.contactholder> {
    private List<database> db = new ArrayList<>();
    @NonNull
    @Override
    public contactholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerviewer, parent, false);
        return new contactholder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull contactholder holder, int position) {

        holder.textViewname.setText(db.get(position).getName());
        holder.textViewnumber.setText(db.get(position).getNumber());


    }

    @Override
    public int getItemCount() {
        return db.size();
    }


    public void setDb(List<database>db) {
        this.db= db;
        notifyDataSetChanged();
    }

    class contactholder extends RecyclerView.ViewHolder{

        private TextView textViewname;
        private TextView textViewnumber;
        public contactholder(View itemView) {
                super(itemView);
                textViewname=itemView.findViewById(R.id.name);






        }
    }
}
