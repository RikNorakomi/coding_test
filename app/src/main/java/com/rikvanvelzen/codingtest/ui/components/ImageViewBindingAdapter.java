package com.rikvanvelzen.codingtest.ui.components;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rikvanvelzen.codingtest.R;
import com.rikvanvelzen.codingtest.helpers.GlideApp;

public class ImageViewBindingAdapter {

    private final static int placeholderResId = R.drawable.ic_image_black_24dp;

    @BindingAdapter({"bind:loadImage", "bind:errorDrawable"})
    public static void loadImage(ImageView view, String imageUrl, Drawable errorDrawable) {

        if (imageUrl != null && !imageUrl.isEmpty()) {

            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .placeholder(errorDrawable)
                            .error(errorDrawable))
                    .into(view);

        } else {
            view.setImageDrawable(errorDrawable);
        }
    }

    @BindingAdapter({"bind:loadImage"})
    public static void loadImage(ImageView view, String imageUrl) {

        if (imageUrl != null && !imageUrl.isEmpty()) {

            GlideApp.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions().error(placeholderResId))
                    .into(view);
        } else {
            view.setImageResource(placeholderResId);
        }
    }
}
