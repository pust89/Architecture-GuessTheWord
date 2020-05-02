package com.pustovit.guesstheword.screens.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ScoreViewModelFactory(val guessedWords:Array<String>, val skippedWords:Array<String>) : ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ScoreViewModel::class.java)){
            return ScoreViewModel(guessedWords,skippedWords) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}