package com.meow.rentalz_kotlin.fragments.rentalconfirm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.PropertyDao

class RentalConfirmViewModelFactory(private val dao: PropertyDao, private val property: Property) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val assignable = modelClass.isAssignableFrom(RentalConfirmViewModel::class.java)
        if (assignable) {
            return RentalConfirmViewModel(dao, property) as T
        }
        throw IllegalArgumentException("Invalid ViewModel detected")
    }
}