package com.rootcheckerplus.root;

/**
 * Created by Sri Harrsha on 29-Aug-16.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;


public class RootCheckingProgressDialog extends DialogFragment
{

    public static final String FRAGMENT_TAG = "Progress";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setCancelable(false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setTitle("Checking Root Status");
        dialog.setMessage("checking...");
        dialog.setIndeterminate(true);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            }, 3000);

        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        return dialog;
    }


}