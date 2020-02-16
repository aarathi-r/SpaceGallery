package com.example.spacegallery.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.spacegallery.data.ImageData;

import java.util.List;

public class DetailAdapter extends FragmentPagerAdapter {

    List<ImageData> imageDataList;

    public DetailAdapter(@NonNull FragmentManager fm, List<ImageData> imageDataList) {
        super(fm);
        this.imageDataList = imageDataList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new DetailFragment(imageDataList, position) ;
    }

    @Override
    public int getCount() {
        if (imageDataList != null) {
            return imageDataList.size();
        }
        return 0;
    }
}
