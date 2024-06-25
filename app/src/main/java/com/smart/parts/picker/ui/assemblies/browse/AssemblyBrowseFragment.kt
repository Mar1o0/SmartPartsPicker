package com.smart.parts.picker.ui.assemblies.browse

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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ProductPreviewItem
import com.smart.parts.picker.core.adapter.recycler.util.SwipeToDelete
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.models.types.ProductId
import com.smart.parts.picker.databinding.FragmentAssemblyBrowseBinding
import com.smart.parts.picker.domain.format
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class AssemblyBrowseFragment : Fragment() {

    private var _binding: FragmentAssemblyBrowseBinding? = null
    private val binding get() = _binding!!

    private val args: AssemblyBrowseFragmentArgs by navArgs()
    private val viewModel: AssemblyBrowseViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<AssemblyBrowseViewModel.Factory> { factory ->
                    factory.create(assemblyId = args.assemblyId)
                }
        }
    )

    private val adapter: FingerprintAdapter by lazy {
        FingerprintAdapter(
            listOf(
                ProductPreviewFingerprint(
                    onProductClick = ::onProductClick,
                    onVariantsClick = ::onVariantsClick,
                ),
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssemblyBrowseBinding.inflate(inflater, container, false)
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

        val onItemSwipedToDelete: (Int) -> Unit = { positionForRemove: Int ->
            val removedItem = adapter.currentList[positionForRemove] as? ProductPreviewItem
            removedItem?.productId?.let(viewModel::deleteProduct)
        }
        val swipeToDeleteCallback = SwipeToDelete(R.layout.item_product_preview, onItemSwipedToDelete)
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.rvProducts)

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (_binding == null) return@collect
                when (state) {
                    AssemblyBrowseState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is AssemblyBrowseState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        tvTitle.text = state.assemblyTitle
                        tvPriceValue.text = getString(R.string.price, state.averagePrice.format(2))
                        adapter.submitList(state.products)
                    }

                    AssemblyBrowseState.Error -> with(binding) {
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
            AssemblyBrowseFragmentDirections.actionAssemblyBrowseFragmentToProductFragment(productId)
        )
    }

    private fun onVariantsClick(productId: ProductId) {
        findNavController().navigate(
            AssemblyBrowseFragmentDirections.actionAssemblyBrowseFragmentToProductFragment(
                productId,
                1
            )
        )
    }

}