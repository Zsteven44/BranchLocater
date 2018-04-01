package com.szafrani.branchlocater.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.szafrani.branchlocater.R;
import com.szafrani.branchlocater.models.BranchLocation;

import java.util.ArrayList;


public class BranchAdapter extends ArrayAdapter {
    private final Context context;
    public ArrayList<BranchLocation> branchLocations = null;

    public BranchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        BranchLocation branchLocation = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview, parent, false);
        }

        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.item_nameview);
        TextView state = (TextView) convertView.findViewById(R.id.item_stateview);
        TextView address = (TextView) convertView.findViewById(R.id.item_addressview);
        TextView city = (TextView) convertView.findViewById(R.id.item_cityview);
        TextView zip = (TextView) convertView.findViewById(R.id.item_zipview);
        TextView phone = (TextView) convertView.findViewById(R.id.item_phoneview);
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
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public BranchLocation getItem(int position) {
        if (branchLocations != null) {
            return branchLocations.get(position);
        }
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }




}
