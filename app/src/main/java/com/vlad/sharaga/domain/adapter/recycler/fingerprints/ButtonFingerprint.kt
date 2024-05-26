package com.vlad.sharaga.domain.adapter.recycler.fingerprints

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ItemButtonBinding
import com.vlad.sharaga.domain.adapter.recycler.Item
import com.vlad.sharaga.domain.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.domain.adapter.recycler.ItemViewHolder
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ButtonItem(
    val title: String,
    val gravity: Int = Gravity.CENTER
): Item

class ButtonFingerprint(
    private val onClick: () -> Unit
) : ItemFingerprint<ItemButtonBinding, ButtonItem> {

    override fun isRelativeItem(item: Item) = item is ButtonItem

    override fun getLayoutResId() = R.layout.item_button

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemButtonBinding, ButtonItem> {
        val binding = ItemButtonBinding.inflate(layoutInflater, parent, false)
        return ButtonViewHolder(binding, onClick)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<ButtonItem>() {
        override fun areItemsTheSame(oldItem: ButtonItem, newItem: ButtonItem) = oldItem == newItem

        override fun areContentsTheSame(oldItem: ButtonItem, newItem: ButtonItem) = oldItem == newItem
    }

}

class ButtonViewHolder(
    binding: ItemButtonBinding,
    private val onClick: () -> Unit,
) : ItemViewHolder<ItemButtonBinding, ButtonItem>(binding) {

    override fun onBind(item: ButtonItem) {
        super.onBind(item)
        binding.btnButton.text = item.title
        val layoutParams = binding.btnButton.layoutParams as FrameLayout.LayoutParams
        layoutParams.gravity = item.gravity
        binding.btnButton.setOnClickListener {
            binding.btnButton.isEnabled = false
            onClick()
        }
    }

}