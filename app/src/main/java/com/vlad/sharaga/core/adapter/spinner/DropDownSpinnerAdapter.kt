package com.vlad.sharaga.core.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.vlad.sharaga.databinding.ItemSpinerBinding

class DropDownSpinnerAdapter<T>(
    context: Context,
    private val values: List<T>,
    private val map: (T?) -> String = { it?.toString() ?: "" },
    private val onConfirmClicked: (String) -> Unit = {},
    private val withInput: Boolean = false
) : ArrayAdapter<T>(context, 0, values) {

    override fun getCount(): Int {
        return if (withInput) values.size + 1 else values.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (!withInput || position < values.size) ITEM_TYPE else INPUT_TYPE
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemSpinerBinding = if (convertView == null) {
            ItemSpinerBinding.inflate(LayoutInflater.from(context), parent, false)
        } else {
            ItemSpinerBinding.bind(convertView)
        }

        when (getItemViewType(position)) {
            ITEM_TYPE -> setupItemView(binding, position)
            INPUT_TYPE -> setupInputView(binding)
        }

        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    private fun setupItemView(binding: ItemSpinerBinding, position: Int) {
        val option = getItem(position)
        binding.containerText.isVisible = true
        binding.containerText.text = map(option)
    }

    private fun setupInputView(binding: ItemSpinerBinding) {
        binding.containerInput.isVisible = true

        binding.etName.addTextChangedListener {
            binding.btnConfirm.isEnabled = it?.isNotBlank() == true
        }

        binding.btnConfirm.setOnClickListener {
            binding.btnConfirm.isEnabled = false
            onConfirmClicked(binding.etName.text.toString())
        }
    }

    companion object {
        private const val ITEM_TYPE = 0
        private const val INPUT_TYPE = 1
    }
}