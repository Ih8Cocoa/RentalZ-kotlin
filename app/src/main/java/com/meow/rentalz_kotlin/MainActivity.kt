package com.meow.rentalz_kotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.meow.rentalz_kotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        drawerLayout = binding.drawerLayout
        val navController = findNavController(R.id.nav_host_fragment)
        setupActionBarWithNavController(navController, drawerLayout)
        binding.navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        hideSoftKeyboard()
        return findNavController(R.id.nav_host_fragment).navigateUp(drawerLayout)
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(InputMethodManager::class.java)
        val windowToken = currentFocus?.windowToken
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }
}