package com.smart.parts.picker.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.databinding.FragmentCatalogBinding
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.CategoryFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.CategoryItem
import com.smart.parts.picker.core.util.parcelable
import com.smart.parts.picker.core.util.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()
    private val adapter: FingerprintAdapter by lazy {
        FingerprintAdapter(
            listOf(CategoryFingerprint(::onCategoryClick))
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryItem: CategoryItem? = arguments.parcelable("category_item")
        if (categoryItem != null) {
            onCategoryClick(categoryItem)
            arguments?.clear()
            return
        }

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter
        binding.rvCategories.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    CatalogState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is CatalogState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        adapter.submitList(state.categories)
                    }

                    CatalogState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true
                    }
                }
            }
        }
    }

    private fun onCategoryClick(categoryItem: CategoryItem) {
        val action =
            CatalogFragmentDirections.actionNavigationCatalogToProductBrowseFragment(
                categoryItem = categoryItem,
                priceMin = 0f,
                priceMax = Float.MAX_VALUE,
                filters = ""
            )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}