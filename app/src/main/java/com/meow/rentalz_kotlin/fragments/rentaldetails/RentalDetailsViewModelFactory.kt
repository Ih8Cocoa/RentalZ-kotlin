package com.meow.rentalz_kotlin.fragments.rentaldetails

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.meow.rentalz_kotlin.database.PropertyDao

class RentalDetailsViewModelFactory(
    private val dao: PropertyDao,
    private val propertyId: Long,
    private val geocoder: Geocoder
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val isAssignable = modelClass.isAssignableFrom(RentalDetailsViewModel::class.java)
        if (isAssignable) {
            return RentalDetailsViewModel(propertyId, dao, geocoder) as T
        }
        throw IllegalArgumentException("Invalid ViewModel detected")
    }
}