package com.gmbett.storagepermissionissue;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Auxiliary class used to handle permission requests.
 */
public class PermissionUtils {

    private static final String READ_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static final String WRITE_STORAGE_PERMISSION = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final String[] STORAGE_PERMISSIONS = {READ_STORAGE_PERMISSION, WRITE_STORAGE_PERMISSION};

    public static boolean checkStoragePermissions(final AppCompatActivity activity, final int requestCode) {
        if (areAllPermissionsCurrentlyGranted(activity, STORAGE_PERMISSIONS)) {
            return true;
        }

        ActivityCompat.requestPermissions(activity, STORAGE_PERMISSIONS, requestCode);
        return false;
    }

    public static boolean werePermissionsGranted(final int[] grantResults) {
        if (grantResults.length == 0) {
            return false;
        }

        for (int result : grantResults) {
            if (!isGrantedResult(result)) {
                return false;
            }
        }

        return true;
    }

    public static void openPermissionSettings(final Activity activity, final int requestCode) {
        final Uri uri = Uri.fromParts("package", activity.getPackageName(), null);

        final Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(uri);

        activity.startActivityForResult(intent, requestCode);
    }

    private static boolean areAllPermissionsCurrentlyGranted(final Context context, final String[] permissions) {
        for (final String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    private static boolean isGrantedResult(final int grantResult) {
        return grantResult == PERMISSION_GRANTED;
    }
}
