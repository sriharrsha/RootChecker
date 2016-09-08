package com.rootcheckerplus.root;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;

/**
 * Created by Sri Harrsha on 31-Aug-16.
 */
public class RootCheckerIntro extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int bgcolor = 0;
        // Instead of fragments, you can also use our default slide
        // Just set a title, description, background and image. AppIntro will do the rest.

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            bgcolor = getColor(R.color.colorPrimaryLight);
        }else {
            bgcolor=getResources().getColor(R.color.colorPrimaryLight);
        }
        addSlide(AppIntroFragment.newInstance("Root Checker +", "Welcome to Root Checker +", R.drawable.checked, bgcolor));
        addSlide(AppIntroFragment.newInstance("Build Details", "Check your phone build details.", R.drawable.build, bgcolor));
        addSlide(AppIntroFragment.newInstance("Busy Box", "Check if BusyBox is installed properly. ", R.drawable.busybox, bgcolor));
        setImmersiveMode(true);
        setProgressButtonEnabled(true);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Do something when users tap on Skip button.
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        // Do something when users tap on Done button.
        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);
        // Do something when the slide changes.
    }
}