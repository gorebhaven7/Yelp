package com.example.yelp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yelp.Model.ReviewModel;
import com.example.yelp.R;

import java.util.List;;

public class ReviewModelAdapter extends RecyclerView.Adapter<ReviewModelAdapter.MyViewHolder> {

    private Context rContext;
    private List<ReviewModel> rData;

    public ReviewModelAdapter(Context rContext, List<ReviewModel> rData) {
        this.rContext = rContext;
        this.rData = rData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(rContext);
        view = inflater.inflate(R.layout.review_row_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ReviewModel reviewModel = rData.get(position);

        holder.tv_review.setText(rData.get(position).getText());
        holder.tv_reviewer.setText(rData.get(position).getName());
        holder.tv_reviewer_rating.setText(rData.get(position).getRating());
        holder.tv_reviewer_time_created.setText(rData.get(position).getTime_created());

    }

    public int getItemCount() {
        return rData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_review;
        TextView tv_reviewer;
        TextView tv_reviewer_rating;
        TextView tv_reviewer_time_created;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_review = itemView.findViewById(R.id.tv_review);
            tv_reviewer = itemView.findViewById(R.id.tv_reviewer);
            tv_reviewer_rating = itemView.findViewById(R.id.tv_reviewer_rating);
            tv_reviewer_time_created = itemView.findViewById(R.id.tv_reviewer_time_created);
        }
    }

}
