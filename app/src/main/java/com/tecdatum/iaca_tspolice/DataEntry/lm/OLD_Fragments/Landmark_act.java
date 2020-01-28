package com.tecdatum.iaca_tspolice.DataEntry.lm.OLD_Fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.app.AlertDialog;

import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.tecdatum.iaca_tspolice.DataEntry.lm.OLD_Fragments.ParkingF;
import com.tecdatum.iaca_tspolice.DataEntry.lm.OLD_Fragments.VitalF;
import com.tecdatum.iaca_tspolice.R;
import com.tecdatum.iaca_tspolice.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class Landmark_act extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);


        SharedPreferences bb = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String s_OrgName =bb.getString("OrgName","");
        TextView tv_OrgName=(TextView)findViewById(R.id.tv_distname) ;
        tv_OrgName.setText(""+s_OrgName);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


   //     adapter.addFragment(new SensitiveF(), "Sensitive Zones");
        adapter.addFragment(new ParkingF(), "Parking Places");
        adapter.addFragment(new VitalF(), "Water Loggings");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}