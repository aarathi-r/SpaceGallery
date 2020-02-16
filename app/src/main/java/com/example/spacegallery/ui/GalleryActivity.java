package com.example.spacegallery.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.spacegallery.R;

public class GalleryActivity extends AppCompatActivity {

    private static final int GRANTED = PackageManager.PERMISSION_GRANTED;
    private static final String[] permissions = {permission.INTERNET,
            permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE};
    private GalleryFragment galleryFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(permission.INTERNET) != GRANTED ||
                checkSelfPermission(permission.WRITE_EXTERNAL_STORAGE) != GRANTED ||
                checkSelfPermission(permission.READ_EXTERNAL_STORAGE) != GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, 100);
        } else if (isNetworkConnected()){
            initializeGalleryFragment();
        } else {
            showNoConnectionToast();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && (grantResults[0] == GRANTED)
                && grantResults[1] == GRANTED && grantResults[2] == GRANTED && isNetworkConnected()) {
            initializeGalleryFragment();
        } else if (!isNetworkConnected()){
            showNoConnectionToast();
        } else {
            Toast.makeText(this, R.string.permission_toast, Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeGalleryFragment() {
        if (galleryFragment == null) {
            galleryFragment = GalleryFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.image_gallery, galleryFragment);
            transaction.commit();
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
        }
        return false;
    }

    public void showNoConnectionToast() {
        Toast.makeText(this, R.string.no_connection_toast, Toast.LENGTH_SHORT).show();
        finish();
    }
}
