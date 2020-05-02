package com.pustovit.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.pustovit.guesstheword.R
import com.pustovit.guesstheword.databinding.FragmentScoreBinding

/**
 * A simple [Fragment] subclass.
 */
class ScoreFragment : Fragment() {

    private lateinit var mBinding: FragmentScoreBinding
    private lateinit var mViewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_score, container, false)
        mBinding.scoreBtnNewGame.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_scoreFragment_to_gameFragment))

        var args = ScoreFragmentArgs.fromBundle(requireArguments())
        mViewModel = ViewModelProvider(
            this,
            ScoreViewModelFactory(args.guessedWords, args.skippedWords)
        ).get(ScoreViewModel::class.java)

        mBinding.viewModel=mViewModel

        return mBinding.root
    }


}
