package com.smart.parts.picker.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.smart.parts.picker.BuildConfig
import com.smart.parts.picker.R
import com.smart.parts.picker.core.view.CityPopup
import com.smart.parts.picker.databinding.FragmentMoreBinding
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
                if (_binding == null) return@collect
                when (state) {
                    MoreState.Loading -> {}

                    is MoreState.Content -> {
                        binding.tvCityValue.text = state.city
                    }

                    is MoreState.ChangeCityPopup -> {
                        var popup: CityPopup? = null
                        popup = CityPopup(
                            context = requireContext(),
                            cities = state.cities,
                            onSelectedCity = { city ->
                                viewModel.onCityChange(city)
                                popup?.dismiss()
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

        binding.tvVersion.text = getString(R.string.v, BuildConfig.VERSION_NAME)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}