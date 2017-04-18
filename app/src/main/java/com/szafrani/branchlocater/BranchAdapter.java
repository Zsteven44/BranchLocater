package com.szafrani.branchlocater;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class BranchAdapter extends ArrayAdapter {
    private final Context context;
    public ArrayList<Location> locations = null;

    public BranchAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Location location = getItem(position);
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
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public Location getItem(int position) {
        if (locations != null) {
            Location item = locations.get(position);
            return item;
        }
        return null;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }




}
