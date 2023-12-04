package com.example.yelp.Fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yelp.API.ApiCalls;
import com.example.yelp.Interface.InterfaceAPI;
import com.example.yelp.R;
import com.example.yelp.Model.ReviewModel;
import com.example.yelp.Adapters.ReviewModelAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewFragment extends Fragment {


    private List<ReviewModel> lstReview;
    private RecyclerView recyclerView;
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    String id;

    public ReviewFragment(String id) {
        // Required empty public constructor
        this.id = id;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        lstReview = new ArrayList<>();
        recyclerView = view.findViewById(R.id.review_recycler_view);
        getReviewData(this.id);
        return view;
    }

    private void getReviewData(String id) {
        ApiCalls.getInstance().ReviewData(id, new InterfaceAPI() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("Review Data: ", response.toString());

                JSONArray jsonArray = response.optJSONArray("reviews");
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ReviewModel reviewModel = new ReviewModel();
                        reviewModel.setText(jsonObject.getString("text"));
                        String rat = "Rating :"+jsonObject.getString("rating")+"/5";
                        reviewModel.setRating(rat);
                        String n = jsonObject.getJSONObject("user").getString("name");
//                        Log.d("Name", n);
//                        reviewModel.setText(jsonObject.getString("name"));
                        reviewModel.setName(n);
                        reviewModel.setTime_created(jsonObject.getString("time_created").split(" ")[0]);
                        lstReview.add(reviewModel);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setuprecyclerview(lstReview);
            }
        });
    }

    private void setuprecyclerview(List<ReviewModel> lstReview) {
        ReviewModelAdapter myadapter = new ReviewModelAdapter(getContext(),lstReview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(myadapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);
    }

}