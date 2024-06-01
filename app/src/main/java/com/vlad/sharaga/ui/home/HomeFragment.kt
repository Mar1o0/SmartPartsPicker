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
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.FragmentHomeBinding
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.CategoryFingerprint
import com.vlad.sharaga.core.adapter.recycler.fingerprints.CategoryItem
import com.vlad.sharaga.core.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.core.adapter.recycler.fingerprints.GameItem
import com.vlad.sharaga.core.util.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    private val gamesAdapter by lazy {
        FingerprintAdapter(
            listOf(
                GameFingerprint(::onGameClick)
            )
        )
    }
    private val categoriesAdapter by lazy {
        FingerprintAdapter(
            listOf(
                CategoryFingerprint(::onCategoryClick)
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findNavController().clearBackStack(R.id.navigation_home)
    }

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

        binding.rvGames.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGames.adapter = gamesAdapter
        binding.rvGames.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(32).roundToInt()
            )
        )
        binding.rvGames.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(16).roundToInt()
            )
        )

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvGames.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(32).roundToInt()
            )
        )
        binding.rvGames.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(16).roundToInt()
            )
        )

        binding.btnMoreGames.setOnClickListener {
            val action = HomeFragmentDirections.actionNavigationHomeToGamesFragment()
            findNavController().navigate(action)
        }


        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeState.Loaded -> {
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = false
                        binding.nsContent.isVisible = true

                        gamesAdapter.submitList(state.games)
                        categoriesAdapter.submitList(state.categories)
                    }

                    HomeState.Loading -> {
                        binding.nsContent.isVisible = false
                        binding.tvError.isVisible = false
                        binding.cpiLoading.isVisible = true
                    }

                    is HomeState.Error -> {
                        binding.cpiLoading.isVisible = false
                        binding.nsContent.isVisible = false
                        binding.tvError.isVisible = true

                        binding.tvError.text = state.message
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}