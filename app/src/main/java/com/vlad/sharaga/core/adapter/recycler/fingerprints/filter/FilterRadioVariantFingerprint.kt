package com.vlad.sharaga.core.adapter.recycler.fingerprints.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.databinding.ItemFilterRadioVariantBinding
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FilterRadioVariantItem(
    val id: Int,
    val name: String,
    var isSelected: Boolean,
) : FilterItem

class FilterRadioVariantFingerprint(
) : ItemFingerprint<ItemFilterRadioVariantBinding, FilterRadioVariantItem> {

    override fun isRelativeItem(item: Item) = item is FilterRadioVariantItem

    override fun getLayoutResId() = R.layout.item_filter_radio_variant

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFilterRadioVariantBinding, FilterRadioVariantItem> {
        val binding = ItemFilterRadioVariantBinding.inflate(layoutInflater, parent, false)
        return FilterRadioVariantViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FilterRadioVariantItem>() {
        override fun areItemsTheSame(
            oldItem: FilterRadioVariantItem,
            newItem: FilterRadioVariantItem
        ) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: FilterRadioVariantItem,
            newItem: FilterRadioVariantItem
        ) = oldItem == newItem
    }
}

class FilterRadioVariantViewHolder(
    binding: ItemFilterRadioVariantBinding,
) : ItemViewHolder<ItemFilterRadioVariantBinding, FilterRadioVariantItem>(binding) {

    override fun onBind(item: FilterRadioVariantItem) {
        super.onBind(item)
        binding.cbSelect.isChecked = item.isSelected
        binding.cbSelect.text = item.name
        binding.cbSelect.setOnCheckedChangeListener { _, isChecked ->
            item.isSelected = isChecked
        }
    }

}