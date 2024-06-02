package com.vlad.sharaga.core.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.vlad.sharaga.databinding.ItemSpinerTextBinding

class DropDownSpinnerAdapter<T>(
    context: Context,
    cityNames: List<T>,
    private val map: (T?) -> String = { it?.toString() ?: "" }
) : ArrayAdapter<T>(context, 0, cityNames) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinerTextBinding = if (convertView == null) {
            ItemSpinerTextBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemSpinerTextBinding.bind(convertView)
        }

        val option = getItem(position)
        binding.tvValue.text = map(option)

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}