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
            VerticalDividerItemDecoration(
                requireContext().toPx(16).roundToInt()
            )
        )

        binding.rvCategories.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCategories.adapter = categoriesAdapter
        binding.rvCategories.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(16).roundToInt()
            )
        )

        binding.btnMoreGames.setOnClickListener {
//            val action = HomeFragmentDirections.actionNavigationHomeToGamesFragment()
//            findNavController().navigate(action)
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    HomeState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is HomeState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        gamesAdapter.submitList(state.games)
                        categoriesAdapter.submitList(state.categories)
                    }

                    is HomeState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true

                        tvError.text = state.message
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

    private fun onGameClick(gameItem: GameItem) {
        val action =
            HomeFragmentDirections
                .actionNavigationHomeToNavigationCatalog(
                    gameItem = gameItem
                )
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