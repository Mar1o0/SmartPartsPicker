package com.vlad.sharaga.ui.catalog.filter

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
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.filter.FilterItem
import com.vlad.sharaga.core.adapter.recycler.fingerprints.filter.FilterPriceRangeFingerprint
import com.vlad.sharaga.core.adapter.recycler.fingerprints.filter.FilterSelectFingerprint
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.databinding.FragmentBrowseBinding
import com.vlad.sharaga.databinding.FragmentFilterBinding
import com.vlad.sharaga.ui.NavHostViewModel
import com.vlad.sharaga.ui.catalog.browse.BrowseState
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
                        productType = args.productType,
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
            sharedViewModel.currentFilters.clear()
            @Suppress("UNCHECKED_CAST")
            sharedViewModel.currentFilters.addAll(adapter.currentList as List<FilterItem>)
            sharedViewModel.currentFilters.forEach {
                android.util.Log.e("BBB", "filter = $it")
            }
            findNavController().popBackStack()
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
                        tvError.text = state.message
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
    )
}