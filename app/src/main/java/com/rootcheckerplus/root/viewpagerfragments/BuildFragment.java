package com.rootcheckerplus.root.viewpagerfragments;

import android.os.Build;
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
import com.rootcheckerplus.root.MainActivity;
import com.rootcheckerplus.root.R;

public class BuildFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextView board;
    TextView bootloader;
    TextView brand;
    TextView cpuAbi;
    TextView cpuAbi2;
    TextView device;
    TextView display;
    TextView hardware;
    TextView host;
    TextView deviceID;
    TextView manufacturer;
    TextView model;
    TextView product;
    TextView radio;
    TextView serial;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BuildFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BuildFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BuildFragment newInstance() {
        BuildFragment fragment = new BuildFragment();
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
        View root = inflater.inflate(R.layout.fragment_build, container, false);


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
        board=(TextView)root.findViewById(R.id.board);
        board.setText(Build.BOARD);
        bootloader=(TextView)root.findViewById(R.id.bootloader);
        bootloader.setText(Build.BOOTLOADER);
        brand=(TextView)root.findViewById(R.id.brand);
        brand.setText(Build.BRAND);
        cpuAbi=(TextView)root.findViewById(R.id.cpu_abi);
        cpuAbi.setText(Build.CPU_ABI);
        cpuAbi2=(TextView)root.findViewById(R.id.cpu_abi2);
        cpuAbi2.setText(Build.CPU_ABI2);
        device=(TextView)root.findViewById(R.id.device);
        device.setText(Build.DEVICE);
        display=(TextView)root.findViewById(R.id.display);
        display.setText(Build.DISPLAY);
        hardware=(TextView)root.findViewById(R.id.hardware);
        hardware.setText(Build.HARDWARE);
        host=(TextView)root.findViewById(R.id.host);
        host.setText(Build.HOST);
        deviceID=(TextView)root.findViewById(R.id.deviceID);
        deviceID.setText(Build.ID);
        manufacturer=(TextView)root.findViewById(R.id.manufacturer);
        manufacturer.setText(Build.MANUFACTURER);
        model=(TextView)root.findViewById(R.id.model);
        model.setText(Build.MODEL);
        product=(TextView)root.findViewById(R.id.product);
        product.setText(Build.PRODUCT);
        radio=(TextView)root.findViewById(R.id.radio);
        radio.setText(Build.RADIO);
        serial=(TextView)root.findViewById(R.id.serial);
        serial.setText(Build.SERIAL);

        return root;
    }



}
