package com.pustovit.guesstheword.screens.game

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.pustovit.guesstheword.R
import com.pustovit.guesstheword.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private lateinit var mBinding: FragmentGameBinding
    private lateinit var mViewModel: GameFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mViewModel = ViewModelProvider(
            this,
            GameFragmentViewModelFactory(context)
        ).get(GameFragmentViewModel::class.java)

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel


        mViewModel.buzzLiveData.observe(
            viewLifecycleOwner,
            Observer { pattern: LongArray -> buzz(pattern) })

        mViewModel.gameFinish.observe(
            viewLifecycleOwner,
            Observer { isGameFinish ->
                if (isGameFinish) gameFinish()
                mViewModel.onGameFinishComplete()
            })
        return mBinding.root
    }


    private fun gameFinish() {

        mBinding.gameBtnSkip.isEnabled = false
        mBinding.gameBtnGuessed.isEnabled = false
        val guessedWords: Array<String> = mViewModel.getGuessedWords()
        val skippedWords: Array<String> = mViewModel.getSkippedWords()

        view?.findNavController()?.navigate(
            GameFragmentDirections.actionGameFragmentToScoreFragment(
                guessedWords,
                skippedWords
            )
        )
    }


    private fun buzz(pattern: LongArray) {
        val vibrator: Vibrator? = activity?.getSystemService<Vibrator>()

        vibrator?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                vibrator.vibrate(pattern, -1)
            }
        }
    }
}
