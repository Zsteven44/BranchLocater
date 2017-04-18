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
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.szafrani.branchlocater.Utils.verifyPermissions;

public class HomeFragment extends Fragment implements Callback {
    private final String TAG = getClass().getSimpleName();
    private ArrayList<com.szafrani.branchlocater.Location> branchList = new ArrayList<com.szafrani.branchlocater.Location>();
    private BranchAdapter adapter;
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
                // Create new fragment and transaction
                DetailFragment detailFragment = new DetailFragment(branchList.get(position));
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //transaction.show(newFragment);

                // Replace whatever is in the fragment_container view with this fragment,
                // and add the transaction to the back stack
                transaction.replace(R.id.container, detailFragment);
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

        OkHttpClient client = new OkHttpClient();;
        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://m.chase.com/PSRWeb/location/list.action").newBuilder();
        urlBuilder.addQueryParameter("lat", Double.toString(latitude));
        urlBuilder.addQueryParameter("lng", Double.toString(longitude));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this);
    }


    public void setBranchList(ArrayList<com.szafrani.branchlocater.Location> newBranchList) {
        branchList = newBranchList;
        adapter.locations = branchList;
        Log.e("setBranchList", "the current BranchList: "+ branchList.toString());

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                for (int i =0; i< branchList.size(); i++) {
                    adapter.add(branchList.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        });
    }



    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        String jsonString =  response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("locations");
            Log.e(TAG, "response to string: "+ jsonArray.toString());
            ArrayList<com.szafrani.branchlocater.Location> locationList = new ArrayList<>();

            String state;
            String type;
            String address;
            String city;
            String zip;
            String name;
            String bank;
            String phone;

            for(int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject currentJSON = jsonArray.getJSONObject(i);
                    state = currentJSON.getString("state");
                    type = currentJSON.getString("locType");
                    address = currentJSON.getString("address");
                    city = currentJSON.getString("city");
                    zip = currentJSON.getString("zip");
                    name = currentJSON.getString("name");
                    bank= currentJSON.getString("bank");
                    phone = currentJSON.getString("phone");
                    Log.e(TAG, "adding new location: "+ state+ ", " + type + ", " + address);

                    locationList.add(new com.szafrani.branchlocater.Location(state, type, address, city, zip, name, bank, phone));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            setBranchList(locationList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
