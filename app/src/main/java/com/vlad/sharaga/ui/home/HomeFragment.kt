package com.vlad.sharaga.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.databinding.FragmentHomeBinding
import com.vlad.sharaga.domain.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.domain.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.CategoryFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.IconTitleFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleFingerprint
import com.vlad.sharaga.domain.util.toPx
import com.vlad.sharaga.ui.catalog.CatalogFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var adapter: FingerprintAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FingerprintAdapter(getFingerprints())
        binding.rvFeed.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFeed.adapter = this@HomeFragment.adapter
        binding.rvFeed.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(32).roundToInt()
            )
        )
        binding.rvFeed.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(16).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeState.Loaded -> {
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = false
                        binding.rvFeed.isVisible = true
                        adapter.submitList(state.items)
                    }

                    HomeState.Loading -> {
                        binding.rvFeed.isVisible = false
                        binding.tvError.isVisible = false
                        binding.cpiLoading.isVisible = true
                    }

                    HomeState.Error -> {
                        binding.cpiLoading.isVisible = false
                        binding.rvFeed.isVisible = false
                        binding.tvError.isVisible = true
                    }
                }
            }
        }
    }

    private fun getFingerprints() = listOf(
        IconTitleFingerprint(),
        GameFingerprint(::onGameClick),
        ButtonFingerprint(::onMoreGameClick),
        TitleFingerprint(),
        CategoryFingerprint(::onCategoryClick),
    )

    private fun onMoreGameClick() {
        val action = HomeFragmentDirections.actionNavigationHomeToGamesFragment()
        findNavController().navigate(action)
    }

    private fun onGameClick(gameItem: GameItem) {
        val action = HomeFragmentDirections.actionNavigationHomeToNavigationCatalog(gameItem)
        findNavController().navigate(action)
    }


    private fun onCategoryClick(categoryItem: CategoryItem) {
        val action =
            HomeFragmentDirections
                .actionNavigationHomeToNavigationCatalog(
                    categoryItem = categoryItem
                )
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}