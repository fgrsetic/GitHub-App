package com.franjo.github.presentation.features.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.franjo.github.presentation.R

class SortDialogFragment : DialogFragment(), RadioGroup.OnCheckedChangeListener {


    private lateinit var viewModel: SortDialogViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sort_dialog_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SortDialogViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        //        viewModel.updateFilter(
//            when (item.itemId) {
//                R.id.action_sort -> openSortDialog()
//                R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
//                R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
//                else -> MarsApiFilter.SHOW_ALL
//            }
    }


}