package com.example.firstapplication;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class CafePage extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    MyViewPagerAdapter myViewPagerAdapter;
    Button orderPlacedBtn;


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
                Toast.makeText(requireContext(), "Order Placed Successfully!", Toast.LENGTH_SHORT).show();

            }
        });














//        VPAdapter vpAdapter = new VPAdapter(getActivity().getSupportFragmentManager(), getLifecycle());
//        vpAdapter.addFragment(new BeveragesFragment(),"Beverages");
//        vpAdapter.addFragment(new PastryFragment(),"Pastry");
//        vpAdapter.addFragment(new SnacksFragment(),"Snacks");
//        viewPager.setAdapter(vpAdapter);

//        VPMessageAdapter vpMessageAdapter = new VPMessageAdapter(getActivity().getSupportFragmentManager());
//        viewPager.setAdapter(vpMessageAdapter);
//        tabLayout.setupWithViewPager(viewPager);






        return view;
    }



}