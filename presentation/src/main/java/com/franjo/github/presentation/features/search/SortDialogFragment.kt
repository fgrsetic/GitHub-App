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
import com.franjo.github.presentation.databinding.FragmentSortDialogBinding
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SortDialogFragment : DialogFragment() {

  companion object {
    val TAG: String = SortDialogFragment::class.java.simpleName
  }

  private var _binding: FragmentSortDialogBinding? = null
  private val binding get() = _binding!!

  @Inject
  lateinit var sharedPrefs: ISharedPrefs

  override fun onAttach(context: Context) {
    AndroidSupportInjection.inject(this)
    super.onAttach(context)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    isCancelable = true
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    _binding = FragmentSortDialogBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    when (sharedPrefs.getValue(SORT_REPO_KEY, SORT_STARS) as String) {
      SORT_STARS -> binding.rbStars.isChecked = true
      SORT_FORKS -> binding.rbForks.isChecked = true
      SORT_UPDATES -> binding.rbUpdates.isChecked = true
    }

    binding.rbStars.setOnClickListener {
      sharedPrefs.saveValue(SORT_REPO_KEY, SORT_STARS)
      dialog?.dismiss()
    }

    binding.rbForks.setOnClickListener {
      sharedPrefs.saveValue(SORT_REPO_KEY, SORT_FORKS)
      dialog?.dismiss()
    }

    binding.rbUpdates.setOnClickListener {
      sharedPrefs.saveValue(SORT_REPO_KEY, SORT_UPDATES)
      dialog?.dismiss()
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }
}
