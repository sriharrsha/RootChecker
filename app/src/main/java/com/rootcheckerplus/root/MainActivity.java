package com.rootcheckerplus.root;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appszoom.appszoomsdk.Appszoom;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.stericson.RootTools.RootTools;

public class MainActivity extends AppCompatActivity {
private ShareActionProvider share=null;



private FirebaseAnalytics mFirebaseAnalytics;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;


    InterstitialAd mInterstitialAd;
    public static String deviceId;
    private boolean paid=false;
    private android.widget.ShareActionProvider mShareActionProvider;


    private GoogleApiClient mClient;
    private Uri mUrl;
    private String mTitle;
    private String mDescription;
    private Intent mShareIntent;
    private int backCount=0;

    public static String getDeviceId()
    {
        return deviceId;
    }

    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        backCount++;
        if(backCount==0){
            Toast.makeText(getApplicationContext(),"Press back again to exit", Toast.LENGTH_SHORT).show();
            if(Appszoom.isAdAvailable()){
                Appszoom.showAd(this);
            }
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        backCount=0;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showIntroOnce();
       // showAppTourOnce();

        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        mUrl = Uri.parse("https://rootcheckerplus.com");
        mTitle = "Root Checker +";
        mDescription = "Check your phone's Root Status";

        mShareIntent = new Intent();
        mShareIntent.setAction(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");
        mShareIntent.putExtra(Intent.EXTRA_TEXT, "Hey Check whether your Phone is Rooted or Not? https://play.google.com/store/apps/details?id=com.rootcheckerplus.root");


        Appszoom.start(this);




        appTour();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setTitle("Root Checker +");
        viewPager = (ViewPager) findViewById(R.id.view_pager_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(false);
        FloatingActionButton floatingActionButton=(FloatingActionButton)this.findViewById(R.id.fab);
        viewPager = (ViewPager) findViewById(R.id.view_pager_layout);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        //// TODO: 01-Sep-16 Tablayout Icons


        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new CardPagerAdapter(getApplicationContext(),getSupportFragmentManager()));
        viewPager.setCurrentItem(1);

        String androidId =  Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceId = MD5(androidId).toUpperCase();

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_unit_id));
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showStatus();
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                   showLoadingDialog();
                if(paid){
                    showStatus();
                }else {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        showStatus();
                    }
                }
            }
        });
    }

    private void showAppTourOnce() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("apptour", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app tour
                    appTour();
                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();
                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("apptour", false);
                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
    }

    private void requestNewInterstitial() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest;
            if (BuildConfig.DEBUG) {
                adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(MainActivity.getDeviceId())
                        .build();
            }else {
                adRequest= new AdRequest.Builder().build();
            }
            mInterstitialAd.loadAd(adRequest);
        }
    }

    private void showIntroOnce(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(MainActivity.this, RootCheckerIntro.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();
    }
    public void showStatus(){

        RootTools.debugMode = true;
        viewPager.setCurrentItem(1);

        TextView superUserStatus=(TextView) viewPager.findViewById(R.id.superUserStatus);
        TextView rootAccessStatus=(TextView) viewPager.findViewById(R.id.rootAccessStatus);
        TextView busyBoxStatus=(TextView) viewPager.findViewById(R.id.busyBoxStatus);
        TextView pathValue=(TextView) viewPager.findViewById(R.id.pathValue);



        if(RootTools.isRootAvailable()){
            superUserStatus.setText("Super Access is Available");
            //superUserStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }else{
            superUserStatus.setText("Super Access is Not Available");
            //superUserStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.warning)));
        }

        if(RootTools.isAccessGiven()){
            rootAccessStatus.setText("Root is Available");
            //rootAccessStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
            pathValue.setText((CharSequence) RootTools.getPath());
        }else{
            rootAccessStatus.setText("Root is Not Available");
            //rootAccessStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.warning)));
            pathValue.setText("Not Available");
        }

        if(RootTools.isBusyboxAvailable()){
            busyBoxStatus.setText("Busy Box is Available");
            //busyBoxStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
        }else{
            busyBoxStatus.setText("Busy Box is Not Available");
            //busyBoxStatus.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.warning)));
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getAction());
    }

    @Override
    public void onStop() {
        AppIndex.AppIndexApi.end(mClient, getAction());
        mClient.disconnect();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list,menu);
        MenuItem item = menu.findItem(R.id.menu_item_share);
        share=(ShareActionProvider) MenuItemCompat.getActionProvider(item);
        share.setShareIntent(mShareIntent);
        share.setOnShareTargetSelectedListener(new ShareActionProvider.OnShareTargetSelectedListener() {
            @Override
            public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
                intent=mShareIntent;
                startActivity(intent);
                return false;
            }
        });

        return(super.onCreateOptionsMenu(menu));
    }

    public void showLoadingDialog() {
        RootCheckingProgressDialog fragment = (RootCheckingProgressDialog) getSupportFragmentManager().findFragmentByTag(RootCheckingProgressDialog.FRAGMENT_TAG);
        if (fragment == null) {
            fragment = new RootCheckingProgressDialog();
            fragment.setCancelable(false);
            getSupportFragmentManager().beginTransaction().add(fragment, RootCheckingProgressDialog.FRAGMENT_TAG).commitAllowingStateLoss();

            // fragment.show(getSupportFragmentManager().beginTransaction(), LoadingDialogFragment.FRAGMENT_TAG);
        }
    }

    public void hideLoadingDialog() {
        RootCheckingProgressDialog fragment = (RootCheckingProgressDialog) getSupportFragmentManager().findFragmentByTag(RootCheckingProgressDialog.FRAGMENT_TAG);
        if (fragment != null) {
            // fragment.dismissAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }


    private void appTour(){
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 16)).intValue();
        lps.setMargins(margin, margin, margin, margin);

        FloatingActionButton fab=(FloatingActionButton) this.findViewById(R.id.fab);
        ShowcaseView showcaseView=  new ShowcaseView.Builder(this)
                .setTarget(new ViewTarget(fab))
                .setContentTitle("Check Root Status")
                .setContentText("Tap here to check your phone's root status")
                .setStyle(R.style.CustomShowcaseTheme2)
                .withMaterialShowcase()
                .hideOnTouchOutside()
                .build();

        showcaseView.setButtonPosition(lps);
    }

    //// TODO: 01-Sep-16 InApp Billing
    //// TODO: 01-Sep-16 Share Button
    //// TODO: 01-Sep-16 In app indexing
    //// TODO: 01-Sep-16 Analytics
    //// TODO: 01-Sep-16 Crash reports
    //// TODO: 01-Sep-16 ICON Material Design


    // App Indexing Method
    public Action getAction() {
        Thing object = new Thing.Builder()
                .setName(mTitle)
                .setDescription(mDescription)
                .setUrl(mUrl)
                .build();

        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu_item_share){
            startActivity(mShareIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
