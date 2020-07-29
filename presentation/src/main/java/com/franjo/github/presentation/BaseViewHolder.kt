package com.franjo.github.presentation

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


// ViewDataBinding, the base class for all generated bindings, instead of the specific ItemBinding
class BaseViewHolder(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Any?) {
        binding.setVariable(BR.item, item)
        // This is important, because it forces the data binding to execute immediately,
        // which allows the RecyclerView to make the correct view size measurements
        binding.executePendingBindings()
    }

    fun bind(item: Any?, listener: OnItemClickListener?) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.rowListener, listener)
        // This is important, because it forces the data binding to execute immediately,
        // which allows the RecyclerView to make the correct view size measurements
        binding.executePendingBindings()
    }

    fun bind(item: Any?, listener: OnIconClickListener?) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.iconListener, listener)
        // This is important, because it forces the data binding to execute immediately,
        // which allows the RecyclerView to make the correct view size measurements
        binding.executePendingBindings()
    }


}