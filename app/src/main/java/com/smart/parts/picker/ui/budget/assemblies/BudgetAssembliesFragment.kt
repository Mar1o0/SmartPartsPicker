package com.smart.parts.picker.ui.budget.assemblies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.BudgetAssemblyFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.BudgetAssemblyItem
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.databinding.FragmentBudgetAssembliesBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.withCreationCallback
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class BudgetAssembliesFragment : Fragment() {

    private var _binding: FragmentBudgetAssembliesBinding? = null
    private val binding get() = _binding!!

    private val args: BudgetAssembliesFragmentArgs by navArgs()
    private val viewModel: BudgetAssembliesViewModel by viewModels(
        extrasProducer = {
            defaultViewModelCreationExtras
                .withCreationCallback<BudgetAssembliesViewModel.Factory> { factory ->
                    factory.create(budgetPrice = args.budgetPrice)
                }
        }
    )

    private val adapter by lazy {
        FingerprintAdapter(
            listOf(
                BudgetAssemblyFingerprint(::onBudgetAssemblyClick)
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBudgetAssembliesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAssemblies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAssemblies.adapter = adapter
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
                    is BudgetBrowseState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is BudgetBrowseState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        tvCounter.text = state.assemblies.size.toString()
                        adapter.submitList(state.assemblies)
                    }

                    is BudgetBrowseState.Error -> with(binding) {
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

    private fun onBudgetAssemblyClick(item: BudgetAssemblyItem) {
        findNavController().navigate(
            BudgetAssembliesFragmentDirections
                .actionBudgetAssembliesFragmentToBudgetBrowseFragment(item)
        )
    }
}