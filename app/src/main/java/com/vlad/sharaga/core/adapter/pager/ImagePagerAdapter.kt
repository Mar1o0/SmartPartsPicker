package com.vlad.sharaga.core.adapter.pager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.databinding.ItemImageBinding
import kotlinx.parcelize.Parcelize

@Parcelize
data class ImageItem(
    val imageUrl: String
) : Item

class ImagePagerAdapter(
    private val imageUrls: List<String>
) : RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = imageUrls[position]
        holder.onBind(ImageItem(imageUrl))
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }
}

class ImageViewHolder(
    binding: ItemImageBinding
) : ItemViewHolder<ItemImageBinding, ImageItem>(binding) {

    override fun onBind(item: ImageItem) {
        super.onBind(item)
        Glide.with(context)
            .load(item.imageUrl)
            .into(binding.ivImage)
    }

}