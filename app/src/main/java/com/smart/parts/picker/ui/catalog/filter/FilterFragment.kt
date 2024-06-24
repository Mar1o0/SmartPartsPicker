package com.smart.parts.picker.ui.catalog.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterPriceRangeFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterRadioFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.filter.FilterSelectFingerprint
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.databinding.FragmentFilterBinding
import com.smart.parts.picker.ui.NavHostViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val args: FilterFragmentArgs by navArgs()
    private val sharedViewModel: NavHostViewModel by activityViewModels()
    private val viewModel: FilterViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<FilterViewModel.Factory> { factory ->
                    factory.create(
                        sharedViewModel = sharedViewModel,
                        productType = args.categoryItem.productType,
                    )
                }
        }
    )

    private val adapter: FingerprintAdapter by lazy {
        FingerprintAdapter(getFingerprints())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvFilters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFilters.adapter = adapter
        binding.rvFilters.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        binding.btnApplyFilter.setOnClickListener {
            binding.btnApplyFilter.isEnabled = false
            viewModel.applyFilters(adapter.currentList) { minPrice, maxPrice, filters ->
                findNavController().navigate(
                    FilterFragmentDirections.actionFilterFragmentToProductBrowseFragment(
                        categoryItem = args.categoryItem,
                        priceMin = minPrice,
                        priceMax = maxPrice,
                        filters = filters
                    )
                )
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    FilterState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is FilterState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        adapter.submitList(state.filters)
                    }

                    is FilterState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true
                        tvError.text = getString(R.string.error_no_internet)
                    }
                }
            }
        }
        viewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFingerprints() = listOf(
        FilterSelectFingerprint(),
        FilterPriceRangeFingerprint(),
        FilterRadioFingerprint(),
    )
}