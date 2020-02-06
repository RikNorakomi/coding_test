/*
 * Created by Rik van Velzen, Norakomi Software Development.
 * Copyright (c) 2020. All rights reserved
 * Last modified 1/30/20 2:24 PM
 */
package com.rikvanvelzen.codingtest.ui.components.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.rikvanvelzen.codingtest.R
import com.rikvanvelzen.codingtest.common.GlideApp

class ImageViewBindingAdapter {

    companion object {

        private const val placeholderResId = R.drawable.ic_image_black_24dp
        private val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        @JvmStatic
        @BindingAdapter("bind:loadImage")
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (imageUrl != null && !imageUrl.isEmpty()) {
                GlideApp.with(view.context)
                        .load(imageUrl)
                        .transition(DrawableTransitionOptions.withCrossFade(factory))
                        .apply(RequestOptions().error(placeholderResId))
                        .into(view)
            } else {
                view.setImageResource(placeholderResId)
            }
        }
    }
}