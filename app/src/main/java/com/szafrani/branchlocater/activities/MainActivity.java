package com.szafrani.branchlocater.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.szafrani.branchlocater.R;
import com.szafrani.branchlocater.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {
    HomeFragment homeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, homeFragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (homeFragment == null) {
            return;
        }
        homeFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        this.homeFragment = null;
        super.onDestroy();
    }
}
