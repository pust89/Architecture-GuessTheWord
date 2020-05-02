package com.pustovit.guesstheword.screens.score

import androidx.lifecycle.ViewModel

class ScoreViewModel(
    private val _guessedWords: Array<String>,
    private val _skippedWords: Array<String>
) : ViewModel() {
    val score = _guessedWords.size - _skippedWords.size

    val guessedWords: String
        get() = _guessedWords.joinToString()
    val skippedWords: String
        get() = _skippedWords.joinToString()
}