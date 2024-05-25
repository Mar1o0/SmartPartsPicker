package com.vlad.sharaga.ui

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.vlad.sharaga.R
import com.vlad.sharaga.databinding.ActivityNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavHostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navView: BottomNavigationView = binding.navView

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_catalog,
                R.id.navigation_assemblies,
                R.id.navigation_more
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val backgroundShapeModel: ShapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(CornerFamily.ROUNDED, toPx(16))
            .setTopRightCorner(CornerFamily.ROUNDED, toPx(16))
            .build()
        navView.background = MaterialShapeDrawable(backgroundShapeModel).apply {
            fillColor = ColorStateList.valueOf(0xFF101010.toInt())
        }

//        ViewCompat.setOnApplyWindowInsetsListener(navView) { view, insets ->
//            val imeVisible = insets.isVisible(WindowInsetsCompat.Type.ime())
//            val navigationBarsInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
//
//            // Apply padding only when IME is not visible to avoid height increase
//            view.setPadding(0, 0, 0, if (imeVisible) 0 else navigationBarsInsets.bottom)
//            insets
//        }
    }
}

fun Context.toPx(dp: Int): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp.toFloat(),
    resources.displayMetrics
)