package com.vlad.sharaga.ui.product.shops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ShopFingerprint
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.FragmentProductShopsBinding
import com.vlad.sharaga.ui.product.ARG_PRODUCT_ID
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductShopsFragment : Fragment() {

    private var _binding: FragmentProductShopsBinding? = null
    private val binding get() = _binding!!

    private val productId: ProductId? by lazy { arguments?.getInt(ARG_PRODUCT_ID) }
    private val viewModel: ShopsViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<ShopsViewModel.Factory> { factory ->
                    factory.create(productId = productId)
                }
        }
    )
    private val adapter by lazy {
        FingerprintAdapter(
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
            HorizontalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )
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
                    is ShopsState.Error -> {
                        with(binding) {
                            tvError.visibility = View.VISIBLE
                            cpiLoading.visibility = View.GONE
                            nsContent.visibility = View.GONE

                            tvError.text = state.message
                        }
                    }

                    is ShopsState.Loaded -> {
                        with(binding) {
                            tvError.visibility = View.GONE
                            cpiLoading.visibility = View.GONE
                            nsContent.visibility = View.VISIBLE

                            tvCityLabel.text = state.cityName
                            adapter.submitList(state.article)
                        }
                    }

                    ShopsState.Loading -> {
                        with(binding) {
                            tvError.visibility = View.GONE
                            cpiLoading.visibility = View.VISIBLE
                            nsContent.visibility = View.GONE
                        }
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
            productId: ProductId
        ): ProductShopsFragment = ProductShopsFragment().apply {
            arguments = Bundle().apply {
                putInt(ARG_PRODUCT_ID, productId)
            }
        }
    }
}