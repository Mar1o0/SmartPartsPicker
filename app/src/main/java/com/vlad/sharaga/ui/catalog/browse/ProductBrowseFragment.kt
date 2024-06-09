package com.vlad.sharaga.ui.catalog.browse

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.ProductPreviewFingerprint
import com.vlad.sharaga.core.adapter.spinner.AutocompleteAdapter
import com.vlad.sharaga.core.adapter.spinner.SearchResultItem
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.data.ProductId
import com.vlad.sharaga.databinding.FragmentBrowseBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@AndroidEntryPoint
class ProductBrowseFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductBrowseViewModel by viewModels()
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

    private var autoCompleteAdapter: AutocompleteAdapter? = null
    private var autoCompleteTextView: AutoCompleteTextView? = null

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
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )
        binding.tvTitle.text = args.categoryItem.title

        binding.fabFilters.setOnClickListener {
            findNavController().navigate(
                ProductBrowseFragmentDirections.actionProductBrowseFragmentToFilterFragment(args.categoryItem.productType)
            )
        }

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let(viewModel::search)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        autoCompleteTextView = binding.search.getAutoCompleteTextView()
        autoCompleteTextView?.setPopupBackground(R.drawable.sh_medium)
        autoCompleteTextView?.setOnItemClickListener { _, _, position, _ ->
            val selectedItem: SearchResultItem? = autoCompleteAdapter?.getItem(position)
            if (selectedItem != null) {
                findNavController().navigate(
                    ProductBrowseFragmentDirections
                        .actionProductBrowseFragmentToProductFragment(selectedItem.productId)
                )
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    BrowseState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is BrowseState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        adapter.submitList(state.products)
                    }

                    is BrowseState.Autocomplete -> {
                        autoCompleteAdapter = AutocompleteAdapter(requireContext(), state.result)
                        autoCompleteTextView?.setAdapter(autoCompleteAdapter)
                        autoCompleteTextView?.showDropDown()
                    }

                    BrowseState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true
                    }
                }
            }
        }
        viewModel.load(args.categoryItem.productType)
    }

    private fun onProductClick(productId: ProductId) {
        findNavController().navigate(
            ProductBrowseFragmentDirections
                .actionProductBrowseFragmentToProductFragment(productId)
        )
    }

    private fun onVariantsClick(productId: ProductId) {
        findNavController().navigate(
            ProductBrowseFragmentDirections.actionProductBrowseFragmentToProductFragment(
                productId,
                1
            )
        )
    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun SearchView.getAutoCompleteTextView(): AutoCompleteTextView? {
        try {
            val field = SearchView::class.java.getDeclaredField("mSearchSrcTextView")
            field.isAccessible = true
            return field.get(this) as? AutoCompleteTextView
        } catch (_: Exception) {
            return null
        }
    }

    private fun AutoCompleteTextView.setPopupBackground(backgroundResId: Int) {
        try {
            val method = AutoCompleteTextView::class.java.getMethod("setDropDownBackgroundDrawable", android.graphics.drawable.Drawable::class.java)
            val drawable = ContextCompat.getDrawable(requireContext(), backgroundResId)
            method.invoke(this, drawable)
        } catch (_: Exception) {
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}