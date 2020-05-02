package com.pustovit.guesstheword.screens.game


import android.content.Context
import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pustovit.guesstheword.R
import timber.log.Timber
import kotlin.random.Random
import kotlin.random.nextInt


class GameFragmentViewModel(private val context: Context?) : ViewModel() {

    companion object {
        const val ONE_SECOND = 1000L
        const val TIME_LIMIT = 120000L
        const val TIME_COUNTDOWN_PANIC = 15L
        const val DONE = 0L

        private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
        private val PANIC_BUZZ_PATTERN = longArrayOf(100, 200, 200, 200, 200, 200)
        private val GAME_OVER_BUZZ_PATTERN = longArrayOf(100, 2000)

    }

    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),

    }

    private val _wordLiveData = MutableLiveData<String>()

    val wordLiveData: LiveData<String>
        get() {
            return _wordLiveData
        }
    private val timeLiveData = MutableLiveData<Long>()


    val timeLiveDataString: LiveData<String> =
        Transformations.map(timeLiveData, { DateUtils.formatElapsedTime(it) })

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val gameFinish: LiveData<Boolean>
        get() {
            return _eventGameFinish
        }

    private val _buzzLiveData: MutableLiveData<LongArray> = MutableLiveData()
    val buzzLiveData: LiveData<LongArray> = _buzzLiveData

    private var listOfWord: MutableList<String>
    private var currentWord: String = ""


    private var guessedWords: MutableList<String>
    private var skippedWords: MutableList<String>

    private var timer: CountDownTimer


    init {
        Timber.i("init block called")
        _eventGameFinish.postValue(false)
        guessedWords = mutableListOf()
        skippedWords = mutableListOf()


        listOfWord = mutableListOf()
        val words: Array<String> = context?.resources!!.getStringArray(R.array.word_list)
        listOfWord.addAll(words)
        listOfWord.shuffle()



        timer = object : CountDownTimer(TIME_LIMIT, ONE_SECOND) {
            override fun onFinish() {
                _buzzLiveData.postValue(BuzzType.GAME_OVER.pattern)
                timeLiveData.postValue(DONE)
                _eventGameFinish.postValue(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                if (millisUntilFinished / ONE_SECOND == TIME_COUNTDOWN_PANIC) _buzzLiveData.postValue(BuzzType.COUNTDOWN_PANIC.pattern)
                timeLiveData.postValue(millisUntilFinished / ONE_SECOND)
            }

        }
        timer.start()
        getNewWord()

    }

    private fun getNewWord() {
        if (listOfWord.size > 0) {
            val randomIndex = Random.nextInt(listOfWord.indices)
            currentWord = listOfWord[randomIndex]
            listOfWord.remove(currentWord)
        } else {
            _eventGameFinish.postValue(true)
        }
        _wordLiveData.postValue(currentWord)
    }


    fun guessedWord() {
        _buzzLiveData.postValue( BuzzType.CORRECT.pattern)
        guessedWords.add(currentWord)
        getNewWord()
    }

    fun skippedWord() {
        skippedWords.add(currentWord)
        getNewWord()
    }

    internal fun getGuessedWords(): Array<String> {
        return guessedWords.toTypedArray()
    }

    internal fun getSkippedWords(): Array<String> {
        return skippedWords.toTypedArray()
    }


    fun onGameFinishComplete() {
        _eventGameFinish.postValue(false)
    }


    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }
}