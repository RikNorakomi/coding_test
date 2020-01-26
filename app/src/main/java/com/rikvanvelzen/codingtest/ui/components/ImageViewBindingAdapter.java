package com.rikvanvelzen.codingtest.ui.components;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.rikvanvelzen.codingtest.R;

public class ImageViewBindingAdapter {

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

            Glide.with(view.getContext())
                    .load(imageUrl)
                    .apply(new RequestOptions()
                            .fitCenter()

                            .error(R.drawable.ic_image_black_24dp))

//                    .apply(RequestOptions.circleCropTransform())
                    .into(view);

        } else {
            view.setImageResource(R.drawable.ic_image_black_24dp);
        }
    }
}
