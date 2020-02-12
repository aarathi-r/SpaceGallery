package com.example.spacegallery.data;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.example.spacegallery.logic.OnImageLoadedListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GalleryLoader extends AsyncTask<String,Void, List<ImageData>> {
    private OnImageLoadedListener listener;

    public GalleryLoader(OnImageLoadedListener listener) {
        this.listener = listener;
//        this.parsedJsonString = new StringBuilder();
    }

    @Override
    protected List<ImageData> doInBackground(String... jsonFormat) {
        String json = jsonFormat[0];
        Log.i("Aarathi", "jsonFormat: " + json);
        List<ImageData> imageDataList = new ArrayList<>();

        /*
        Generate the list of ImageData objects
         */
        try {
            JSONArray jsonArray = new JSONArray(json);
            Gson gson = new Gson();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ImageData imageData = gson.fromJson(jsonObject.toString(), ImageData.class);
                Log.i("Aarathi",(i+1) + " : " + imageData.getCopyright());

                URL url;
                Bitmap bitmap = null;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                try {
                    url = new URL(imageData.getUrl());
                    if (url != null) {
                        bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream(), null, options);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("Aarathi","width: " + options.outWidth + " height: " + options.outHeight);
                
                imageData.setWidth(options.outWidth);
                imageData.setHeight(options.outHeight);

                imageDataList.add(imageData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return imageDataList;
    }

    @Override
    protected void onPostExecute(List<ImageData> imageDataList) {
        super.onPostExecute(imageDataList);
        listener.onImageLoaded(imageDataList);
    }
}
