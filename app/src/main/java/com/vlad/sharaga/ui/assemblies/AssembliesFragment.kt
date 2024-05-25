package com.vlad.sharaga.ui.assemblies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vlad.sharaga.databinding.FragmentAssembliesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AssembliesFragment : Fragment() {

    private var _binding: FragmentAssembliesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AssembliesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAssembliesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        viewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}