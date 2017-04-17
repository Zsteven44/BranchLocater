package com.szafrani.branchlocater;


import android.support.annotation.NonNull;
import android.util.Log;

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

public class Service implements Callback {
    private final String TAG = getClass().getSimpleName();
    private static String HOST_URL = "https://m.chase.com/PSRWeb/location/list.action";

    private OkHttpClient client = new OkHttpClient();;

    public Service() {
    }

    public void runLocater(@NonNull double latitude,
                           @NonNull double longitude) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(HOST_URL).newBuilder();
        urlBuilder.addQueryParameter("lat", Double.toString(latitude));
        urlBuilder.addQueryParameter("lng", Double.toString(longitude));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(this);
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
            ArrayList<Location> locationList = new ArrayList<>();

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

                    locationList.add(new Location(state, type, address, city, zip, name, bank, phone));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            HomeFragment.setBranchList(locationList);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
