package com.franjo.github.presentation.features.user.private_user

import android.content.Context
import android.os.Bundle
import android.view.Menu
import com.franjo.github.domain.repository.IEncryptedPrefs
import com.franjo.github.domain.shared.ACCESS_TOKEN_KEY
import com.franjo.github.presentation.BaseFragment
import com.franjo.github.presentation.R
import com.franjo.github.presentation.databinding.FragmentPrivateUserBinding
import com.franjo.github.presentation.features.user.public_user.UserDetailsAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PrivateUserFragment : BaseFragment<FragmentPrivateUserBinding>() {

    override fun getFragmentView(): Int = R.layout.fragment_private_user

    @Inject
    lateinit var viewModel: PrivateUserViewModel

    @Inject
    lateinit var encryptedPrefs: IEncryptedPrefs


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        val token = encryptedPrefs.getValue(ACCESS_TOKEN_KEY, "")
        viewModel.loadPrivateUser(token)

        binding.rvUserDetails.adapter =
            UserDetailsAdapter()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.actionWeb).isVisible = false
        menu.findItem(R.id.actionLogin).isVisible = false
        menu.findItem(R.id.actionPrivateUser).isVisible = false
    }

}