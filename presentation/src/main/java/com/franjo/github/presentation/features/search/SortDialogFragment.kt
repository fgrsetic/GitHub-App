package com.franjo.github.presentation.features.search

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.franjo.github.domain.repository.ISharedPrefs
import com.franjo.github.domain.shared.SORT_FORKS
import com.franjo.github.domain.shared.SORT_REPO_KEY
import com.franjo.github.domain.shared.SORT_STARS
import com.franjo.github.domain.shared.SORT_UPDATES
import com.franjo.github.presentation.databinding.SortDialogFragmentBinding
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.sort_dialog_fragment.*
import javax.inject.Inject


class SortDialogFragment : DialogFragment() {

    companion object {
        val TAG: String = SortDialogFragment::class.java.simpleName
    }

    @Inject
    lateinit var sharedPrefs: ISharedPrefs

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = true
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val binding = SortDialogFragmentBinding.inflate(inflater)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        when (sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS) as String) {
            SORT_STARS -> rb_stars.isChecked = true
            SORT_FORKS -> rb_forks.isChecked = true
            SORT_UPDATES -> rb_updates.isChecked = true
        }

        rb_stars.setOnClickListener {
            sharedPrefs.saveValue(SORT_REPO_KEY, SORT_STARS)
            dialog?.dismiss()
        }

        rb_forks.setOnClickListener {
            sharedPrefs.saveValue(SORT_REPO_KEY, SORT_FORKS)
            dialog?.dismiss()
        }

        rb_updates.setOnClickListener {
            sharedPrefs.saveValue(SORT_REPO_KEY, SORT_UPDATES)
            dialog?.dismiss()
        }

    }

}

