package com.example.spacegallery.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.spacegallery.logic.OnImageLoadedListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryLoader extends AsyncTask<String,Void, List<ImageData>> {
    private OnImageLoadedListener listener;

    public GalleryLoader(OnImageLoadedListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<ImageData> doInBackground(String... jsonFormat) {
        String json = jsonFormat[0];
        Log.i("Aarathi", "jsonFormat: " + json);
        java.util.List<ImageData> imageDataList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            Gson gson = new Gson();
            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ImageData imageData = gson.fromJson(jsonObject.toString(), ImageData.class);
                Log.i("Aarathi",(i+1) + " : " + imageData.getCopyright());
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
