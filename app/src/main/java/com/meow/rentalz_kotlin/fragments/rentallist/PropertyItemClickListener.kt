package com.meow.rentalz_kotlin.fragments.rentallist

import com.meow.rentalz_kotlin.database.Property

class PropertyItemClickListener(private val callback: (Long) -> Unit) {
    fun onClick(property: Property) {
        callback(property.id)
    }
}