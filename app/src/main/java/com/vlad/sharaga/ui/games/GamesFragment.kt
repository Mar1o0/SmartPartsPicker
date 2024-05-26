package com.vlad.sharaga.ui.games

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.FragmentGamesBinding
import com.vlad.sharaga.domain.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.domain.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameItem
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleFingerprint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GamesViewModel by viewModels()

    private lateinit var adapter: FingerprintAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FingerprintAdapter(getFingerprints())
        binding.rvGames.layoutManager = LinearLayoutManager(requireContext())
        binding.rvGames.adapter = adapter
        binding.rvGames.addItemDecoration(HorizontalDividerItemDecoration(90))
        binding.rvGames.addItemDecoration(VerticalDividerItemDecoration(50))

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is GamesState.Loading -> {
                        binding.rvGames.isVisible = false
                        binding.tvError.isVisible = false
                        binding.cpiLoading.isVisible = true
                    }

                    is GamesState.Loaded -> {
                        binding.rvGames.isVisible = true
                        binding.tvError.isVisible = false
                        binding.cpiLoading.isVisible = false
                        adapter.submitList(state.items)
                    }

                    is GamesState.Error -> {
                        binding.rvGames.isVisible = false
                        binding.tvError.isVisible = true
                        binding.cpiLoading.isVisible = false
                    }
                }
            }
        }
    }

    private fun getFingerprints() = listOf(
        TitleFingerprint(),
        GameFingerprint(::onGameClick),
    )

    private fun onGameClick(gameItem: GameItem) {
        val action = GamesFragmentDirections.actionGamesFragmentToNavigationCatalog(gameItem)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = GamesFragment()
    }
}