package com.example.yelp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yelp.Fragments.BusinessDetailsFragment;
import com.example.yelp.Fragments.MapLocationFragment;
import com.example.yelp.Fragments.ReviewFragment;
import com.example.yelp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.w3c.dom.Text;

public class CardData extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    String id,name,url;
    TextView cardName;
    ImageView backcard,facebook,twitter;

    private String[] titles = new String[]{"Business Details", "Map Location", "Reviews"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_data);
        id = getIntent().getStringExtra("business_id");
        name = getIntent().getStringExtra("business_name");
        url = getIntent().getStringExtra("busines_url");
        Log.d("Bhaven", "onCreate: "+url+id+name);

        cardName = findViewById(R.id.cardName);
        cardName.setText(name);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerFragmentStateAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager,
                (tab,position) -> tab.setText(titles[position])
        ).attach();

        backcard = findViewById(R.id.back_cardView);
        facebook = findViewById(R.id.facebook);
        twitter = findViewById(R.id.twitter);

        backcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(android.net.Uri.parse("https://www.facebook.com/sharer/sharer.php?u="+url));
                startActivity(intent);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(android.net.Uri.parse("https://twitter.com/intent/tweet?text=Check%20Out%20"+name+"%20on%20Yelp.&url="+url));
                startActivity(intent);
            }
        });


    }

    public class ViewPagerFragmentStateAdapter extends FragmentStateAdapter {
        public ViewPagerFragmentStateAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new BusinessDetailsFragment(id,name);
                case 1:
                    return new MapLocationFragment(id);
                case 2:
                    return new ReviewFragment(id);
            }
            return new BusinessDetailsFragment(id,name);
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }

}