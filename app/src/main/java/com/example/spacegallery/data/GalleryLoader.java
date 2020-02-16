package com.example.spacegallery.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.spacegallery.logic.GalleryPresenter;
import com.example.spacegallery.logic.OnImageLoadedListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GalleryLoader extends AsyncTask<String,Void, List<ImageData>> {
    private OnImageLoadedListener listener;
    private GalleryPresenter presenter;

    public GalleryLoader(OnImageLoadedListener listener, GalleryPresenter presenter) {
        this.listener = listener;
        this.presenter = presenter;
    }

    @Override
    protected List<ImageData> doInBackground(String... jsonFormat) {
        String json = jsonFormat[0];
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

                if (imageData.getWidth() == 0 || imageData.getHeight() == 0) {
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

                    imageData.setWidth(options.outWidth);
                    imageData.setHeight(options.outHeight);

                    jsonObject.put("width", options.outWidth);
                    jsonObject.put("height", options.outHeight);

                    jsonArray.put(i, jsonObject);
                }
                imageDataList.add(imageData);
            }
            writeJsonFile(jsonArray.toString());
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

    @Override
    protected void onCancelled() {
        super.onCancelled();
        listener.onImageLoaded(null);
    }

    public void writeJsonFile(String parsedJson) {
        Context applicationContext = presenter.getView().getActivity().getApplicationContext();
        File fileJson = new File(applicationContext.getExternalFilesDir("/com.example.spacegallery"), "parsed_data.json");

        BufferedWriter bufferedWriter = null;
        try {
            fileJson.createNewFile();

            FileWriter fileWriter = new FileWriter(fileJson);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(parsedJson);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}