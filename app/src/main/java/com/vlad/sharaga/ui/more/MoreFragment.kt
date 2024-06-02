package com.vlad.sharaga.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.vlad.sharaga.core.view.CityPopup
import com.vlad.sharaga.databinding.FragmentMoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment : Fragment() {

    private var _binding: FragmentMoreBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangeCity.setOnClickListener {
            binding.btnChangeCity.isEnabled = false
            viewModel.changeCity()
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    MoreState.Loading -> {}

                    is MoreState.Content -> {
                        binding.tvCityValue.text = state.city
                    }

                    is MoreState.ChangeCityPopup -> {
                        val popup = CityPopup(
                            context = requireContext(),
                            cities = state.cities,
                            onSelectedCity = { city ->
                                viewModel.onCityChange(city)
                            }
                        )
                        popup.setOnDismissListener {
                            binding.btnChangeCity.isEnabled = true
                        }
                        popup.show()
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
}