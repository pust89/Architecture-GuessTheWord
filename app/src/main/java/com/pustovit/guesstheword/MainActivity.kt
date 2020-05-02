package com.pustovit.guesstheword

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.pustovit.guesstheword.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar = mBinding.toolbar
        setSupportActionBar(toolbar)
        navController = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupWithNavController(toolbar, navController)

    }

}
