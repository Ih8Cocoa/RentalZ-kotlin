package com.meow.rentalz_kotlin.fragments.rentallist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.databinding.ListItemPropertyBinding

class RentalListViewHolder(private val binding: ListItemPropertyBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(property: Property, callback: PropertyItemClickListener) {
        binding.item = property
        binding.callback = callback
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): RentalListViewHolder {
            val parentContext = parent.context
            val inflater = LayoutInflater.from(parentContext)
            val binding = ListItemPropertyBinding.inflate(inflater, parent, false)
            return RentalListViewHolder(binding)
        }
    }
}