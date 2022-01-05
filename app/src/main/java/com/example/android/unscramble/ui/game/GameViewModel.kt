package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var wordsList = mutableListOf<String>()
    private lateinit var currentWord: String

    private var _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private var _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    private var _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.filter { !wordsList.contains(it) }.random()
        wordsList.add(currentWord)
        _currentWordCount.value = _currentWordCount.value?.inc()
        _currentScrambledWord.value = scrambleWord(currentWord)
    }

    private fun scrambleWord(currentWord: String): String {
        val chars = currentWord.toCharArray()
        while (String(chars).equals(currentWord, false)) {
            chars.shuffle()
        }
        return String(chars)
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }
}