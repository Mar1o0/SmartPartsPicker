package com.vlad.sharaga.ui.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.pager.ArticlePagerAdapter
import com.vlad.sharaga.databinding.FragmentProductBinding
import dagger.hilt.android.AndroidEntryPoint

const val ARG_PRODUCT_ID = "productId"

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentProductBinding? = null
    private val binding get() = _binding!!

    private val args: ArticleFragmentArgs by navArgs()

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
        binding.vpContent.adapter = ArticlePagerAdapter(this, args.productId)
        binding.vpContent.isUserInputEnabled = false
        binding.vpContent.currentItem = args.startTab
        setTab(args.startTab)

        binding.btnDescription.setOnClickListener {
            binding.vpContent.currentItem = 0
        }
        binding.btnOffers.setOnClickListener {
            binding.vpContent.currentItem = 1
        }
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
            0 -> {
                binding.btnDescription.isEnabled = false
                binding.btnDescription.setTextColor(0xFFFFFFFF.toInt())
                binding.btnDescription.setBackgroundColor(
                    requireContext().getColor(R.color.primary_variant)
                )

                binding.btnOffers.isEnabled = true
                binding.btnOffers.setBackgroundColor(
                    requireContext().getColor(R.color.primary)
                )
            }
            1 -> {
                binding.btnDescription.isEnabled = true
                binding.btnDescription.setBackgroundColor(
                    requireContext().getColor(R.color.primary)
                )

                binding.btnOffers.isEnabled = false
                binding.btnOffers.setTextColor(0xFFFFFFFF.toInt())
                binding.btnOffers.setBackgroundColor(
                    requireContext().getColor(R.color.primary_variant)
                )
            }
        }
    }
}