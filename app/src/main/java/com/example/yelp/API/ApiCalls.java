package com.example.yelp.API;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.yelp.Interface.InterfaceAPI;
import com.example.yelp.Interface.TableInterfaceAPI;

import org.json.JSONArray;
import org.json.JSONObject;

public class ApiCalls {

    private static ApiCalls instance = null;
    public RequestQueue queue;

    private ApiCalls(Context context) {
         queue = Volley.newRequestQueue(context);
    }

    public static synchronized ApiCalls getInstance(Context context){
        if(null == instance){
            instance = new ApiCalls(context);
        }
        return instance;
    }

    public static synchronized ApiCalls getInstance() {
        return instance;
    }

    public void getAutoComplete(String key,InterfaceAPI anInterfaceAPI){
        String url = "https://backend-368000.uc.r.appspot.com/getautocomplete?Keyword=" + key;
        Log.d("Bhaven1",url);
        JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        anInterfaceAPI.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }

    public void getCurrentLocation(InterfaceAPI anInterfaceAPI){
        String url = "https://ipinfo.io?token=d7bdd983979acd";
        JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        String latitude = response.optString("loc").split(",")[0];
//                        String longtitude= response.optString("loc").split(",")[1];
//                        Log.d("Latitude",latitude);
//                        Log.d("Longtitude",longtitude);
//                        getlatlong(response);

                        anInterfaceAPI.onSuccess(response);


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

// Access the RequestQueue through your singleton class.
        queue.add(jsonObjectRequest);
    }

    public void getGivenLocation(String location, InterfaceAPI anInterfaceAPI) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+location+"&key=AIzaSyDIf0VgJ8BL0l5uoQlNvTbanhdXfoMGKQM";
        JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        anInterfaceAPI.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void getBusiness(String keyword, String distance, String category, String latitude, String longtitude, InterfaceAPI anInterfaceAPI) {
        String url = "https://backend-368000.uc.r.appspot.com/fetchsecondtabledata?term="+keyword+"&latitude="+latitude+"&longitude="+longtitude+"&categories="+category+"&radius="+distance;
        JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        anInterfaceAPI.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void CardData(String id, InterfaceAPI anInterfaceAPI) {
        String url = "https://backend-368000.uc.r.appspot.com/getcarddata?id="+id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        anInterfaceAPI.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void ReviewData(String id, InterfaceAPI anInterfaceAPI) {
//        String url = "https://backend-368000.uc.r.appspot.com/getreviews?id="+id+"/reviews";
//        String url = "https://webtechhw8-366202.wl.r.appspot.com/yelpdata1?id="+id+"/reviews";
        String url = "https://backend-368000.uc.r.appspot.com/getreviews?id="+id;
        Log.d("URL",url);

        JSONObject[] res = new JSONObject[1];
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        anInterfaceAPI.onSuccess(response);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
    }

}
