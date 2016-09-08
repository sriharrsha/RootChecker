package com.rootcheckerplus.root.viewpagerfragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rootcheckerplus.root.BuildConfig;
import com.rootcheckerplus.root.MainActivity;
import com.rootcheckerplus.root.R;

/**
 * Created by Sri Harrsha on 28-Aug-16.
 */
public class HomeFragment extends Fragment {
    private static final String KEY_POSITION = "KEY_POSITION";

    public static Fragment newInstance(int position) {
        HomeFragment homeFragment =new HomeFragment();
        Bundle args=new Bundle();
        args.putInt(KEY_POSITION, position);
        homeFragment.setArguments(args);
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View root=inflater.inflate(R.layout.layout_card_fragment,container,false);
            MobileAds.initialize(getContext(), "ca-app-pub-2504330195355115~1456906586");
            AdView mAdView = (AdView) root.findViewById(R.id.adView);
            AdRequest adRequest;
            if (BuildConfig.DEBUG) {
                adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(MainActivity.getDeviceId())
                        .build();
            }else {
                adRequest= new AdRequest.Builder().build();
            }
            mAdView.loadAd(adRequest);
            return root;
    }

    public static CharSequence getTitle(Context context, int position) {
        switch (position){
            case 0: return "PHONE";

            case 1: return "HOME";

            case 2: return "WIKI";

            default: return "HOME";
        }
    }
}
