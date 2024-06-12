package com.smart.parts.picker.ui

import androidx.lifecycle.ViewModel
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterItem
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.models.types.ProductType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavHostViewModel @Inject constructor(

) : ViewModel() {

    var currentProductType: ProductType? = null
    val currentFilters = mutableListOf<FilterItem>()

}