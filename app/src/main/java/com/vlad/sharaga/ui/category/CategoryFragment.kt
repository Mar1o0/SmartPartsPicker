package com.vlad.sharaga.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.vlad.sharaga.databinding.FragmentCategoryBinding
import com.vlad.sharaga.domain.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.domain.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.domain.adapter.recycler.fingerprints.ArticleFingerprint
import com.vlad.sharaga.domain.util.toPx
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CategoryViewModel by viewModels()
    private val args: CategoryFragmentArgs by navArgs()
    private lateinit var adapter: FingerprintAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FingerprintAdapter(getFingerprints())
        binding.rvArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.rvArticles.adapter = adapter
        binding.rvArticles.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(
                    32
                ).roundToInt()
            )
        )
        binding.rvArticles.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )
        binding.tvTitle.text = args.categoryItem.title

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    CategoryState.Loading -> {
                        binding.cpiLoading.isVisible = true
                        binding.rvArticles.isVisible = false
                        binding.tvError.isVisible = false
                    }

                    is CategoryState.Loaded -> {
                        binding.cpiLoading.isVisible = false
                        binding.rvArticles.isVisible = true
                        binding.tvError.isVisible = false
                        adapter.submitList(state.items)
                    }

                    CategoryState.Error -> {
                        binding.cpiLoading.isVisible = false
                        binding.rvArticles.isVisible = false
                        binding.tvError.isVisible = true
                    }
                }
            }
        }
        viewModel.fetchCategory(args.categoryItem.categoryType.id)
    }

    private fun getFingerprints() = listOf(
        ArticleFingerprint()
    )


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}