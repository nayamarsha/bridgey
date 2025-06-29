package com.example.bridgey2

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
//import com.example.bridgey2.Fragments.ScheduleFragment
//import com.example.bridgey2.Fragments.HomeFragment
import com.example.bridgey2.MainActivity
//import com.example.bridgey2.Fragments.PostFragment
//import com.example.bridgey2.Fragments.StatusFragment
import com.example.bridgey2.Fragments.SearchFragment
import com.example.bridgey2.databinding.ActivityMainBinding


import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        // no action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = binding.bottomNavigation

        NavigationUI.setupWithNavController(bottomNav, navController)


        bottomNav.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.searchFragment -> {
                    navController.navigate(R.id.searchFragment)
                    true
                }
                R.id.postFragment -> {
                    navController.navigate(R.id.postFragment)
                    true
                }
                R.id.scheduleFragment -> {
                    navController.navigate(R.id.scheduleFragment)
                    true
                }
                R.id.statusFragment -> {
                    // Force kembali ke StatusFragment dari manapun
                    navController.navigate(
                        R.id.statusFragment,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.statusFragment, true)
                            .setLaunchSingleTop(true)
                            .build()
                    )
                    true
                }
                else -> false
            }
        }


    }
}