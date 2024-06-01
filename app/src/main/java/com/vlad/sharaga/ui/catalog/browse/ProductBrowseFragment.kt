package com.vlad.sharaga.ui.catalog.browse

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
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewFingerprint
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.FragmentBrowseBinding
import com.vlad.sharaga.ui.assemblies.browse.AssemblyBrowseFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductBrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BrowseViewModel by viewModels()
    private val args: ProductBrowseFragmentArgs by navArgs()
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
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.adapter = adapter
        binding.rvArticles.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(
                    32
                ).roundToInt()
            )
        )
        binding.rvArticles.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )
        binding.tvTitle.text = args.categoryItem.title

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    BrowseState.Loading -> {
                        binding.cpiLoading.isVisible = true
                        binding.rvArticles.isVisible = false
                        binding.tvError.isVisible = false
                    }

                    is BrowseState.Loaded -> {
                        binding.cpiLoading.isVisible = false
                        binding.rvArticles.isVisible = true
                        binding.tvError.isVisible = false
                        adapter.submitList(state.products)
                    }

                    BrowseState.Error -> {
                        binding.cpiLoading.isVisible = false
                        binding.rvArticles.isVisible = false
                        binding.tvError.isVisible = true
                    }
                }
            }
        }
        viewModel.fetchCategory(args.categoryItem.productType)
    }

    private fun onProductClick(productId: ProductId) {
        findNavController().navigate(
            ProductBrowseFragmentDirections.actionCategoryFragmentToArticleFragment(productId)
        )
    }

    private fun onVariantsClick(productId: ProductId) {
        findNavController().navigate(
            ProductBrowseFragmentDirections.actionCategoryFragmentToArticleFragment(productId, 1)
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}