package de.rsp24.schloebe;

import android.app.Application;
import com.google.android.gms.analytics.*;

import java.util.HashMap;

public class RSP24 extends Application {
    //private static final String PROPERTY_ID = "UA-816979-5";

    public enum TrackerName {
        APP_TRACKER,
        GLOBAL_TRACKER
    }

    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();

    public RSP24() {
        super();
    }

    synchronized Tracker getTracker(TrackerName trackerId) {
        //if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            /*Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(PROPERTY_ID)
                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(R.xml.global_tracker)
                    : analytics.newTracker(R.xml.global_tracker);*/
            Tracker t = analytics.newTracker(R.xml.global_tracker);
                    t.enableAdvertisingIdCollection(true);
            mTrackers.put(trackerId, t);
        //}
        return mTrackers.get(trackerId);
    }
}