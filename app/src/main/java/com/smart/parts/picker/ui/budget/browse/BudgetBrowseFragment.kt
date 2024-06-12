package com.smart.parts.picker.ui.budget.browse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewFingerprint
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.databinding.FragmentBudgetBrowseBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class BudgetBrowseFragment : Fragment() {

    private var _binding: FragmentBudgetBrowseBinding? = null
    private val binding get() = _binding!!

    private val args: BudgetBrowseFragmentArgs by navArgs()
    private val viewModel: BudgetBrowseViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<BudgetBrowseViewModel.Factory> { factory ->
                    factory.create(assembly = args.assembly)
                }
        }
    )
    private val adapter by lazy {
        FingerprintAdapter(
            listOf(
                ProductPreviewFingerprint(
                    onProductClick = ::onProductClick,
                    onVariantsClick = ::onVariantsClick,
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetBrowseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.rvProducts.adapter = adapter
        binding.rvProducts.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    12
                ).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    BudgetBrowseState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is BudgetBrowseState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        tvPrice.text = state.assemblyPrice
                        adapter.submitList(state.products)
                    }

                    BudgetBrowseState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true
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

    private fun onProductClick(productId: ProductId) {
        findNavController().navigate(
            BudgetBrowseFragmentDirections
                .actionBudgetBrowseFragmentToProductFragment(productId)
        )
    }

    private fun onVariantsClick(productId: ProductId) {
        findNavController().navigate(
            BudgetBrowseFragmentDirections
                .actionBudgetBrowseFragmentToProductFragment(productId, 1)
        )
    }
}