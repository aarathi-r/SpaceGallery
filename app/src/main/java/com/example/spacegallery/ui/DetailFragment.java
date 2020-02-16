package com.example.spacegallery.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
        final View mainView = inflater.inflate(R.layout.fragment_detail, container, false);

        ImageData imageData = imageDataList.get(position);

        final ImageView imageView = mainView.findViewById(R.id.image_display);
        final int imageTop = imageView.getTop();

        RelativeLayout detailView = mainView.findViewById(R.id.image_detail_view);
        final BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(detailView);
        sheetBehavior.setPeekHeight((int) getContext().getResources().getDimension(R.dimen.collapsed_height));

        sheetBehavior.setBottomSheetCallback(new BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                if(sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    slideImageToTop(imageView, mainView, imageTop);
                } else if(sheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    slideImageToCentre(imageView, mainView, imageTop);
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) { }
        });

        ImageView slideUp = mainView.findViewById(R.id.slide_up);
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

        TextView title = mainView.findViewById(R.id.image_title);
        String imageTitle = imageData.getTitle();
        if (imageTitle != null) {
            title.setVisibility(View.VISIBLE);
            title.setText(imageTitle);
        }

        TextView copyright = mainView.findViewById(R.id.image_copyright);
        String imageCopyright = imageData.getCopyright();
        if (imageCopyright != null) {
            copyright.setVisibility(View.VISIBLE);
            copyright.setText(imageCopyright);
        }

        TextView date = mainView.findViewById(R.id.image_date);
        String imageDate = imageData.getDate();
        if (imageDate != null) {
            date.setVisibility(View.VISIBLE);
            date.setText(imageDate);
        }

        TextView explanation = mainView.findViewById(R.id.image_explanation);
        String imageExplanation = imageData.getExplanation();
        if (imageExplanation != null) {
            explanation.setVisibility(View.VISIBLE);
            explanation.setText(imageExplanation);
        }

        Glide.with(getContext())
                .load(imageData.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        return mainView;
    }

    private void slideImageToTop(View view, View parent, int startPosition) {
        final ImageView image = (ImageView) view;
        Animation animation = new TranslateAnimation(0, 0, startPosition, parent.getTop());
        animation.setDuration(100);
        image.startAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) image.getLayoutParams();
                lp.gravity = Gravity.TOP;
                image.setLayoutParams(lp);
            }
        });
    }

    private void slideImageToCentre(View view, View parent, int endPosition) {
        final ImageView image = (ImageView) view;
        Animation animation = new TranslateAnimation(0, 0, parent.getTop(), endPosition);
        animation.setDuration(1);
        image.startAnimation(animation);

        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) { }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) image.getLayoutParams();
                lp.gravity = Gravity.CENTER;
                image.setLayoutParams(lp);
            }
        });
    }
}