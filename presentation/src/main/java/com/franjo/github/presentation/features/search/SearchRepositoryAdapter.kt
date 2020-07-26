package com.franjo.github.presentation.features.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.franjo.github.presentation.BaseViewHolder
import com.franjo.github.presentation.OnItemClickListener
import com.franjo.github.presentation.R
import com.franjo.github.presentation.model.RepositoryUI


// PagingDataAdapter to update the RecyclerView that presents the data
class SearchRepositoryAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<RepositoryUI, BaseViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.item_search,
            parent,
            false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, listener)
            holder.itemView.setOnClickListener { listener.onItemClick(item) }
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<RepositoryUI> =
            object : DiffUtil.ItemCallback<RepositoryUI>() {
                override fun areItemsTheSame(
                    oldItem: RepositoryUI,
                    newItem: RepositoryUI
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: RepositoryUI,
                    newItem: RepositoryUI
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}