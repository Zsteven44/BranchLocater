package com.szafrani.branchlocater.misc;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;

import java.util.Arrays;

public class LocationPermissionUtil {
    /*
    Tool for checking permissions needed to use app features, as described in the Android docs.
     */
    public static final int REQUEST_PERMISSIONS = 1831;
    public static String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    public static boolean hasPermission(Context context) {
        return ((ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) && (
                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED));
    }

    public static void requestPermission(Activity activity) {
        // We don't have permission so prompt the user
        ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS,
                REQUEST_PERMISSIONS);
    }


    public static boolean onReqPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (permissions.length == 0 || grantResults.length != permissions.length) {
            return false;
        }
        if (requestCode != LocationPermissionUtil.REQUEST_PERMISSIONS) {
            return false;
        }
        boolean granted = false;
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (!Arrays.asList(PERMISSIONS).contains(permission)) {
                return false;
            }
            if (grantResults[i] == PermissionChecker.PERMISSION_GRANTED) {
                granted = true;
            }
        }
        return granted;
    }


}

