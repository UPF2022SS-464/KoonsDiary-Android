package com.upf464.koonsdiary.presentation.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("app:submitList")
internal fun <T, VH : RecyclerView.ViewHolder> RecyclerView.submitList(list: List<T>?) {
    (adapter as? ListAdapter<T, VH>)?.submitList(list) {
        invalidateItemDecorations()
    }
}
