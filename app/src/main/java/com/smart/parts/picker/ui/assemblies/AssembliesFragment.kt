package com.smart.parts.picker.ui.assemblies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.smart.parts.picker.R
import com.smart.parts.picker.databinding.FragmentAssembliesBinding
import com.smart.parts.picker.core.adapter.recycler.FingerprintAdapter
import com.smart.parts.picker.core.adapter.recycler.decorations.VerticalDividerItemDecoration
import com.smart.parts.picker.core.adapter.recycler.fingerprints.AssemblyFingerprint
import com.smart.parts.picker.core.adapter.recycler.fingerprints.AssemblyItem
import com.smart.parts.picker.core.adapter.recycler.util.SwipeToDelete
import com.smart.parts.picker.core.util.toPx
import com.smart.parts.picker.core.view.AssemblyPopup
import com.smart.parts.picker.core.view.AssemblyPopupType
import dagger.hilt.android.AndroidEntryPoint
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
            VerticalDividerItemDecoration(
                requireContext().toPx(
                    16
                ).roundToInt()
            )
        )

        val onItemSwipedToDelete: (Int) -> Unit = { positionForRemove: Int ->
            val removedItem = adapter.currentList[positionForRemove] as? AssemblyItem
            removedItem?.id?.let(viewModel::deleteAssembly)
        }
        val swipeToDeleteCallback = SwipeToDelete(R.layout.item_assembly, onItemSwipedToDelete)
        ItemTouchHelper(swipeToDeleteCallback).attachToRecyclerView(binding.rvAssemblies)

        binding.btnNewAssembly.setOnClickListener {
            binding.btnNewAssembly.isEnabled = false
            val popup = AssemblyPopup(
                requireContext(),
                AssemblyPopupType.CREATE,
                onCreateAssembly = { name ->
                    viewModel.createAssembly(name)
                }
            )
            popup.setOnDismissListener {
                binding.btnNewAssembly.isEnabled = true
            }
            popup.show()
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is AssembliesState.Loading -> with(binding) {
                        cpiLoading.isVisible = true
                        nsContent.isVisible = false
                        tvError.isVisible = false
                    }

                    is AssembliesState.Content -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = true
                        tvError.isVisible = false

                        tvCounter.text = state.assemblies.size.toString()
                        adapter.submitList(state.assemblies)
                    }

                    is AssembliesState.Error -> with(binding) {
                        cpiLoading.isVisible = false
                        nsContent.isVisible = false
                        tvError.isVisible = true

                        tvError.text = getString(R.string.error_no_internet)
                    }
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

    private fun onAssemblyClick(assemblyId: Long) {
        findNavController().navigate(
            AssembliesFragmentDirections.actionNavigationAssembliesToAssemblyBrowseFragment(
                assemblyId
            )
        )
    }
}