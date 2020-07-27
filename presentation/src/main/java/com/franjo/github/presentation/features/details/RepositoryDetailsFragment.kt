package com.franjo.github.presentation.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentRepositoryDetailsBinding
import javax.inject.Inject

class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsViewModel>() {

    override fun getFragmentView(): Int = R.layout.fragment_repository_details
    override fun getViewModel(): Class<RepositoryDetailsViewModel> = RepositoryDetailsViewModel::class.java

    @Inject


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val repoProperty = RepositoryDetailsFragmentArgs.fromBundle(requireArguments()).repository

    }


}