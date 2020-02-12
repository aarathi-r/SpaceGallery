package com.example.spacegallery.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.spacegallery.data.ImageData;
import com.example.spacegallery.ui.GalleryAdapter.ImageGridViewHolder;
import com.fivehundredpx.greedolayout.GreedoLayoutSizeCalculator.SizeCalculatorDelegate;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<ImageGridViewHolder> implements SizeCalculatorDelegate {
    public static final String IMAGE_DATA_KEY = "image_data";

    private Context context;
    private List<ImageData> imageDataList;
    private double[] mImageAspectRatios;

    public GalleryAdapter(Context context) {
        this.context = context;
    }

    /*
        Update the adapter data
     */
    public void updateImageDataList(List<ImageData> imageDataList) {
        this.imageDataList = imageDataList;
        mImageAspectRatios = new double[imageDataList.size()];
        calculateImageAspectRatios();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        return new ImageGridViewHolder(imageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageGridViewHolder holder, int position) {
        ImageView galleryImage = holder.getGalleryImage();

        final ImageData imageData = imageDataList.get(position);
        galleryImage.getLayoutParams().width = imageData.getWidth();
        galleryImage.getLayoutParams().height = imageData.getHeight();
        galleryImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                launchDetailView(imageData);
            }
        });

        String imageUrl = imageData.getUrl();
        Log.i("Aarathi", "imageData.imageUrl: " + imageUrl);

        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(galleryImage);
    }

    @Override
    public int getItemCount() {
        if (imageDataList != null) {
            return imageDataList.size();
        }
        return 0;
    }

    @Override
    public double aspectRatioForIndex(int index) {
        if (index >= getItemCount()) return 1.0;
        return mImageAspectRatios[getLoopedIndex(index)];
    }

    private int getLoopedIndex(int index) {
        return index % imageDataList.size();
    }

    /*
        Calculate width/height ratio of all images
     */
    private void calculateImageAspectRatios() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        for (int i = 0; i < imageDataList.size(); i++) {
            mImageAspectRatios[i] = imageDataList.get(i).getWidth() / (double) imageDataList.get(i).getHeight();
        }
    }

    /*
        Show the image details
     */
    private void launchDetailView(ImageData imageData) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(IMAGE_DATA_KEY, imageData);
        context.startActivity(intent);
    }

    public class ImageGridViewHolder extends RecyclerView.ViewHolder {

        ImageView galleryImage;

        public ImageGridViewHolder(@NonNull ImageView itemView) {
            super(itemView);
            galleryImage = itemView;
        }

        public ImageView getGalleryImage() {
            return galleryImage;
        }
    }
}
