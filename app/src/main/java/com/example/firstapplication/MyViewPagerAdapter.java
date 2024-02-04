package com.example.firstapplication;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyViewPagerAdapter  extends FragmentStateAdapter {
    public MyViewPagerAdapter(@NonNull CafePage fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new BeveragesFragment();
            case 1:
                return new PastryFragment();
            case 2:
                return new SnacksFragment();

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
