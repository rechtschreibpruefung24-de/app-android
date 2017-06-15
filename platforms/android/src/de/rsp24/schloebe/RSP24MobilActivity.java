package de.rsp24.schloebe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.analytics.*;
//import com.google.android.gms.common.*;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import org.apache.cordova.*;

import static de.rsp24.schloebe.RSP24.*;

public class RSP24MobilActivity extends CordovaActivity {
    private final int SPLASH_DISPLAY_LENGTH = 20000;
    //private Tracker t;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //super.init();

        Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());
        if (resultCode == ConnectionResult.SUCCESS) {
            Intent intent = getIntent();
            String action = intent.getAction();
            String type = intent.getType();

            super.setIntegerProperty("loadUrlTimeoutValue", SPLASH_DISPLAY_LENGTH);

            super.setIntegerProperty("splashscreen", R.drawable.splash);

            try {
                //Tracker t = ((RSP24)this.getApplication()).getTracker(TrackerName.APP_TRACKER);
                //t = GoogleAnalytics.getInstance(getApplicationContext()).newTracker(R.xml.global_tracker);
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            if (Intent.ACTION_SEND.equals(action) && type != null) {
                if ("text/plain".equals(type)) {
                    //t.setScreenName("RSP24 Werbe-Version + Shared aus App");
                    handleSendText(intent);
                }
            } else {
                super.clearCache();
                //t.setScreenName("RSP24 Werbe-Version");
                super.loadUrl("http://android.rechtschreibpruefung24.de/?version=" + BuildConfig.VERSION_NAME, SPLASH_DISPLAY_LENGTH);
            }

            //t.send(new HitBuilders.AppViewBuilder().build());
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), 0);
            if (dialog != null) {
                dialog.show();
            }
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            super.clearCache();
            loadUrl("http://android.rechtschreibpruefung24.de/?version=" + BuildConfig.VERSION_NAME + "&text=" + sharedText, SPLASH_DISPLAY_LENGTH);

            Context context = getApplicationContext();
            CharSequence text = getString(R.string.import_success);
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
