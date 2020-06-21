package com.meow.rentalz_kotlin.fragments.rentallist

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.meow.rentalz_kotlin.database.Property
import kotlin.reflect.KFunction1

class RentalListAdapter(private val listener: PropertyItemClickListener)
    : ListAdapter<Property, RentalListViewHolder>(PropertyDiffCallback()) {
    // helper constructor
    constructor(listener: (Long) -> Unit) : this(PropertyItemClickListener {propertyId ->
        listener(propertyId)
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = RentalListViewHolder.from(parent)

    override fun onBindViewHolder(holder: RentalListViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, listener)
    }

    private class PropertyDiffCallback : DiffUtil.ItemCallback<Property>() {
        override fun areItemsTheSame(oldItem: Property, newItem: Property) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Property, newItem: Property) = oldItem == newItem
    }
}