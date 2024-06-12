package com.smart.parts.picker.core.adapter.recycler.fingerprints

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.Item
import com.smart.parts.picker.core.adapter.recycler.ItemFingerprint
import com.smart.parts.picker.core.adapter.recycler.ItemViewHolder
import com.smart.parts.picker.databinding.ItemAssemblyBinding
import com.smart.parts.picker.domain.format
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AssemblyItem(
    val id: Int,
    val title: String,
    val count: Int,
    val price: Double,
    val previewUrl1: String?,
    val previewUrl2: String?,
    val previewUrl3: String?,
) : Item

class AssemblyFingerprint(
    private val onAssemblyClick: (Int) -> Unit
) : ItemFingerprint<ItemAssemblyBinding, AssemblyItem> {

    override fun isRelativeItem(item: Item) = item is AssemblyItem

    override fun getLayoutResId() = R.layout.item_assembly

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemAssemblyBinding, AssemblyItem> {
        val binding = ItemAssemblyBinding.inflate(layoutInflater, parent, false)
        return AssemblyViewHolder(binding, onAssemblyClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<AssemblyItem>() {
        override fun areItemsTheSame(oldItem: AssemblyItem, newItem: AssemblyItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: AssemblyItem, newItem: AssemblyItem) =
            oldItem == newItem
    }

}

class AssemblyViewHolder(
    binding: ItemAssemblyBinding,
    private val onAssemblyClick: (Int) -> Unit
) : ItemViewHolder<ItemAssemblyBinding, AssemblyItem>(binding) {

    override fun onBind(item: AssemblyItem) {
        super.onBind(item)
        binding.root.setOnClickListener { onAssemblyClick(item.id) }
        binding.tvTitle.text = item.title
        binding.tvDescription.text = context.getString(R.string.assembly_description, item.count)
        binding.tvPriceValue.text = context.getString(R.string.price, item.price.format(2))
        val glide = Glide.with(context)
        item.previewUrl1?.let {
            binding.ivPreview1.isVisible = true
            glide.load(it).into(binding.ivPreview1)
        }
        item.previewUrl2?.let {
            binding.ivPreview2.isVisible = true
            glide.load(it).into(binding.ivPreview2)
        }
        item.previewUrl3?.let {
            binding.ivPreview3.isVisible = true
            glide.load(it).into(binding.ivPreview3)
        }
    }

}