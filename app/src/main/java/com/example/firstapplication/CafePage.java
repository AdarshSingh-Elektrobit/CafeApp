package com.example.firstapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class CafePage extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
    Button orderPlacedBtn;
    BeveragesFragment beveragesFragment;
    SnacksFragment snacksFragment ;
    PastryFragment pastryFragment ;

    static ArrayList<String> orders = new ArrayList<>();



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cafe_page, container, false);

        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.viewPager);


        myViewPagerAdapter = new MyViewPagerAdapter(this);

        viewPager2.setAdapter(myViewPagerAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });
        orderPlacedBtn = view.findViewById(R.id.orderPlacedBtn);
        orderPlacedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();

//                check();
                Log.i("Tagging",orders.toString());
                values.put(OrderContentProvider.orderList,orders.toString());

                Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show();

                // inserting into database through content URI
                getContext().getContentResolver().insert(OrderContentProvider.CONTENT_URI, values);
// displaying a toast message
                 Toast.makeText(getContext(), "Order Confirmed: " + orders.toString(), Toast.LENGTH_SHORT).show();
                onClickShowDetails();
                orders.clear();
            }
        });

        return view;
    }



    @SuppressLint("Range")
    public void onClickShowDetails() {

// creating a cursor object of the
// content URI
        Cursor cursor = getContext().getContentResolver().query(OrderContentProvider.CONTENT_URI,
                null, null, null, null);
// iteration of the cursor
// to print whole table
        if(cursor.moveToFirst()) {
            StringBuilder strBuild=new StringBuilder();
            while (!cursor.isAfterLast()) {
                strBuild.append("\n").
                        append(cursor.getString(cursor.getColumnIndex(OrderContentProvider.orderList)));




                cursor.moveToNext();

            }
            Log.i("Tagging","Done Adding in DB "+strBuild.toString());
            Toast.makeText(getContext(), strBuild.toString(), Toast.LENGTH_SHORT).show();
        }
        else {
            Log.i("Tagging","No Records Found");
        }
    }


}