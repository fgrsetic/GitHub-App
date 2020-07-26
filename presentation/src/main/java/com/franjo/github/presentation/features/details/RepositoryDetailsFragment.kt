package com.franjo.github.presentation.features.details

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentRepositoryDetailsBinding

class RepositoryDetailsFragment :
    BaseFragment<FragmentRepositoryDetailsBinding, RepositoryDetailsViewModel>() {

    override fun getFragmentView(): Int = R.layout.fragment_repository_details
    override fun getViewModel(): Class<RepositoryDetailsViewModel> = RepositoryDetailsViewModel::class.java

//    private val viewModel: RepositoryDetailsViewModel by lazy {
//        ViewModelProvider(this).get(RepositoryDetailsViewModel::class.java)
//    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


}