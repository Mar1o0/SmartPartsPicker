package com.vlad.sharaga.core.adapter.recycler.fingerprints

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.databinding.ItemShopBinding
import com.vlad.sharaga.domain.format
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ShopItem(
    val logoUrl: String,
    val price: Double,
    val deliveryDescription: String,
    val deliveryPrice: Double,
    val url: String,
): Item

class ShopFingerprint : ItemFingerprint<ItemShopBinding, ShopItem> {

    override fun isRelativeItem(item: Item) = item is ShopItem

    override fun getLayoutResId() = R.layout.item_shop

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemShopBinding, ShopItem> {
        val binding = ItemShopBinding.inflate(layoutInflater, parent, false)
        return ShopViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ShopItem>() {
        override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem
    }

}

class ShopViewHolder(
    binding: ItemShopBinding
) : ItemViewHolder<ItemShopBinding, ShopItem>(binding) {

    override fun onBind(item: ShopItem) {
        super.onBind(item)
        binding.tvPrice.text = context.getString(R.string.price, item.price.format(2))
        binding.tvDeliveryPrice.text = context.getString(R.string.price, item.deliveryPrice.format(2))
        binding.tvDeliveryDescription.text = item.deliveryDescription
        Glide.with(context)
            .load(item.logoUrl)
            .into(binding.ivPreview)
        binding.btnVariants.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
            try {
                context.startActivity(intent)
            } catch (_: Exception) {

            }
        }
    }

}