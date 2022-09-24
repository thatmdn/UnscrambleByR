package com.example.unscramblefromr.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblefromr.R
import com.example.unscramblefromr.databinding.FragmentGameBinding

/**
 * Fragment with main playing game logic
 */
class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding

    private var score = 0
    private var currentWordCount = 0
    private var currentScrambledWord = "test"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }

        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(R.string.words_count, 0, MAX_NO_OF_WORDS)

        updateScrambledWord()
    }

    /**
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        binding.wordCount.text = getString(R.string.words_count, currentWordCount, MAX_NO_OF_WORDS)
        setErrorTextField(false)
        updateScrambledWord()
    }

    /**
     * Checks the user's word, and updates the score accordingly.
     * Displays the next scrambled word.
     */
    private fun onSubmitWord() {
        currentScrambledWord = getNextScrambledWord()
        currentWordCount++
        score += SCORE_INCREASE

        binding.wordCount.text = getString(R.string.words_count, currentWordCount, MAX_NO_OF_WORDS)
        binding.score.text = getString(R.string.score, score)

        setErrorTextField(false)
        updateScrambledWord()
    }

    /**
     * Gets new word from the wordList and shuffles letters in it
     */
    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }

    /**
     * Updates text in the scrambled word textField
     */
    private fun updateScrambledWord() {
        binding.scrambledWord.text = currentScrambledWord
    }

    /**
     * Sets error state in the textField
     */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textInput.isErrorEnabled = true
            binding.textInput.error = getString(R.string.try_again)
        } else {
            binding.textInput.isErrorEnabled = false
            binding.textInput.error = null
        }
    }

    companion object {
        // Max word's count per game
        const val MAX_NO_OF_WORDS = 10

        // Score increase value
        const val SCORE_INCREASE = 20

    }
}