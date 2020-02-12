package com.example.spacegallery.data;

import com.example.spacegallery.logic.GalleryPresenter;
import com.example.spacegallery.logic.OnImageLoadedListener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GalleryModel {
    private GalleryPresenter galleryPresenter;

    public GalleryModel(GalleryPresenter presenter) {
        this.galleryPresenter = presenter;
    }

    /*
        Read the data.json file
     */
    public String loadJsonData(String jsonFileName) {
        String jsonData = null;
        try {
            InputStream in = galleryPresenter.getView().getContext().getAssets().open(jsonFileName);
            int length = in.available();
            byte[] data = new byte[length];
            in.read(data);
            in.close();
            jsonData = new String(data, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    /*
        Convert json format to Java object
     */
    public void processJsonToJava(String jsonFormat, OnImageLoadedListener listener) {
        GalleryLoader asyncTask = new GalleryLoader(listener);
        asyncTask.execute(jsonFormat);
    }
}
