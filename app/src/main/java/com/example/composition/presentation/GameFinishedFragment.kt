package com.example.composition.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.composition.R
import com.example.composition.databinding.FragmentChooseLevelBinding
import com.example.composition.databinding.FragmentGameFinishedBinding
import com.example.composition.domain.entity.GameResult
import com.example.composition.domain.entity.GameSettings

class GameFinishedFragment : Fragment() {

    private lateinit var gameResult: GameResult

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnBackClickRetryGame()
        setEmoji()
        setGameResult()
    }

    private fun setGameResult() {
        binding.tvRequiredAnswers.text = String.format(getString(R.string.required_score),
            gameResult.gameSettings.minCountOfRightAnswers)
        binding.tvScoreAnswers.text =
            String.format(getString(R.string.score_answers), gameResult.countOfRightAnswers)
        binding.tvRequiredPercentage.text = String.format(getString(R.string.required_percentage),
            gameResult.gameSettings.minPercentOfRightAnswers)
        binding.tvScorePercentage.text =
            String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())
    }

    private fun getPercentOfRightAnswers(): Int{
        return with(gameResult){
            if (countOfQuestions == 0){
                0
            } else{
                ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
            }
        }
    }

    private fun setEmoji() {
        binding.emojiResult.setImageResource(
            if (gameResult.winner) R.drawable.ic_smile else R.drawable.ic_sad)
    }

    private fun setOnBackClickRetryGame() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                retryGame()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        binding.buttonRetry.setOnClickListener { retryGame() }
    }

    private fun retryGame() {
        requireActivity().supportFragmentManager.popBackStack(GameFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    private fun parseArgs() {
        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
            gameResult = it
        }
    }

    companion object {
        private const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}