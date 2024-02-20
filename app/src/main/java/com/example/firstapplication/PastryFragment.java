package com.example.firstapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

public class PastryFragment extends Fragment {


    CheckBox ch, ch1, ch2;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_pastry, container, false);

        ch=(CheckBox)view.findViewById(R.id.teaCheckBox);
        ch1=(CheckBox)view.findViewById(R.id.CoffeeCheckBox);
        ch2=(CheckBox)view.findViewById(R.id.MilkCheckBox);

        return view;
    }


    public void Check()
    {
        String msg="";

        // Concatenation of the checked options in if

        // isChecked() is used to check whether
        // the CheckBox is in true state or not.

        if(ch.isChecked())
            CafePage.orders.add("BlackForest");
        if(ch1.isChecked())
            CafePage.orders.add("White Forest");
        if(ch2.isChecked())
            CafePage.orders.add("Vanilla");

        // Toast is created to display the
        // message using show() method.
    }
    @Override
    public void onPause() {
        super.onPause();
        // Call the Check() function when the fragment is about to lose focus
        Check();
    }

}