package com.example.spacegallery.ui;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.spacegallery.ui.GalleryAdapter.ImageGridViewHolder;

public class GalleryAdapter extends RecyclerView.Adapter<ImageGridViewHolder> {
    Context context;

    public GalleryAdapter(Context context) {
        this.context = context;
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

    }

    @Override
    public int getItemCount() {
        return 0;
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
