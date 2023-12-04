package com.example.yelp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;


import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.yelp.API.ApiCalls;
import com.example.yelp.Model.BusinessModel;
import com.example.yelp.Adapters.BusinessTableAdapter;
import com.example.yelp.Interface.InterfaceAPI;
import com.example.yelp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {

    private List<BusinessModel> lstBusiness;
    private RecyclerView recyclerView;
    private JsonObjectRequest request;
    private RequestQueue requestQueue;
    private ArrayAdapter<String> adapter1;
    List<String> responseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ApiCalls.getInstance(this);
        setTheme(R.style.Theme_Yelp);
        setContentView(R.layout.activity_main);

//        ToolBar

        ImageView reservations = findViewById(R.id.reservations);

        reservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Reservations.class);
                startActivity(intent);
            }
        });

//        DropDown Category

        Spinner category = (Spinner) findViewById(R.id.category_spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        category.setAdapter(adapter);

//        CheckBox Click

        CheckBox check = (CheckBox) findViewById(R.id.checkBox);
        EditText location = (EditText) findViewById(R.id.location);
        final boolean[] ischeck = {false};
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CompoundButton) v).isChecked()) {
                    ischeck[0] = true;
                    location.setVisibility(View.INVISIBLE);
                } else {
                    ischeck[0] = false;
                    location.setVisibility(View.VISIBLE);
                }
            }
        });

//      FormValidation



        EditText distance;
        Button submit, clear;
        AutoCompleteTextView keyword;

        keyword = findViewById(R.id.keyword);

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    Log.d("keyword", "onTextChanged: " + s);
//                    AutoComplete(keyword.getText().toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line);
        keyword.setAdapter(adapter1);

        distance = findViewById(R.id.distance);
        submit = findViewById(R.id.submit);
        clear = findViewById(R.id.clear);
        String cat = category.getSelectedItem().toString();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = keyword.getText().toString();
                String dis = distance.getText().toString();
                String loc = null;
                final String[] lat = new String[1];
                final String[] lang = new String[1];

                if (TextUtils.isEmpty(key)) {
                    keyword.setError("This field is required");
                    return;
                }
                if (TextUtils.isEmpty(dis)) {
                    distance.setError("This field is required");
                    return;
                }
                if (!ischeck[0]) {
                    loc = location.getText().toString();
                    if (TextUtils.isEmpty(loc)) {
                        location.setError("This field is required");
                        return;

                    }
                }

                if (ischeck[0]) {

                    ApiCalls.getInstance().getCurrentLocation(new InterfaceAPI() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.d("Response", response.toString());
                            lat[0] = response.optString("loc").split(",")[0];
                            lang[0] = response.optString("loc").split(",")[1];
                            Log.d("Response IP", lat[0] + lang[0]);

                            Submit(key, dis, cat, lat[0], lang[0]);
                        }
                    });
                } else {
                    ApiCalls.getInstance().getGivenLocation(loc, new InterfaceAPI() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.d("Response", response.optJSONArray("results").toString());
                            for (int i = 0; i < response.optJSONArray("results").length(); i++) {
                                lat[0] = response.optJSONArray("results").optJSONObject(i).optJSONObject("geometry").optJSONObject("location").optString("lat");
                                lang[0] = response.optJSONArray("results").optJSONObject(i).optJSONObject("geometry").optJSONObject("location").optString("lng");
                            }

                            Log.d("Response Given", response.toString());
                            Submit(key, dis, cat, lat[0], lang[0]);
                        }
                    });
                }



            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword.setText("");
                distance.setText("");
                location.setText("");
                check.setChecked(false);
                location.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

    }

    private void AutoComplete(String s) {
        Log.d("Bhaven1", "AutoComplete");
        ApiCalls.getInstance().getAutoComplete(s, new InterfaceAPI() {
            @Override
            public void onSuccess(JSONObject response) {
//                Log.d("Response", response.toString());
                responseList = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("terms");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String name = jsonObject.getString("text");
                        responseList.add(name);
                    }
                    jsonArray = response.getJSONArray("categories");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        responseList.add(jsonObject.getString("title"));
                    }
                    Log.d("Bhaven1", responseList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter1.addAll(responseList);
                adapter1.notifyDataSetChanged();

            }
        });
    }

    private void Submit(String key, String dis, String cat, String lat, String lang) {

        String JSON_URL = "https://backend-368000.uc.r.appspot.com/fetchsecondtabledata?term="+key+"&latitude="+lat+"&longitude="+lang+"&categories="+cat+"&radius="+dis;
        lstBusiness = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_viewid);
        jsonrequest(key, dis, cat, lat, lang);
        Log.d("Bhaven", JSON_URL);
    }

    private void jsonrequest(String key, String dis, String cat, String lat, String lang){
        ApiCalls.getInstance().getBusiness(key, dis, cat, lat, lang, new InterfaceAPI() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("Response", response.toString());
                try {
                    JSONArray jsonArray = response.getJSONArray("businesses");
                    Log.d("Bhaven", jsonArray.toString());
                    TextView nores = findViewById(R.id.noResult);

                    if(jsonArray.toString().equals("[]")){
                        recyclerView.setVisibility(View.GONE);
                        nores.setVisibility(View.VISIBLE);
                    }
                    else{

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String dist = String.valueOf((Double.parseDouble(jsonObject.getString("distance")) / 1609));
                            Log.d("Bhaven", jsonObject.toString());
                            Log.d("Bhaven", jsonObject.optString("url"));
                            BusinessModel businessModel = new BusinessModel();
                            businessModel.setId(String.valueOf(i + 1));
                            businessModel.setName(jsonObject.getString("name"));
                            businessModel.setRating(jsonObject.getString("rating"));
                            businessModel.setBusinessId(jsonObject.getString("id"));
                            businessModel.setDistance(String.format("%.0f", Double.parseDouble(dist)));
                            businessModel.setImage_url(jsonObject.getString("image_url"));
                            businessModel.setUrl(jsonObject.optString("url"));
                            lstBusiness.add(businessModel);

                        }
                        recyclerView.setVisibility(View.VISIBLE);
                        nores.setVisibility(View.GONE);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
                setuprecyclerview(lstBusiness);
            }
        });
    }

    private void setuprecyclerview(List<BusinessModel> lstBusiness) {

        BusinessTableAdapter myadapter = new BusinessTableAdapter(this, lstBusiness);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);
    }

}