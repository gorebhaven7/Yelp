package com.example.yelp.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yelp.Model.Data;
import com.example.yelp.R;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {

    private ArrayList<Data> data;

    public DataAdapter( ){
    }

    public DataAdapter(ArrayList<Data> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView bookingEmail, bookingDate, bookingTime,name,bookid;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingEmail = itemView.findViewById(R.id.booking_enail);
            bookingDate = itemView.findViewById(R.id.booking_date);
            bookingTime = itemView.findViewById(R.id.booking_time);
            name = itemView.findViewById(R.id.booking_name);
            bookid = itemView.findViewById(R.id.booking_id);
        }
    }
    @NonNull
    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_data,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataAdapter.ViewHolder holder, int position) {
        Data dt = data.get(position);
        holder.bookid.setText(String.valueOf(position+1));
        holder.bookingEmail.setText(dt.getEmail());
        holder.bookingDate.setText(dt.getDate());
        holder.bookingTime.setText(dt.getTime());
        holder.name.setText(dt.getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
