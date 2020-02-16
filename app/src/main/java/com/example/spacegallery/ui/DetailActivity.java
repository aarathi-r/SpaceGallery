package com.example.spacegallery.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.spacegallery.R;
import com.example.spacegallery.data.ImageData;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private List<ImageData> imageDataList;
    private int position;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageDataList = (ArrayList<ImageData>) getIntent().getSerializableExtra(GalleryAdapter.IMAGE_DATA_LIST_KEY);
        position = getIntent().getIntExtra(GalleryAdapter.IMAGE_POSITION_KEY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (imageDataList != null && imageDataList.size() != 0) {
            initializeDetailAdapter(position);
        }
    }

    private void initializeDetailAdapter(int position) {
        viewPager = findViewById(R.id.detail_page_holder);
        DetailAdapter adapter = new DetailAdapter(getSupportFragmentManager(), imageDataList);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}