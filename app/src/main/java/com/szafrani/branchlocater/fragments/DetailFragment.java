package com.szafrani.branchlocater.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szafrani.branchlocater.models.Location;
import com.szafrani.branchlocater.R;

public class DetailFragment extends Fragment{
    Location location;

    public DetailFragment(Location location) {
        this.location = location;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        // Lookup view for data population
        TextView name = (TextView) rootView.findViewById(R.id.item_nameview);
        TextView state = (TextView) rootView.findViewById(R.id.item_stateview);
        TextView address = (TextView) rootView.findViewById(R.id.item_addressview);
        TextView city = (TextView) rootView.findViewById(R.id.item_cityview);
        TextView zip = (TextView) rootView.findViewById(R.id.item_zipview);
        TextView phone = (TextView) rootView.findViewById(R.id.item_phoneview);
        // Populate the data into the template view using the data object
        if (location !=null) {
            if (location.getName() != null) {
                name.setText(location.getName());
            }
            if (location.getState() != null) {
                state.setText(location.getState());
            }
            if (location.getAddress() != null) {
                address.setText(location.getAddress());
            }
            if (location.getCity() != null) {
                city.setText(location.getCity());
            }
            if (location.getZip() != null) {
                zip.setText(location.getZip());
            }
            if (location.getPhone() != null) {
                phone.setText(location.getPhone());
            }
        }

        return rootView;
    }

}
