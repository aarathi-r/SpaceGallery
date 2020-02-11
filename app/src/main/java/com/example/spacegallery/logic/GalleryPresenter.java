package com.example.spacegallery.logic;

import com.example.spacegallery.data.GalleryModel;
import com.example.spacegallery.data.ImageData;
import com.example.spacegallery.ui.GalleryFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class GalleryPresenter {
    WeakReference<GalleryFragment> galleryFragment;
    GalleryModel galleryModel;

    public GalleryPresenter(GalleryFragment fragment) {
        this.galleryFragment = new WeakReference<>(fragment);
        this.galleryModel = new GalleryModel(this);
    }

    public void parseJsonFile(String fileName) {

    }

    interface OnImageDataAvailableListener {
        void onImageDataAvailable(List<ImageData> imageDataList);
    }
}
