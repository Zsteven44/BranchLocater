package com.szafrani.branchlocater.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szafrani.branchlocater.models.BranchLocation;
import com.szafrani.branchlocater.R;

public class DetailFragment extends Fragment{
    BranchLocation branchLocation;

    public DetailFragment(BranchLocation branchLocation) {
        this.branchLocation = branchLocation;

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
        if (branchLocation !=null) {
            if (branchLocation.getName() != null) {
                name.setText(branchLocation.getName());
            }
            if (branchLocation.getState() != null) {
                state.setText(branchLocation.getState());
            }
            if (branchLocation.getAddress() != null) {
                address.setText(branchLocation.getAddress());
            }
            if (branchLocation.getCity() != null) {
                city.setText(branchLocation.getCity());
            }
            if (branchLocation.getZip() != null) {
                zip.setText(branchLocation.getZip());
            }
            if (branchLocation.getPhone() != null) {
                phone.setText(branchLocation.getPhone());
            }
        }

        return rootView;
    }

}
