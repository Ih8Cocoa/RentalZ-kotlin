package com.meow.rentalz_kotlin.fragments.rentallist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meow.rentalz_kotlin.database.PropertyDao

class RentalListViewModelFactory(private val dao: PropertyDao) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val isAssignable = modelClass.isAssignableFrom(RentalListViewModel::class.java)
        if (isAssignable) {
            return RentalListViewModel(dao) as T
        }
        throw IllegalArgumentException("Invalid ViewModel detected")
    }

}