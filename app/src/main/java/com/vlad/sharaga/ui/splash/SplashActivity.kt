package com.vlad.sharaga.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.vlad.sharaga.databinding.ActivitySplashBinding
import com.vlad.sharaga.domain.adapter.spinner.CitiesSpinnerAdapter
import com.vlad.sharaga.ui.NavHostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashScreenViewModel by viewModels()
    private var keepSplashScreen = true

    private lateinit var citiesSpinnerAdapter: CitiesSpinnerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { keepSplashScreen }
        enableEdgeToEdge()

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    SplashScreenState.Loading -> Unit

                    is SplashScreenState.LocationChooser -> setCitiesOptions(state.cityNames)

                    SplashScreenState.Dispose -> openNavHostActivity()
                }
            }
        }

        binding.btnAccept.setOnClickListener {
            binding.btnAccept.isEnabled = false
            viewModel.onAcceptClicked(
                citiesSpinnerAdapter.getItem(
                    binding.spCitiesOptions.selectedItemPosition
                )
            )
        }
    }

    private fun setCitiesOptions(cityNames: List<String>) {
        citiesSpinnerAdapter = CitiesSpinnerAdapter(
            context = this,
            cityNames = cityNames
        )
        binding.spCitiesOptions.adapter = citiesSpinnerAdapter
        keepSplashScreen = false
    }

    private fun openNavHostActivity() {
        val intent = Intent(this, NavHostActivity::class.java)
        startActivity(intent)
        finish()
    }
}