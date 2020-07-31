package com.example.nhatro.Adapters;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.nhatro.View.Fragments.AnGiFragment;
import com.example.nhatro.View.Fragments.OdauFragment;


/**
 * Created by Binh on 4/13/17.
 */

public class AdapterViewPagerTrangChu extends FragmentStatePagerAdapter {
    AnGiFragment anGiFragment;
    OdauFragment odauFragment;

    public AdapterViewPagerTrangChu(FragmentManager fm) {
        super(fm);
        anGiFragment = new AnGiFragment();
        odauFragment = new OdauFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 :
                return odauFragment;


            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
