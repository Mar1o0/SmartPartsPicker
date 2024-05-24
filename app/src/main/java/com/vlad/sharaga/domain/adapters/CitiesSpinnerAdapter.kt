package com.vlad.sharaga.domain.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.vlad.sharaga.databinding.ItemCityBinding

class CitiesSpinnerAdapter(
    context: Context,
    cityNames: List<String>
) : ArrayAdapter<String>(context, 0, cityNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemCityBinding = if (convertView == null) {
            ItemCityBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemCityBinding.bind(convertView)
        }

        val option = getItem(position)
        binding.tvCityName.text = option

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}