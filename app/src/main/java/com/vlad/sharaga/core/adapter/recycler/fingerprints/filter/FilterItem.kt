package com.vlad.sharaga.core.adapter.recycler.fingerprints.filter

import com.vlad.sharaga.core.adapter.Item
import com.vlad.sharaga.models.Filter
import com.vlad.sharaga.models.FilterType

interface FilterItem : Item {

    companion object {
        fun fromFilter(filter: Filter): FilterItem {
            return when (filter.filterType) {
                FilterType.SELECT -> FilterSelectItem.fromFilter(filter)
                FilterType.RANGE -> FilterPriceRangeItem.fromFilter(filter)
                FilterType.RADIO -> FilterRadioItem.fromFilter(filter)
            }
        }
    }
}
