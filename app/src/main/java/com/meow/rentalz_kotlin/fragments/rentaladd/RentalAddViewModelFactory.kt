package com.meow.rentalz_kotlin.fragments.rentaladd

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RentalAddViewModelFactory : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val isAssignable = modelClass.isAssignableFrom(RentalAddViewModel::class.java)
        if (isAssignable) {
            return RentalAddViewModel() as T
        }
        throw IllegalArgumentException("Incorrect ViewModel detected")
    }
}