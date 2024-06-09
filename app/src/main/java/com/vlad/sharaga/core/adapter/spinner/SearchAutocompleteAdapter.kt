package com.vlad.sharaga.core.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.ItemAutoCompleteBinding

data class SearchResultItem(
    val productId: ProductId,
    val title: String,
    val price: String,
    val imageUrl: String
)

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
            binding.tvPrice.text = item.price
            val glide = Glide.with(context)
            glide.load(item.imageUrl).into(binding.ivPreview)
        }

        return binding.root
    }
}
