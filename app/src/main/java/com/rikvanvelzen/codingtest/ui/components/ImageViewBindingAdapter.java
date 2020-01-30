package com.rikvanvelzen.codingtest.ui.components;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.rikvanvelzen.codingtest.R;
import com.rikvanvelzen.codingtest.common.GlideApp;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class ImageViewBindingAdapter {

    private final static int placeholderResId = R.drawable.ic_image_black_24dp;
    private static final DrawableCrossFadeFactory factory =
            new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build();

    @BindingAdapter({"bind:loadImage"})
    public static void loadImage(ImageView view, String imageUrl) {

        if (imageUrl != null && !imageUrl.isEmpty()) {

            GlideApp.with(view.getContext())
                    .load(imageUrl)
                    .transition(withCrossFade(factory))
                    .apply(new RequestOptions().error(placeholderResId))
                    .into(view);
        } else {
            view.setImageResource(placeholderResId);
        }
    }
}
