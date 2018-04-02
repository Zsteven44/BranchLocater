package com.szafrani.branchlocater.misc;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.szafrani.branchlocater.activities.MainActivity;

import static android.content.ContentValues.TAG;

/**
 * Created by stevenzafrani on 4/1/18.
 */

public class LocationFinder {
    /*
    The purpose of this class is to return a Location object that has the user's longitude and latitude.

     */
    MainActivity activity;
    Location myLocation;

    public LocationFinder(MainActivity activity) {
        this.activity = activity;
    }

    public Location fetchLocation(final boolean requestIfNeeded) {

        // Permission check
        if (!LocationPermissionUtil.hasPermission(activity)) {
            Log.e(TAG, "Missing BranchLocation, Permission not found.");
            LocationPermissionUtil.requestPermission(activity);
            return null;
        }
        // Register the listener with the BranchLocation Manager to receive location updates
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                myLocation = location;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                // Nothing to be done
            }

            @Override
            public void onProviderEnabled(String s) {
                // Nothing to be done
            }

            @Override
            public void onProviderDisabled(String s) {
                // Nothing to be done
            }
        };
        final LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        Boolean isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        Boolean isNetworkEnabled = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        try {
            if (!isGPSEnabled && !isNetworkEnabled) {
                Toast.makeText(activity, "No network provided", Toast.LENGTH_SHORT).show();
            } else {
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            5,
                            1, locationListener);
                    Log.d("Network", "Network Enabled");
                        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                } else {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                                5,
                                1, locationListener);
                        Log.d("GPS", "GPS Enabled");
                        myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }
            }
            Log.e(TAG, "Location latitude: " + myLocation.getLatitude());
        } catch ( SecurityException e) {
            e.printStackTrace();
        }

        return myLocation;

    }
}
