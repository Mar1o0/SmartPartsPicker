package com.vlad.sharaga.core.adapter.recycler.fingerprints.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.ItemFingerprint
import com.vlad.sharaga.core.adapter.recycler.ItemViewHolder
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.databinding.ItemFilterSelectBinding
import com.vlad.sharaga.models.Filter
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Keep
@Parcelize
data class FilterSelectItem(
    val id: Int,
    val title: String,
    val variants: List<FilterSelectVariantItem>
) : FilterItem {

    companion object {
        fun fromFilter(filter: Filter): FilterItem = FilterSelectItem(
            id = filter.id,
            title = filter.filterName,
            variants = filter.variants.mapIndexed { i, variant ->
                FilterSelectVariantItem(
                    id = i,
                    name = variant,
                    isSelected = false
                )
            }
        )
    }

}

class FilterSelectFingerprint : ItemFingerprint<ItemFilterSelectBinding, FilterSelectItem> {

    override fun isRelativeItem(item: Item) = item is FilterSelectItem

    override fun getLayoutResId() = R.layout.item_filter_select

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFilterSelectBinding, FilterSelectItem> {
        val binding = ItemFilterSelectBinding.inflate(layoutInflater, parent, false)
        return FilterSelectViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FilterSelectItem>() {
        override fun areItemsTheSame(oldItem: FilterSelectItem, newItem: FilterSelectItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FilterSelectItem, newItem: FilterSelectItem) =
            oldItem == newItem
    }

}

class FilterSelectViewHolder(
    binding: ItemFilterSelectBinding
) : ItemViewHolder<ItemFilterSelectBinding, FilterSelectItem>(binding) {

    private val adapter by lazy {
        FingerprintAdapter(
            listOf(
                FilterSelectVariantFingerprint()
            )
        )
    }

    private var isExpanded = false

    override fun onBind(item: FilterSelectItem) {
        super.onBind(item)

        binding.tvTitle.text = item.title
        binding.rvFilterSelect.layoutManager = LinearLayoutManager(context)
        binding.rvFilterSelect.adapter = adapter
        binding.rvFilterSelect.addItemDecoration(
            VerticalDividerItemDecoration(
                context.toPx(12).roundToInt()
            )
        )

        setVariants()

        binding.btnShowAll.setOnClickListener {
            isExpanded = !isExpanded
            binding.btnShowAll.text = if (isExpanded) {
                context.getString(R.string.hide_all)
            } else {
                context.getString(R.string.show_all, item.variants.size)
            }
            setVariants()
        }
    }

    private fun setVariants() {
        if (isExpanded) {
            adapter.submitList(item.variants)
        } else {
            adapter.submitList(item.variants.take(5))
        }
    }
}