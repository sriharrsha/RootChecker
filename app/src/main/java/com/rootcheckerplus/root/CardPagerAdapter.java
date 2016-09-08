package com.rootcheckerplus.root;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rootcheckerplus.root.viewpagerfragments.BuildFragment;
import com.rootcheckerplus.root.viewpagerfragments.HomeFragment;
import com.rootcheckerplus.root.viewpagerfragments.WikiFragment;

/**
 * Created by Sri Harrsha on 28-Aug-16.
 */
public class CardPagerAdapter extends FragmentPagerAdapter {
    Context context;
    public CardPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        this.context=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:return BuildFragment.newInstance();
            case 1:return HomeFragment.newInstance(position);
            case 2:return WikiFragment.newInstance();
            default: return HomeFragment.newInstance(position);
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return HomeFragment.getTitle(context,position);
    }
}
