package com.vlad.sharaga.ui.assemblies

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
import com.vlad.sharaga.databinding.FragmentAssembliesBinding
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.decorations.HorizontalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.vlad.sharaga.core.adapter.recycler.fingerprints.AssemblyFingerprint
import com.vlad.sharaga.core.util.toPx
import com.vlad.sharaga.ui.assemblies.browse.AssemblyBrowseViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class AssembliesFragment : Fragment() {

    private var _binding: FragmentAssembliesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AssembliesViewModel by viewModels()
    private val adapter: FingerprintAdapter by lazy {
        FingerprintAdapter(
            listOf(
                AssemblyFingerprint(
                    onAssemblyClick = ::onAssemblyClick
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAssembliesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAssemblies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAssemblies.adapter = adapter
        binding.rvAssemblies.addItemDecoration(
            HorizontalDividerItemDecoration(
                requireContext().toPx(
                    32
                ).roundToInt()
            )
        )
        binding.rvAssemblies.addItemDecoration(
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AssembliesState.Loading -> {
                        binding.cpiLoading.isVisible = true
                        binding.tvError.isVisible = false
                        binding.rvAssemblies.isVisible = false
                    }

                    is AssembliesState.Error -> {
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = true
                        binding.rvAssemblies.isVisible = false
                    }

                    is AssembliesState.Content -> {
                        binding.cpiLoading.isVisible = false
                        binding.tvError.isVisible = false
                        binding.rvAssemblies.isVisible = true
                        binding.tvCounter.text = state.assemblies.size.toString()
                        adapter.submitList(state.assemblies)
                    }

                    AssembliesState.Empty -> Unit
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onAssemblyClick(assemblyId: Int) {
        findNavController().navigate(
            AssembliesFragmentDirections.actionNavigationAssembliesToAssemblyBrowseFragment(
                assemblyId
            )
        )
    }
}