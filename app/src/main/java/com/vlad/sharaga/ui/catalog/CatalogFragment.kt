package com.vlad.sharaga.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.databinding.FragmentCatalogBinding
import com.vlad.sharaga.domain.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.domain.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.CategoryFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.IconTitleFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleFingerprint
import com.vlad.sharaga.domain.util.toPx
import com.vlad.sharaga.ui.category.CategoryType
import com.vlad.sharaga.ui.home.HomeState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CatalogViewModel by viewModels()
    private val args: CatalogFragmentArgs by navArgs()
    private lateinit var adapter: FingerprintAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments?.containsKey("category_item") == true) {
            args.categoryItem?.let { onCategoryClick(it) }
            arguments?.clear()
            return
        }

        adapter = FingerprintAdapter(getFingerprints())
        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = adapter
        binding.rvCategories.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(
                    32
                ).roundToInt()
            )
        )
        binding.rvCategories.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is CatalogState.Loaded -> {
                        binding.search.isVisible = true
                        binding.tvTitle.isVisible = true
                        binding.rvCategories.isVisible = true
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = false
                        adapter.submitList(state.items)
                    }

                    CatalogState.Loading -> {
                        binding.search.isVisible = false
                        binding.tvTitle.isVisible = false
                        binding.rvCategories.isVisible = false
                        binding.cpiLoading.isVisible = true
                        binding.tvError.isVisible = false
                    }

                    CatalogState.Error -> {
                        binding.search.isVisible = false
                        binding.tvTitle.isVisible = false
                        binding.rvCategories.isVisible = false
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = true
                    }
                }
            }
        }
    }

    private fun getFingerprints() = listOf(
        TitleFingerprint(),
        CategoryFingerprint(::onCategoryClick),
    )

    private fun onCategoryClick(categoryItem: CategoryItem) {
        val action =
            CatalogFragmentDirections.actionNavigationCatalogToCategoryFragment(categoryItem)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}