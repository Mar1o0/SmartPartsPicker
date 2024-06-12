package com.smart.parts.picker.core.view

import android.app.Dialog
import android.content.Context
import androidx.core.widget.doOnTextChanged
import com.smart.parts.picker.R
import com.smart.parts.picker.databinding.PopupBudgetBinding

class BudgetPopup(
    context: Context,
    private val onSearch: (String) -> Unit = {},
) : Dialog(context) {

    init {
        setupUI()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun setupUI() {
        val binding = PopupBudgetBinding.inflate(layoutInflater)
        binding.etValue.doOnTextChanged { text, _, _, _ ->
            val value = text.toString().toIntOrNull() ?: 0
            if (value < 0) {
                binding.btnSearch.isEnabled = false
                binding.tiValue.error = context.getString(R.string.enter_positive_number)
                return@doOnTextChanged
            } else {
                binding.btnSearch.isEnabled = true
                binding.tiValue.error = null
            }
        }
        binding.btnSearch.setOnClickListener {
            onSearch(
                binding.etValue.text.toString()
            )
        }
        setContentView(binding.root)
    }

}