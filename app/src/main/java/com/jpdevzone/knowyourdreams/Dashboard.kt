package com.jpdevzone.knowyourdreams

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jpdevzone.knowyourdreams.databinding.ActivityDashboardBinding

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        bottomNavigationView = binding.bottomNavigation
        val navHostFragment = fragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
    }
}