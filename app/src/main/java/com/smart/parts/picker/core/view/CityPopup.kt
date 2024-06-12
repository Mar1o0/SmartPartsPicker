package com.smart.parts.picker.core.view

import android.app.Dialog
import android.content.Context
import com.smart.parts.picker.core.adapter.spinner.DropDownSpinnerAdapter
import com.smart.parts.picker.databinding.PopupCitySelectBinding

const val DEFAULT_CITY = "Unknown"

class CityPopup(
    context: Context,
    private val cities: List<String>,
    private val onSelectedCity: (String) -> Unit = {},
) : Dialog(context) {

    init {
        setupSelectPopup()
        window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun setupSelectPopup() {
        val binding = PopupCitySelectBinding.inflate(layoutInflater)
        binding.spCitiesOptions.adapter = DropDownSpinnerAdapter(context, cities)
        binding.btnAccept.setOnClickListener {
            binding.btnAccept.isEnabled = false
            onSelectedCity(cities[binding.spCitiesOptions.selectedItemPosition])
        }
        setContentView(binding.root)
    }

}