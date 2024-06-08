package com.vlad.sharaga.core.adapter.recycler.fingerprints.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.core.util.afterTextChanged
import com.vlad.sharaga.databinding.ItemFilterPriceRangeBinding
import com.vlad.sharaga.models.Filter
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class FilterPriceRangeItem(
    val id: Int,
    val min: Float,
    val max: Float,
    var from: Float,
    var to: Float,
) : FilterItem {
    companion object {
        fun fromFilter(filter: Filter): FilterItem = FilterPriceRangeItem(
            id = filter.id,
            min = filter.variants.getOrNull(0)?.toFloatOrNull() ?: 0f,
            max = filter.variants.getOrNull(1)?.toFloatOrNull() ?: 0f,
            from = filter.variants.getOrNull(0)?.toFloatOrNull() ?: 0f,
            to = filter.variants.getOrNull(1)?.toFloatOrNull() ?: 0f,
        )
    }
}

class FilterPriceRangeFingerprint : ItemFingerprint<ItemFilterPriceRangeBinding, FilterPriceRangeItem> {

    override fun isRelativeItem(item: Item) = item is FilterPriceRangeItem

    override fun getLayoutResId() = R.layout.item_filter_price_range

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFilterPriceRangeBinding, FilterPriceRangeItem> {
        val binding = ItemFilterPriceRangeBinding.inflate(layoutInflater, parent, false)
        return FilterPriceRangeViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FilterPriceRangeItem>() {
        override fun areItemsTheSame(oldItem: FilterPriceRangeItem, newItem: FilterPriceRangeItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FilterPriceRangeItem, newItem: FilterPriceRangeItem) =
            oldItem == newItem
    }
}

class FilterPriceRangeViewHolder(
    binding: ItemFilterPriceRangeBinding
) : ItemViewHolder<ItemFilterPriceRangeBinding, FilterPriceRangeItem>(binding) {

    override fun onBind(item: FilterPriceRangeItem) {
        super.onBind(item)
        binding.etFrom.setText(item.from.toInt().toString())
        binding.etTo.setText(item.to.toInt().toString())
        binding.rsRange.valueFrom = item.min
        binding.rsRange.valueTo = item.max
        binding.rsRange.values = listOf(item.from, item.to)

        binding.etFrom.doOnTextChanged { text, _, _, _ ->
            val value = text.toString().toFloatOrNull() ?: item.min
            if (value < item.min) {
                binding.tiFrom.error = context.getString(R.string.min_value, item.min)
                return@doOnTextChanged
            } else if (value > item.max) {
                binding.tiFrom.error = context.getString(R.string.max_value, item.max)
                return@doOnTextChanged
            } else {
                binding.tiFrom.error = null
            }

            binding.rsRange.values = listOf(value, binding.rsRange.values[1])
        }
        binding.etTo.doOnTextChanged { text, _, _, _ ->
            val value = text.toString().toFloatOrNull() ?: item.min
            if (value < item.min) {
                binding.tiTo.error = context.getString(R.string.min_value, item.min)
                return@doOnTextChanged
            } else if (value > item.max) {
                binding.tiTo.error = context.getString(R.string.max_value, item.max)
                return@doOnTextChanged
            } else {
                binding.tiTo.error = null
            }

            binding.rsRange.values = listOf(binding.rsRange.values[0], value)
        }

        binding.rsRange.addOnChangeListener { slider, _, fromUser ->
            if (!fromUser) return@addOnChangeListener
            item.from = slider.values[0]
            item.to = slider.values[1]
            binding.etFrom.setText(item.from.toInt().toString())
            binding.etTo.setText(item.to.toInt().toString())
        }
    }
}