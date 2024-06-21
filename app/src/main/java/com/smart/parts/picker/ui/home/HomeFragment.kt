package com.smart.parts.picker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smart.parts.picker.R
import com.smart.parts.picker.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

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

        binding.etValue.doOnTextChanged { text, _, _, _ ->
            val value = text.toString().toIntOrNull() ?: 0
            if (value <= 0) {
                binding.btnSearch.isEnabled = false
                binding.tiValue.error = getString(R.string.budget_error)
                return@doOnTextChanged
            } else {
                binding.btnSearch.isEnabled = true
                binding.tiValue.error = null
            }
        }

        binding.btnSearch.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToBudgetAssembliesFragment(
                    budgetPrice = binding.etValue.text.toString().toFloatOrNull() ?: 0f
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}