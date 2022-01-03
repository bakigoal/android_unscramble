package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = 0
    private var _currentWordCount = 0
    private var wordsList = mutableListOf<String>()
    private lateinit var currentWord: String
    private lateinit var _currentScrambledWord: String

    val score: Int
        get() = _score
    val currentWordCount: Int
        get() = _currentWordCount
    val currentScrambledWord: String
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
        return if (currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.filter { !wordsList.contains(it) }.random()
        wordsList.add(currentWord)
        _currentWordCount++
        _currentScrambledWord = scrambleWord(currentWord)
    }

    private fun scrambleWord(currentWord: String): String {
        val chars = currentWord.toCharArray()
        while (String(chars).equals(currentWord, false)) {
            chars.shuffle()
        }
        return String(chars)
    }

    private fun increaseScore() {
        _score += SCORE_INCREASE
    }
}