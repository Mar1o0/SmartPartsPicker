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
        binding.vpContent.adapter = ArticlePagerAdapter(this, args.productId, ::onChangePage)
        binding.vpContent.isUserInputEnabled = false
        binding.vpContent.post {
            binding.vpContent.setCurrentItem(args.startTab, true)
        }

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
//        setTab(args.startTab)
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