package com.pustovit.guesstheword.screens.title

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.pustovit.guesstheword.R
import com.pustovit.guesstheword.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var mBinding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)

        mBinding.startGame.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_titleFragment_to_gameFragment))

        return mBinding.root
    }

}
