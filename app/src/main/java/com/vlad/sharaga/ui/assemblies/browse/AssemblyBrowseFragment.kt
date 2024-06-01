package com.vlad.sharaga.ui.assemblies.browse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewFingerprint
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.FragmentAssemblyBrowseBinding
import com.vlad.sharaga.ui.product.description.ProductDescriptionViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.flow.collect
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

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    AssemblyBrowseState.Error -> TODO()
                    is AssemblyBrowseState.Loaded -> TODO()
                    AssemblyBrowseState.Loading -> TODO()
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