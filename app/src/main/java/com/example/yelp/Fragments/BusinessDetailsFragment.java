package com.example.yelp.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yelp.API.ApiCalls;
import com.example.yelp.Activity.MainActivity;
import com.example.yelp.Interface.InterfaceAPI;
import com.example.yelp.LocalStorage.PrefConfig;
import com.example.yelp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class BusinessDetailsFragment extends Fragment {

    String id,name;
    TextView address,price,phone,status,category,url;
    LinearLayout image_scroll;
    Button reserve;
    static SharedPreferences sharedPreferences;
//    ArrayList<Data> data = new ArrayList<>();


    public BusinessDetailsFragment(String id,String name) {
        // Required empty public constructor
        this.id = id;
        this.name = name;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for thisfragment
        View view = inflater.inflate(R.layout.fragment_business_details, container, false);

        address = view.findViewById(R.id.tv_address);
        price = view.findViewById(R.id.tv_price_range);
        phone = view.findViewById(R.id.tv_phone_number);
        status = view.findViewById(R.id.tv_status);
        category = view.findViewById(R.id.tv_category);
        url = view.findViewById(R.id.tv_url);
        reserve = view.findViewById(R.id.reserve_button);
        image_scroll = view.findViewById(R.id.image_scroll);

        getCardData(this.id);

        reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.reservation_model);
                dialog.setTitle("Reserve a table");
                TextView submit,clear,tv_name;
                submit = dialog.findViewById(R.id.model_submit);
                clear = dialog.findViewById(R.id.model_cancel);

                tv_name = dialog.findViewById(R.id.tv_name);
                tv_name.setText(name);
                EditText email = dialog.findViewById(R.id.email);
                EditText date = dialog.findViewById(R.id.date);
                EditText time = dialog.findViewById(R.id.time);


                Calendar calendar = Calendar.getInstance();

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        String Format = "MM-dd-yyyy";
                        SimpleDateFormat sdf = new SimpleDateFormat(Format);
                        date.setText(sdf.format(calendar.getTime()));
                    }
                };
                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog datePicker = new DatePickerDialog(getContext(),dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                        datePicker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                        datePicker.show();
                        datePicker.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        datePicker.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                    }
                });

                final TimePickerDialog[] timePickerDialog = new TimePickerDialog[1];
                time.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timePickerDialog[0] = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                calendar.set(Calendar.MINUTE,minute);
                                String Format = "hh:mm a";
                                SimpleDateFormat sdf = new SimpleDateFormat(Format);
                                String time1 = sdf.format(calendar.getTime());
                                DateFormat outputFormat = new SimpleDateFormat("HH:mm");
                                String time2 = "00:00";
                                try {
                                    time2 = outputFormat.format(sdf.parse(time1));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                time.setText(time2);
                            }
                        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false);
                        timePickerDialog[0].show();
                        timePickerDialog[0].getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                        timePickerDialog[0].getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
                    }
                });



                PrefConfig prefConfig = new PrefConfig(getContext());
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String email1 = email.getText().toString();
                        String time1 = time.getText().toString();
                        if(!emailValidator(email1)){
                            Toast.makeText(getContext(),"InValid Email Address",Toast.LENGTH_SHORT).show();
                        }
                        else if(!timeValidator(time1)){
                            Toast.makeText(getContext(),"Time should be between 10AM AND 5PM ",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            prefConfig.saveData(name,email.getText().toString(), date.getText().toString(), time.getText().toString());
                        }
                        dialog.dismiss();

                        Toast.makeText(getContext(), "Reservation Booked", Toast.LENGTH_LONG).show();
                    }
                });
                clear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        return view;
    }

    private void getCardData(String id) {
        ApiCalls.getInstance().CardData(id, new InterfaceAPI() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("CardData", response.toString());
                try {
                    String msg = response.getJSONObject("location").getString("address1") + " "+response.getJSONObject("location").getString("address2") + response.getJSONObject("location").getString("city") + " " + response.getJSONObject("location").getString("state") + " " + response.getJSONObject("location").getString("zip_code");
                    address.setText(msg);
                    price.setText(response.getString("price"));
                    phone.setText(response.getString("display_phone"));
                    if (response.getString("is_closed").equals("false")) {
                        status.setText("Open Now");
                        status.setTextColor(Color.GREEN);
                    }
                    else {
                        status.setText("Closed");
                        status.setTextColor(Color.RED);
                    }
//                    status.setText(response.getString("is_closed"));
                    String cate = response.getJSONArray("categories").getJSONObject(0).getString("title")+"|"+response.getJSONArray("categories").getJSONObject(1).getString("title")+"|"+response.getJSONArray("categories").getJSONObject(2).getString("title");
                    category.setText(response.getJSONArray("categories").getJSONObject(0).getString("title"));
                    url.setClickable(true);
                    url.setMovementMethod(LinkMovementMethod.getInstance());
                    String text = "<a href='"+response.getString("url")+"'>Business Link </a>";
                    url.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY));
//                    name = response.getString("price");
//                    Log.d("Disha",name);
//                    String[] imageURL = new String[]{};
//                    Log.d("Image",response.getJSONArray("photos").getJSONObject());
                    for(int i=0;i<response.getJSONArray("photos").length();i++){
                        Log.d("Image", response.getJSONArray("photos").getString(i));
                        image_scroll.addView(addImageView(response.getJSONArray("photos").getString(i)));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean timeValidator(String time){
        String hh = time.split(":")[0];
        String mm = time.split(":")[1];
        int total = Integer.parseInt(hh)*60 + Integer.parseInt(mm);
        int start = 10*60;
        int end = 17*60;

        if(total>=start && total<=end){
            return true;
        }
        else{
            return false;
        }
    }

    private View addImageView(String s){
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setPadding(10,10,200,10);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Picasso.get().load(s).into(imageView);
        linearLayout.addView(imageView);
        return linearLayout;
    }

}