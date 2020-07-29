package com.franjo.github.presentation.features.user_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.franjo.github.presentation.BaseViewHolder
import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.UserDataRowItem

class UserDetailsAdapter() : RecyclerView.Adapter<BaseViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<UserDataRowItem>?) {
        mDiffer.submitList(list)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.item_user_details,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = mDiffer.currentList[position]
        holder.bind(item)
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<UserDataRowItem> =
            object : DiffUtil.ItemCallback<UserDataRowItem>() {
                override fun areItemsTheSame(
                    oldItem: UserDataRowItem,
                    newItem: UserDataRowItem
                ): Boolean {
                    return oldItem.description == newItem.description
                }

                override fun areContentsTheSame(
                    oldItem: UserDataRowItem,
                    newItem: UserDataRowItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}
