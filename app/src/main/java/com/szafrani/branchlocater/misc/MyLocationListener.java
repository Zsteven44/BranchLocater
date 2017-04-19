package com.szafrani.branchlocater.misc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener{
    private final String TAG = getClass().getSimpleName();
    @Override
    public void onLocationChanged(Location location) {
        Log.e(TAG, "Received changed location:" + location.toString());
        Log.e(TAG, "Running fetchData on changed location.");



    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
