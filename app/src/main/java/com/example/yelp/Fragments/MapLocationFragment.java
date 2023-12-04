package com.example.yelp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yelp.API.ApiCalls;
import com.example.yelp.Interface.InterfaceAPI;
import com.example.yelp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

public class MapLocationFragment extends Fragment {

    String id;

    public MapLocationFragment(String id) {
        this.id=id;
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map_location, container, false);

        getCardData(this.id);

        return  view;
    }

    private void getCardData(String id) {
        ApiCalls.getInstance().CardData(id, new InterfaceAPI() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("CardData", response.toString());
                try {
                    double latitude = response.getJSONObject("coordinates").getDouble("latitude");
                    double longitude = response.getJSONObject("coordinates").getDouble("longitude");
                    Log.d("CardData", "Latitude: " + latitude + " Longitude: " + longitude);
                    SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {

                            LatLng latLng = new LatLng(latitude, longitude);
                            googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}