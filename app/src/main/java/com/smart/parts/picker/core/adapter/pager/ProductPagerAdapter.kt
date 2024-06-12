package com.smart.parts.picker.core.adapter.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.ui.product.description.ProductDescriptionFragment
import com.smart.parts.picker.ui.product.shops.ProductShopsFragment

class ProductPagerAdapter(
    fragment: Fragment,
    private val product: Product,
    private val onChangePage: (Int) -> Unit
) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductDescriptionFragment.newInstance(product, onChangePage)
            1 -> ProductShopsFragment.newInstance(product)
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}