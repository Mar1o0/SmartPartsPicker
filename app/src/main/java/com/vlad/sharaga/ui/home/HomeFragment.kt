package com.vlad.sharaga.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.databinding.FragmentHomeBinding
import com.vlad.sharaga.domain.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.domain.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ButtonFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.FeedFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.IconTitleFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.SearchFingerprint
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.TitleFingerprint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        binding.rvFeed.addItemDecoration(HorizontalDividerItemDecoration(90))
        binding.rvFeed.addItemDecoration(VerticalDividerItemDecoration(50))

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is HomeState.Loaded -> {
                        binding.rvFeed.isVisible = true
                        binding.cpiLoading.isVisible = false
                        adapter.submitList(state.items)
                    }

                    HomeState.Loading -> {
                        binding.cpiLoading.isVisible = true
                        binding.rvFeed.isVisible = false
                    }
                }
            }
        }
    }

    private fun getFingerprints() = listOf(
        SearchFingerprint(),
        IconTitleFingerprint(),
        GameFingerprint(),
        ButtonFingerprint(),
        TitleFingerprint(),
        FeedFingerprint(),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}