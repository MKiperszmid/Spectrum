package com.example.dh.tpmusicagrupo3.Controller;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.dh.tpmusicagrupo3.R;

/**
 * Created by DH on 18/6/2018.
 */

public class GlideController {
    public static void loadImageFade(View view, String url, ImageView imageView) {
        GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public static void loadImages(View view, String url, ImageView imageView) {
        GlideApp.with(view)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .into(imageView);
    }
}
