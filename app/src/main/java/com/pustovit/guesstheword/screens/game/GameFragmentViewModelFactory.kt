package com.pustovit.guesstheword.screens.game

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GameFragmentViewModelFactory(private val context: Context?) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = GameFragmentViewModel (context)
        return viewModel as T
    }
}