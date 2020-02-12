package com.example.spacegallery.logic;

import com.example.spacegallery.data.GalleryModel;
import com.example.spacegallery.data.ImageData;
import com.example.spacegallery.ui.GalleryFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class GalleryPresenter {
    private WeakReference<GalleryFragment> galleryFragment;
    private GalleryModel galleryModel;

    public GalleryPresenter(GalleryFragment fragment) {
        this.galleryFragment = new WeakReference<>(fragment);
        this.galleryModel = new GalleryModel(this);
    }

    public GalleryFragment getView() {
        return galleryFragment.get();
    }

    /*
        Parse the data.json file
     */
    public void parseJsonFile(String fileName) {
        OnImageLoadedListener callback = new OnImageLoadedListener() {

            @Override
            public void onImageLoaded(List<ImageData> imageDataList) {
                galleryFragment.get().updateAdapterData(imageDataList);
            }
        };
        String jsonFormat = galleryModel.loadJsonData(fileName);
        galleryModel.processJsonToJava(jsonFormat, callback);
    }
}
