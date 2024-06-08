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
import com.vlad.sharaga.databinding.ItemFilterRadioBinding
import com.vlad.sharaga.models.Filter
import kotlinx.parcelize.Parcelize
import kotlin.math.roundToInt

@Keep
@Parcelize
data class FilterRadioItem(
    val id: Int,
    val title: String,
    val variants: List<FilterRadioVariantItem>
) : FilterItem {

    companion object {
        fun fromFilter(filter: Filter): FilterItem = FilterRadioItem(
            id = filter.id,
            title = filter.filterName,
            variants = filter.variants.mapIndexed { i, variant ->
                FilterRadioVariantItem(
                    id = i,
                    name = variant,
                    isSelected = false
                )
            }
        )
    }

}

class FilterRadioFingerprint : ItemFingerprint<ItemFilterRadioBinding, FilterRadioItem> {

    override fun isRelativeItem(item: Item) = item is FilterRadioItem

    override fun getLayoutResId() = R.layout.item_filter_radio

    override fun getViewHolder(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemViewHolder<ItemFilterRadioBinding, FilterRadioItem> {
        val binding = ItemFilterRadioBinding.inflate(layoutInflater, parent, false)
        return FilterRadioViewHolder(binding)
    }

    override fun getDiffUtil() = diffUtil

    private val diffUtil = object : DiffUtil.ItemCallback<FilterRadioItem>() {
        override fun areItemsTheSame(oldItem: FilterRadioItem, newItem: FilterRadioItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: FilterRadioItem, newItem: FilterRadioItem) =
            oldItem == newItem
    }

}

class FilterRadioViewHolder(
    binding: ItemFilterRadioBinding
) : ItemViewHolder<ItemFilterRadioBinding, FilterRadioItem>(binding) {

    private val adapter by lazy {
        FingerprintAdapter(
            listOf(
                FilterRadioVariantFingerprint()
            )
        )
    }

    private var isExpanded = false

    override fun onBind(item: FilterRadioItem) {
        super.onBind(item)

        binding.tvTitle.text = item.title
        binding.rvFilterRadio.layoutManager = LinearLayoutManager(context)
        binding.rvFilterRadio.adapter = adapter
        binding.rvFilterRadio.addItemDecoration(
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