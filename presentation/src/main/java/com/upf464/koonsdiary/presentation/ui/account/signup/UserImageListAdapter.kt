package com.upf464.koonsdiary.presentation.ui.account.signup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.upf464.koonsdiary.presentation.databinding.ItemUserImageBinding
import com.upf464.koonsdiary.presentation.model.account.UserImageModel

internal class UserImageListAdapter(
    private val onClickImage: (Int) -> Unit
) : ListAdapter<UserImageModel, UserImageListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<UserImageModel>() {

        override fun areItemsTheSame(oldItem: UserImageModel, newItem: UserImageModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserImageModel, newItem: UserImageModel): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.lifecycleOwner = parent.findViewTreeLifecycleOwner()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position), onClickImage)
    }

    override fun onViewRecycled(holder: ViewHolder) {
        holder.onViewRecycled()
    }

    class ViewHolder(
        private val binding: ItemUserImageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.imageViewUserImage.clipToOutline = true
        }

        fun onBind(item: UserImageModel, onClickImage: (Int) -> Unit) {
            binding.model = item
            binding.imageViewUserImage.setOnClickListener {
                onClickImage(adapterPosition)
            }
        }

        fun onViewRecycled() {
            Glide.with(binding.root.context).clear(binding.imageViewUserImage)
        }
    }
}
