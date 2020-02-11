package com.example.spacegallery.logic;

import com.example.spacegallery.data.ImageData;

import java.util.List;

public interface OnImageLoadedListener {
    void onImageLoaded(List<ImageData> imageDataList);
}
