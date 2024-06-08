package com.vlad.sharaga.ui

import androidx.lifecycle.ViewModel
import com.vlad.sharaga.core.adapter.recycler.fingerprints.filter.FilterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavHostViewModel @Inject constructor(

) : ViewModel() {

    val currentFilters = mutableListOf<FilterItem>()

}