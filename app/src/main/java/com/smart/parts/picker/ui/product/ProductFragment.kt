package com.smart.parts.picker.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.smart.parts.picker.R
import com.smart.parts.picker.core.adapter.pager.ProductPagerAdapter
import com.smart.parts.picker.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch

const val ARG_PRODUCT = "product"

@AndroidEntryPoint
class ProductFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val args: ProductFragmentArgs by navArgs()
    private val viewModel: ProductViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<ProductViewModel.Factory> { factory ->
                    factory.create(productId = args.productId)
                }
        }
    )

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setTab(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vpContent.isUserInputEnabled = false

        binding.btnDescription.setOnClickListener {
            binding.vpContent.currentItem = 0
        }
        binding.btnOffers.setOnClickListener {
            binding.vpContent.currentItem = 1
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    ProductState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        flContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is ProductState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        flContent.isVisible = true
                        tvError.isVisible = false

                        binding.vpContent.adapter = ProductPagerAdapter(
                            this@ProductFragment,
                            state.product,
                            ::onChangePage
                        )
                        binding.vpContent.post {
                            binding.vpContent.setCurrentItem(args.startTab, true)
                        }
                    }

                    is ProductState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        flContent.isVisible = false
                        tvError.isVisible = true

                        tvError.text = state.message
                    }
                }
            }
        }
        viewModel.load()
    }

    override fun onStart() {
        super.onStart()
        binding.vpContent.registerOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onStop() {
        super.onStop()
        binding.vpContent.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    private fun setTab(position: Int) {
        when (position) {
            0 -> with(binding) {
                btnDescription.isEnabled = false
                btnDescription.setTextColor(0xFFFFFFFF.toInt())
                btnDescription.setBackgroundColor(
                    requireContext().getColor(R.color.primary_variant)
                )

                btnOffers.isEnabled = true
                btnOffers.setBackgroundColor(
                    requireContext().getColor(R.color.primary)
                )
            }

            1 -> with(binding) {
                btnDescription.isEnabled = true
                btnDescription.setBackgroundColor(
                    requireContext().getColor(R.color.primary)
                )

                btnOffers.isEnabled = false
                btnOffers.setTextColor(0xFFFFFFFF.toInt())
                btnOffers.setBackgroundColor(
                    requireContext().getColor(R.color.primary_variant)
                )
            }
        }
    }

    private fun onChangePage(position: Int) {
        binding.vpContent.currentItem = position
    }
}