package com.franjo.github.presentation.features.user_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.franjo.github.presentation.BaseViewHolder
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.RepositoryUI

class UserDetailsAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<BaseViewHolder>() {

    private val mDiffer = AsyncListDiffer(this, DIFF_CALLBACK)


    fun submitList(list: List<RepositoryUI>?) {
        mDiffer.submitList(list)
    }

    override fun getItemCount(): Int {
        return mDiffer.currentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_user_details, parent, false)
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = mDiffer.currentList[position]
        holder.bind(item, listener)
        holder.itemView.setOnClickListener { listener.onItemClick(item) }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<RepositoryUI> = object : DiffUtil.ItemCallback<RepositoryUI>() {
            override fun areItemsTheSame(oldItem: RepositoryUI, newItem: RepositoryUI): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepositoryUI, newItem: RepositoryUI): Boolean {
                return oldItem == newItem
            }
        }
    }

}
