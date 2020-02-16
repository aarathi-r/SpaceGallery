package com.example.spacegallery.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.spacegallery.R;
import com.example.spacegallery.data.ImageData;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback;

import java.util.List;

public class DetailFragment extends Fragment {

    private List<ImageData> imageDataList;
    private int position;

    public DetailFragment(List<ImageData> imageDataList, int position) {
        this.imageDataList = imageDataList;
        this.position = position;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Aarathi", "DetailFragment onCreateView");
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageData imageData = imageDataList.get(position);
        ImageView imageView = view.findViewById(R.id.image_display);
        LinearLayout detailView = view.findViewById(R.id.image_detail_view);
        final BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(detailView);

        Button slideUp = view.findViewById(R.id.slide_up);

        sheetBehavior.setPeekHeight((int) getContext().getResources().getDimension(R.dimen.collapsed_height));
        slideUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        sheetBehavior.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                Log.i("Aarathi","onStateChanged");
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
                Log.i("Aarathi","onSlide");
            }
        });

        Glide.with(getContext())
                .load(imageData.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return view;
    }
}
