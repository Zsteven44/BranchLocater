package com.szafrani.branchlocater.fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.szafrani.branchlocater.R;
import com.szafrani.branchlocater.activities.MainActivity;
import com.szafrani.branchlocater.adapters.BranchAdapter;
import com.szafrani.branchlocater.misc.BranchFinder;
import com.szafrani.branchlocater.misc.LocationFinder;
import com.szafrani.branchlocater.misc.LocationPermissionUtil;
import com.szafrani.branchlocater.models.BranchLocation;

import java.util.ArrayList;


public class HomeFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    private ArrayList<BranchLocation> branchList = new ArrayList<BranchLocation>();
    private BranchAdapter adapter;
    private ListView listView;
    private LocationFinder locationFinder;

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
                // Create new fragment and transaction
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.container, DetailFragment.newInstance(branchList.get(position).getBranchJson().toString()));
                transaction.addToBackStack(null);
                transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.commit();
            }

        });
        return rootView;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e(TAG, "Running fetchLocation.");
        retrieveLocations();

    }

    public void retrieveLocations() {
        if (locationFinder == null) locationFinder = new LocationFinder((MainActivity) getActivity());
        Location location = locationFinder.fetchLocation(true);
        Log.e(TAG, "Location latitude and longitude: " + location.getLatitude() + ", " + location.getLongitude());
        if (location != null) new BranchFinder(this).fetchData(location);
    }



    public void setBranchList(ArrayList<BranchLocation> newBranchList) {
        branchList = newBranchList;
        adapter.branchLocations = branchList;
        ( getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                for (int i = 0; i < branchList.size(); i++) {
                    adapter.add(branchList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationPermissionUtil.onReqPermissionsResult(requestCode, permissions, grantResults);
        retrieveLocations();

    }
}
