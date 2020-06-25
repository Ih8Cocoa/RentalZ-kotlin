package com.meow.rentalz_kotlin.fragments.rentalmodify

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.PropertyDao

class RentalModifyViewModelFactory(
    private val propertyDao: PropertyDao,
    private val property: Property
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val isAssignable = modelClass.isAssignableFrom(RentalModifyViewModel::class.java)
        if (isAssignable) {
            return RentalModifyViewModel(propertyDao, property) as T
        }
        throw IllegalArgumentException("Invalid ViewModel detected")
    }
}