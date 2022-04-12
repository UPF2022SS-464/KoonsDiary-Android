package com.upf464.koonsdiary.presentation.ui.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadImage")
internal fun ImageView.loadImage(src: String?) {
    Glide.with(context)
        .load(src ?: return)
        .into(this)
}
