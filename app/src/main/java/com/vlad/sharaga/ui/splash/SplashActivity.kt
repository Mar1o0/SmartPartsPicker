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
import com.vlad.sharaga.core.view.CityPopup
import com.vlad.sharaga.ui.NavHostActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashScreenViewModel by viewModels()
    private var keepSplashScreen = true
    private var popup: CityPopup? = null

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

                    SplashScreenState.Dismiss -> popup?.dismiss()
                }
            }
        }
    }

    private fun setCitiesOptions(cityNames: List<String>) {
        keepSplashScreen = false
        popup = CityPopup(
            context = this,
            cities = cityNames,
            onSelectedCity = { name ->
                viewModel.onAcceptClicked(name)
            }
        ).apply {
            setOnDismissListener {
                viewModel.onDefault()
            }
            setCancelable(false)
            show()
        }
    }

    private fun openNavHostActivity() {
        val intent = Intent(this, NavHostActivity::class.java)
        startActivity(intent)
        finish()
    }
}