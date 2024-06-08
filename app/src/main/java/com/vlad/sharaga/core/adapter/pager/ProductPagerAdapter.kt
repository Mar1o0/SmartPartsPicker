package com.vlad.sharaga.core.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.ui.product.description.ProductDescriptionFragment
import com.vlad.sharaga.ui.product.shops.ProductShopsFragment

class ProductPagerAdapter(
    fragment: Fragment,
    private val productId: ProductId,
    private val onChangePage: (Int) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductDescriptionFragment.newInstance(productId, onChangePage)
            1 -> ProductShopsFragment.newInstance(productId)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}