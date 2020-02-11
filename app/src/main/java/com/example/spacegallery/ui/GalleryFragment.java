package com.example.spacegallery.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacegallery.R;
import com.example.spacegallery.logic.GalleryPresenter;

public class GalleryFragment extends Fragment {

    private static final String JSON_FILE_NAME = "data.json";

    private GalleryPresenter galleryPresenter;

    private RecyclerView imageGrid;

    /*
        Create new fragment instance
     */
    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        galleryPresenter = new GalleryPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        initImageGridView(view);
        parseJsonFile(JSON_FILE_NAME);
        return view;
    }

    /*
        Initialize the recycler-view for image grid
     */
    private void initImageGridView(View view) {
        imageGrid = view.findViewById(R.id.image_grid);
    }

    private void parseJsonFile(String fileName) {
        galleryPresenter.parseJsonFile(fileName);

    }


}
