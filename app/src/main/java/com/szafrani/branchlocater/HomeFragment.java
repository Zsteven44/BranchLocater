package com.szafrani.branchlocater;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.szafrani.branchlocater.Utils.verifyPermissions;

public class HomeFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    private static ArrayList<com.szafrani.branchlocater.Location> branchList = new ArrayList<com.szafrani.branchlocater.Location>();
    private static BranchAdapter adapter;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        adapter = new BranchAdapter(getActivity(), R.layout.listview);
        listView = (ListView) rootView.findViewById(R.id.listview);
        adapter.add(branchList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
            }

        });

        return rootView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "Running fetchLocation.");
        verifyPermissions(getActivity());
        fetchLocation();

    }


    public void fetchLocation() {

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new MyLocationListener();

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fetchData(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
        Log.e(TAG, "GPS Enabled");
    }


    public void fetchData(Location location) {
        Log.e(TAG, "Received location:" + location.toString());
        Log.e(TAG, "Running fetchData.");
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        Service service = new Service();
        service.runLocater(latitude, longitude);

    }


    public void updateListView(){

    }

    public static void setBranchList(ArrayList<com.szafrani.branchlocater.Location> newBranchList) {
        branchList = newBranchList;
        adapter.clear();
        adapter.locations = branchList;
        for (int i =0; i< branchList.size(); i++) {
            adapter.add(branchList.get(i));
        }
        adapter.notifyDataSetChanged();
        Log.e("setBranchList", "the current BranchList: "+ branchList.toString());

    }
}
