package com.example.spacegallery.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest.permission;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.spacegallery.R;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkSelfPermission(permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission.INTERNET}, 100);
        } else {
            Toast.makeText(this, "Internet permission already there", Toast.LENGTH_SHORT).show();
            initializeGalleryFragment();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            initializeGalleryFragment();
        } else{
            Toast.makeText(this, "No Internet", Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeGalleryFragment() {
        GalleryFragment galleryFragment = GalleryFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.image_gallery, galleryFragment);
        transaction.commit();
    }
}
