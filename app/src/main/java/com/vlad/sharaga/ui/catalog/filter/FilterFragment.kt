package com.vlad.sharaga.ui.catalog.filter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.vlad.sharaga.R
import com.vlad.sharaga.core.adapter.recycler.FingerprintAdapter
import com.vlad.sharaga.core.adapter.recycler.fingerprints.GameFingerprint
import com.vlad.sharaga.databinding.FragmentBrowseBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentBrowseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilterViewModel by viewModels()
    private val args: FilterFragmentArgs by navArgs()
    private val adapter: FingerprintAdapter by lazy {
        FingerprintAdapter(getFingerprints())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBrowseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFingerprints() = listOf(
        GameFingerprint{}
    )
}