package com.szafrani.branchlocater.misc;

import android.location.Location;
import android.util.Log;

import com.szafrani.branchlocater.fragments.HomeFragment;
import com.szafrani.branchlocater.models.BranchLocation;

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

/**
 * Created by stevenzafrani on 4/1/18.
 */

public class BranchFinder implements Callback{
    /*
    The purpose of this class is to send a request to the Chase endpoint with params from user's Location.
    The response object's json will contain all nearby banks, which we parse into a BranchLocation model
    and return to the fragment to display.
     */
    private final String TAG = getClass().getSimpleName();
    private final HomeFragment homeFragment;

    public BranchFinder(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
    }

    public void fetchData(Location location) {
        try {
            Log.e(TAG, "Received location:" + location.toString());
            Log.e(TAG, "Running fetchData.");
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            OkHttpClient client = new OkHttpClient();
            HttpUrl.Builder urlBuilder = HttpUrl.parse("https://m.chase.com/PSRWeb/location/list.action").newBuilder();
            urlBuilder.addQueryParameter("lat", Double.toString(latitude));
            urlBuilder.addQueryParameter("lng", Double.toString(longitude));
            String url = urlBuilder.build().toString();

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            client.newCall(request).enqueue(this);
        }catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        // on successful response, convert response to json string.
        // create a list from json of the branchLocations, along with important information.
        String jsonString = response.body().string();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray jsonArray = jsonObject.getJSONArray("locations");
            Log.e(TAG, "response to string: " + jsonArray.toString());
            ArrayList<BranchLocation> branchLocationList = new ArrayList<>();

            String state;
            String type;
            String address;
            String city;
            String zip;
            String name;
            String bank;
            String phone;

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject currentJSON = jsonArray.getJSONObject(i);
                    state = currentJSON.getString("state");
                    type = currentJSON.getString("locType");
                    address = currentJSON.getString("address");
                    city = currentJSON.getString("city");
                    zip = currentJSON.getString("zip");
                    name = currentJSON.getString("name");
                    bank = currentJSON.getString("bank");
                    phone = currentJSON.getString("phone");
                    Log.e(TAG, "adding new location: " + state + ", " + type + ", " + address);

                    branchLocationList.add(new BranchLocation(state, type, address, city, zip, name, bank, phone, currentJSON));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            homeFragment.setBranchList(branchLocationList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
