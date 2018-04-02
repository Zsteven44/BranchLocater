package com.szafrani.branchlocater.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.szafrani.branchlocater.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class DetailFragment extends Fragment{
    /*
    Displays greater detail for a specific branch location.
     */
    private final String TAG = getClass().getSimpleName();
    private static final String BRANCH_JSON_AS_STRING = "json_as_string";
    JSONObject branchJson;

    public DetailFragment() {}

    public static final DetailFragment newInstance(String branchJson) {
        /*
        Generates a DetailFragment and attaches the branch model's json to its arguments.
         */
        DetailFragment detailFragment = new DetailFragment();
        try {
            detailFragment.branchJson = new JSONObject(branchJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString(BRANCH_JSON_AS_STRING, branchJson);
        detailFragment.setArguments(bundle);
        return detailFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        /*
        Retrieves the branch objects json to generate the views.
         */
        super.onCreate(savedInstanceState);
        try {
            this.branchJson = new JSONObject(getArguments().getString(BRANCH_JSON_AS_STRING));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.layout);
        Iterator keys = branchJson.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            layout.addView(generateView(key, inflater));
        }

        return rootView;
    }

    private View generateView(String key, LayoutInflater inflater){
        /*
        This takes the fragment_detail_item.xml and inflates it with the data from a single key in
        branchJson.  If it comes across a JSONArray (such as 'hours' or 'services') it will append
        the array of values to the valueTextView with new lines.

        This returns a view for every "key" in branchJson.
         */
        View view;
        TextView keyView;
        TextView valueView;
        try {
            Log.e(TAG, "Key: " + key + ", Value: " + branchJson.get(key));
            view = inflater.inflate(R.layout.fragment_detail_item, null);
            keyView = (TextView) view.findViewById(R.id.branch_detail_key);
            keyView.setText(key + ":");
            valueView = ((TextView) view.findViewById(R.id.branch_detail_value));
            if (!(branchJson.get(key) instanceof JSONArray)) {
                valueView.setText(branchJson.get(key).toString());
            } else {
                JSONArray jsonArray = branchJson.getJSONArray(key);
                for (int i =0; i < jsonArray.length(); i++ ) {
                    valueView.append("\n" + jsonArray.getString(i));
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return view;
    }

}
