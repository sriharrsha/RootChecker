package com.rootcheckerplus.root.viewpagerfragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.rootcheckerplus.root.BuildConfig;
import com.rootcheckerplus.root.R;



public class WikiFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public WikiFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WikiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WikiFragment newInstance() {
        WikiFragment fragment = new WikiFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_wiki, container, false);
        MobileAds.initialize(getContext(), "ca-app-pub-2504330195355115~1456906586");
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        AdRequest adRequest;
        if (BuildConfig.DEBUG) {
            adRequest = new AdRequest.Builder()
          //          .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            //        .addTestDevice(MainActivity.getDeviceId())
                    .build();
        }else {
            adRequest= new AdRequest.Builder().build();
        }
        mAdView.loadAd(adRequest);
        TextView rootWiki=(TextView) root.findViewById(R.id.root_wiki_content);
        rootWiki.setText(R.string.root_wiki_content);
        TextView advantagesWiki=(TextView) root.findViewById(R.id.advantages_wiki);
        advantagesWiki.setText("1)Install \"Incompatible\" Apps\n\n2)Automate Everything with Tasker\n\n3)Remove Ads by Adblocker Apps\n\n4)Boost your phone's speed by overclocking\n\n5) Back Up Your Phone along with apps using Titanium\n\n6) Remove Preinstalled Crapware(Bloatware)\n\n7)Flash a custom Kernel\n\n8) Flash a custom ROM(OS)\n\n9) Get 100% Privacy with XPrivacy App\n\n10) Get full control on Permissions\n");
        return root;
    }

}
