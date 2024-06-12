package com.smart.parts.picker.core.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.databinding.ItemAutoCompleteBinding
import com.smart.parts.picker.domain.format
import com.smart.parts.picker.models.Product

data class SearchResultItem(
    val productId: ProductId,
    val title: String,
    val price: String?,
    val imageUrl: String
) {
    companion object {
        fun create(
            product: Product
        ): SearchResultItem? {
            if (!product.isValid) return null
            return SearchResultItem(
                productId = product.id!!,
                title = product.fullName!!,
                price = product.price!!.filter { it.isValid }.minOfOrNull { it.price!! }?.format(2),
                imageUrl = product.image!!
            )
        }
    }
}

class AutocompleteAdapter(
    context: Context,
    private val items: List<SearchResultItem>
) : ArrayAdapter<SearchResultItem>(context, 0, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemAutoCompleteBinding = if (convertView == null) {
            ItemAutoCompleteBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemAutoCompleteBinding.bind(convertView)
        }

        val item = items.getOrNull(position)
        if (item != null) {
            binding.tvTitle.text = item.title
            item.price?.let { binding.tvPrice.text = it }

            val glide = Glide.with(context)
            glide.load(item.imageUrl).into(binding.ivPreview)
        }

        return binding.root
    }
}
