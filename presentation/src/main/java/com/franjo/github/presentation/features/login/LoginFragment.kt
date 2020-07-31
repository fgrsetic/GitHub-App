package com.franjo.github.presentation.features.login

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentLoginBinding
import com.franjo.github.presentation.features.user_details.UserDetailsAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_login

    @Inject
    lateinit var viewModel: LoginViewModel


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        binding.rvUserDetails.adapter = UserDetailsAdapter()

    }

}