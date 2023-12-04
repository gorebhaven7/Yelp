package com.example.yelp.LocalStorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.yelp.Adapters.DataAdapter;
import com.example.yelp.Model.Data;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PrefConfig {

   private SharedPreferences dataStore;
   private Gson gson;
   private static ArrayList<Data> data = new ArrayList<>();

    public PrefConfig(Context context){
         dataStore = context.getSharedPreferences("user", Context.MODE_PRIVATE);
         gson = new Gson();
    }

    public void saveData(String name,String email,String Date,String Time){

        String json = dataStore.getString("data", null);
        Type dataListType = new TypeToken<ArrayList<Data>>(){}.getType();
        data = gson.fromJson(json, dataListType);
        if(data == null){
            data = new ArrayList<>();
        }
        data.add(0,new Data(name,email,Date,Time));
        String json1 = gson.toJson(data);
        SharedPreferences.Editor editor = dataStore.edit();
        editor.putString("data", json1);
        editor.apply();
    }

    public ArrayList<Data> getData(){
        String json = dataStore.getString("data", null);
        Type dataListType = new TypeToken<ArrayList<Data>>(){}.getType();
        ArrayList<Data> data = gson.fromJson(json, dataListType);
        Log.d("GetData", String.valueOf(data));
        if(data == null){
            return new ArrayList<>();
        }
        else
            return data;
    }

    public void removeData(Context context, int position, DataAdapter adapter1){
        SharedPreferences pref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

//        editor.remove(position);
        editor.apply();

        String json = dataStore.getString("data", null);
        Type dataListType = new TypeToken<ArrayList<Data>>(){}.getType();
        ArrayList<Data> data = gson.fromJson(json, dataListType);
        Log.d("Bhaven", "removeData: "+context + " "+position+" "+data);

        data.remove(position);
        String json1 = gson.toJson(data);
        Log.d("Bhaven", "saveData: "+json1);
        SharedPreferences.Editor editor1 = dataStore.edit();
        editor1.putString("data", json1);
        editor1.apply();
        adapter1.notifyDataSetChanged();
        Log.d("Bhaven", "after Delete: "+context + " "+position+" "+data);
    }

}
