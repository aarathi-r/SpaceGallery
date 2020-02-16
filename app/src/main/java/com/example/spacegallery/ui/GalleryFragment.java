package com.example.spacegallery.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.spacegallery.R;
import com.example.spacegallery.data.ImageData;
import com.example.spacegallery.logic.GalleryPresenter;
import com.example.spacegallery.util.Utils;
import com.fivehundredpx.greedolayout.GreedoLayoutManager;
import com.fivehundredpx.greedolayout.GreedoSpacingItemDecoration;

import java.util.List;

public class GalleryFragment extends Fragment {

    private static final String JSON_FILE_NAME = "data.json";

    private GalleryPresenter galleryPresenter;

    private ImageView loadingGif;
    private RecyclerView imageGrid;
    private GalleryAdapter galleryAdapter;

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
        showLoadingGif(view);
        initImageGridView(view);
        return view;
    }

    /*
        Initialize the recycler-view for image grid
     */
    private void initImageGridView(View view) {
        imageGrid = view.findViewById(R.id.image_grid);

        galleryAdapter = new GalleryAdapter(getContext());
        imageGrid.setAdapter(galleryAdapter);

        final GreedoLayoutManager layoutManager = new GreedoLayoutManager(galleryAdapter);
        layoutManager.setMaxRowHeight(Utils.dpToPx(150, getContext()));
        imageGrid.setLayoutManager(layoutManager);

        int spacing = Utils.dpToPx(2, getContext());
        imageGrid.addItemDecoration(new GreedoSpacingItemDecoration(spacing));

        parseJsonFile(JSON_FILE_NAME);
    }

    /*
        Parsing json file to list of ImageData objects
     */
    private void parseJsonFile(String fileName) {
        galleryPresenter.parseJsonFile(fileName);
    }

    /*
        Callback from presenter when ImageData objects list is available to show in adapter
     */
    public void updateAdapterData(List<ImageData> imageDataList) {
        galleryAdapter.updateImageDataList(imageDataList);
        if (!((GalleryActivity) getActivity()).isNetworkConnected()) {
            ((GalleryActivity) getActivity()).showNoConnectionToast();
        }
        if (getGifView() != null) {
            getGifView().setVisibility(View.GONE);
        }
    }

    private void showLoadingGif(View view) {
        loadingGif = view.findViewById(R.id.loading);
        Glide.with(getContext())
                .load(R.drawable.loading_indicator)
                .into(loadingGif);

    }

    public ImageView getGifView() {
        return loadingGif;
    }
}