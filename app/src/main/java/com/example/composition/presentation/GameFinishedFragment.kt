package com.example.composition.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.composition.R
import com.example.composition.databinding.FragmentGameFinishedBinding

class GameFinishedFragment : Fragment() {
    private val args by navArgs<GameFinishedFragmentArgs>()

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")


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
            args.gameResult.gameSettings.minCountOfRightAnswers)
        binding.tvScoreAnswers.text =
            String.format(getString(R.string.score_answers), args.gameResult.countOfRightAnswers)
        binding.tvRequiredPercentage.text = String.format(getString(R.string.required_percentage),
            args.gameResult.gameSettings.minPercentOfRightAnswers)
        binding.tvScorePercentage.text =
            String.format(getString(R.string.score_percentage), getPercentOfRightAnswers())
    }

    private fun getPercentOfRightAnswers(): Int{
        return with(args.gameResult){
            if (countOfQuestions == 0){
                0
            } else{
                ((countOfRightAnswers/countOfQuestions.toDouble())*100).toInt()
            }
        }
    }

    private fun setEmoji() {
        binding.emojiResult.setImageResource(
            if (args.gameResult.winner) R.drawable.ic_smile else R.drawable.ic_sad)
    }

    private fun setOnBackClickRetryGame() {
        binding.buttonRetry.setOnClickListener { retryGame() }
    }

    private fun retryGame() {
        findNavController().popBackStack()
    }
}