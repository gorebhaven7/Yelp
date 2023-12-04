package com.example.yelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yelp.Model.Data;
import com.example.yelp.Adapters.DataAdapter;
import com.example.yelp.LocalStorage.PrefConfig;
import com.example.yelp.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class Reservations extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView no_bookings;
    private DataAdapter adapter;
    private ArrayList<Data> data = new ArrayList<>();
    PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservations);

        PrefConfig prefConfig = new PrefConfig(this);

        ImageView backArrow = findViewById(R.id.back_reservation);
        no_bookings = findViewById(R.id.no_bookings);
        recyclerView = findViewById(R.id.booking_recycler_view);
        data = prefConfig.getData();
        checkData();
        Log.d("Reservation", "onCreate: "+data);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String delete;
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAbsoluteAdapterPosition();
                data.remove(position);
                adapter.notifyItemRemoved(position);
                prefConfig.removeData(getApplicationContext(),position,adapter);
                Log.d("Reservation", "onCreate: "+data);
                checkData();

                Snackbar.make(recyclerView,"Removing Existing Reservation",Snackbar.LENGTH_LONG).setAction("", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(Reservations.this, R.color.red))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                        .setSwipeLeftLabelColor(ContextCompat.getColor(Reservations.this, R.color.white))
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(data);
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);



    }
    public void checkData(){
        if(data.size() == 0){
            recyclerView.setVisibility(View.GONE);
            no_bookings.setVisibility(View.VISIBLE);
        }
        else{
            no_bookings.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}