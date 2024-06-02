package com.vlad.sharaga.ui.product.description

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.tabs.TabLayout
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.pager.ImagePagerAdapter
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.LineDivideItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.SpecificationFingerprint
import com.vlad.sharaga.core.adapter.recycler.fingerprints.SpecificationItem
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.core.view.AssemblyPopup
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.FragmentProductDescriptionBinding
import com.vlad.sharaga.domain.format
import com.vlad.sharaga.models.ProductDescription
import com.vlad.sharaga.ui.product.ARG_PRODUCT_ID
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class ProductDescriptionFragment(
    private val onChangePage: (Int) -> Unit = {}
) : Fragment() {

    private var _binding: FragmentProductDescriptionBinding? = null
    private val binding get() = _binding!!

    private val productId: ProductId? by lazy { arguments?.getInt(ARG_PRODUCT_ID) }
    private val viewModel: ProductDescriptionViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<ProductDescriptionViewModel.Factory> { factory ->
                    factory.create(productId = productId)
                }
        }
    )
    private val adapter by lazy {
        FingerprintAdapter(listOf(SpecificationFingerprint()))
    }

    private val onImageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            setPreviewTab(position)
        }
    }
    private val onTabSelectCallback = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) {
            binding.vpPreview.currentItem = tab?.position ?: 0
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
        override fun onTabReselected(tab: TabLayout.Tab?) = Unit
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tlPreviewIndicator.addOnTabSelectedListener(onTabSelectCallback)
        binding.rvSpecifications.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSpecifications.adapter = adapter
        binding.rvSpecifications.addItemDecoration(
            LineDivideItemDecoration(requireContext().toPx(1).roundToInt(), 0xFF5A5C67.toInt())
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    ProductDescriptionState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is ProductDescriptionState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        setup(state.productDescription)
                    }

                    is ProductDescriptionState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true
                    }

                    is ProductDescriptionState.AddToAssemblyPopup -> {
                        val popup = AssemblyPopup(
                            context = requireContext(),
                            assemblies = state.assemblies,
                            onCreateAssembly = { name ->
                                viewModel.createAssembly(name)
                            },
                            onSelectedAssembly = { assemblyId ->
                                viewModel.addProductToAssembly(assemblyId)
                            }
                        )
                        popup.setOnDismissListener {
                            binding.btnAddToAssembly.isEnabled = true
                        }
                        popup.show()
                    }
                }
            }
        }
        viewModel.fetchArticle()
    }


    override fun onStart() {
        super.onStart()
        binding.vpPreview.registerOnPageChangeCallback(onImageChangeCallback)
    }

    override fun onStop() {
        super.onStop()
        binding.vpPreview.unregisterOnPageChangeCallback(onImageChangeCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup(productDescription: ProductDescription) {
        with(binding) {
            tvArticleId.text = getString(R.string.article_id, productDescription.id)
            tvTitle.text = productDescription.title
            tvDescription.text = productDescription.description
            vpPreview.adapter = ImagePagerAdapter(productDescription.imageUrls)
            tvMinPrice.text = getString(R.string.min_price, productDescription.minPrice.format(2))
            btnVariants.text = getString(R.string.variants, productDescription.variantsCount)
            rbRating.rating = productDescription.rating
            tvRating.text = getString(R.string.rating_format, productDescription.rating.format(1))
            adapter.submitList(productDescription.specs.map { SpecificationItem(it.key, it.value) })
            btnVariants.setOnClickListener { onChangePage(1) }
            btnAddToAssembly.setOnClickListener {
                btnAddToAssembly.isEnabled = false
                viewModel.loadPopup()
            }

            val cornerRadius = requireContext().toPx(16)
            val topShape: ShapeAppearanceModel = ShapeAppearanceModel.builder()
                .setTopLeftCorner(CornerFamily.ROUNDED, cornerRadius)
                .setTopRightCorner(CornerFamily.ROUNDED, cornerRadius)
                .build()
            tvSpecificationsLabel.background = MaterialShapeDrawable(topShape).apply {
                fillColor = ColorStateList.valueOf(0xFF181A20.toInt())
            }
            val bottomShape: ShapeAppearanceModel = ShapeAppearanceModel.builder()
                .setBottomLeftCorner(CornerFamily.ROUNDED, cornerRadius)
                .setBottomRightCorner(CornerFamily.ROUNDED, cornerRadius)
                .build()
            rvSpecifications.background = MaterialShapeDrawable(bottomShape).apply {
                fillColor = ColorStateList.valueOf(0xFF30333B.toInt())
            }
            binding.tlPreviewIndicator.removeAllTabs()
            repeat(productDescription.imageUrls.size) {
                binding.tlPreviewIndicator.addTab(binding.tlPreviewIndicator.newTab())
            }
            setPreviewTab(0)
        }
    }

    private fun setPreviewTab(position: Int) {
        binding.tlPreviewIndicator.getTabAt(position)?.select()
    }

    companion object {
        @JvmStatic
        fun newInstance(
            productId: ProductId,
            onChangePage: (Int) -> Unit
        ): ProductDescriptionFragment = ProductDescriptionFragment(
            onChangePage = onChangePage
        ).apply {
            arguments = Bundle().apply {
                putInt(ARG_PRODUCT_ID, productId)
            }
        }
    }
}