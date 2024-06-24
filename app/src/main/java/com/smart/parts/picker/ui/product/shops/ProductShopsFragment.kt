package com.smart.parts.picker.ui.product.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.ShopFingerprint
import com.smart.parts.picker.core.util.parcelable
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.databinding.FragmentProductShopsBinding
import com.smart.parts.picker.models.Product
import com.smart.parts.picker.ui.product.ARG_PRODUCT
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductShopsFragment : Fragment() {

    private var _binding: FragmentProductShopsBinding? = null
    private val binding get() = _binding!!

    private val product: Product? by lazy { arguments?.parcelable(ARG_PRODUCT) }
    private val viewModel: ProductShopsViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<ProductShopsViewModel.Factory> { factory ->
                    factory.create(product = product)
                }
        }
    )
    private val adapter by lazy {
        com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter(
            listOf(ShopFingerprint())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductShopsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvOffers.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOffers.adapter = adapter
        binding.rvOffers.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    ShopsState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is ShopsState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        tvCityValue.text = state.cityName
                        adapter.submitList(state.article)
                    }

                    is ShopsState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true

                        tvError.text = getString(R.string.error_no_internet)
//                        tvError.text = state.message
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

    companion object {
        @JvmStatic
        fun newInstance(
            product: Product
        ): ProductShopsFragment = ProductShopsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PRODUCT, product)
            }
        }
    }
}