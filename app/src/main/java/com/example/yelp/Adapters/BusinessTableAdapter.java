package com.example.yelp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yelp.Activity.CardData;
import com.example.yelp.Model.BusinessModel;
import com.example.yelp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BusinessTableAdapter extends RecyclerView.Adapter<BusinessTableAdapter.MyViewHolder> {

    private Context mContext;
    private List<BusinessModel> mData;


    public BusinessTableAdapter(Context mContext, List<BusinessModel> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.table_row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final BusinessModel businessModel = mData.get(position);
        holder.tv_id.setText(mData.get(position).getId());
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_rating.setText(mData.get(position).getRating());
        holder.tv_distance.setText(mData.get(position).getDistance());
//        Picasso.get().load(mData.get(position).getImage_url()).into(holder.iv_image);
        Glide.with(mContext).load(mData.get(position).getImage_url()).into(holder.iv_image);
//        Glide.with(mContext).load("https://s3-media2.fl.yelpcdn.com/bphoto/KJUso7sKFzGAjHridy2adA/o.jpg").placeholder(R.drawable.splash_yelp).into(holder.iv_image);
        Log.d("Bhaven", "onBindViewHolder: "+mData.get(position).getImage_url());
//        holder.tv_business_id.setText(mData.get(position).getBusinessId());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Bhaven", "Clicked" + position);
                Intent intent = new Intent(view.getContext(), CardData.class);
                intent.putExtra("business_id", businessModel.getBusinessId());
                intent.putExtra("business_name", businessModel.getName());
                intent.putExtra("busines_url", businessModel.getUrl());
                Log.d("Bhaven",businessModel.getBusinessId());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_rating, tv_distance,tv_id,tv_business_id,tv_business_url;
        ImageView iv_image;
        LinearLayout layout;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_id = itemView.findViewById(R.id.Number);
            tv_name = itemView.findViewById(R.id.business_name);
            tv_rating = itemView.findViewById(R.id.business_rating);
            tv_distance = itemView.findViewById(R.id.business_distance);
            iv_image = itemView.findViewById(R.id.business_image);
            tv_business_id = itemView.findViewById(R.id.business_id);
            tv_business_url = itemView.findViewById(R.id.business_url);
            layout = itemView.findViewById(R.id.layout);


        }
    }


}
