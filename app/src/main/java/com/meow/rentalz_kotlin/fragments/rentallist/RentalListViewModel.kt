package com.meow.rentalz_kotlin.fragments.rentallist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meow.rentalz_kotlin.database.PropertyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RentalListViewModel(private val propertyDao: PropertyDao) : ViewModel() {
    private val _navigateToPropertyDetails = MutableLiveData<Long>()
    private val _viewModelState = MutableLiveData<ViewModelState>()

    val allProperties = propertyDao.allProperties()

    val navigateToPropertyDetails: LiveData<Long>
        get() = _navigateToPropertyDetails

    val viewModelState: LiveData<ViewModelState>
        get() = _viewModelState

    fun onPropertyItemClicked(id: Long) {
        _navigateToPropertyDetails.value = id
    }

    fun doneNavigatingToPropertyDetails() {
        _navigateToPropertyDetails.value = null
    }

    fun resetButtonState() {
        _viewModelState.value = null
    }

    fun onAddButtonClicked() {
        _viewModelState.value = ViewModelState.ADD
    }

    fun onClearButtonClicked() {
        _viewModelState.value = ViewModelState.CLEAR_BUTTON_PRESSED
    }

    fun clearDatabase() {
        viewModelScope.launch {
            deleteAllPropertiesWithIoContext()
            _viewModelState.value = ViewModelState.DB_CLEARED
        }
    }

    private suspend fun deleteAllPropertiesWithIoContext() = withContext(Dispatchers.IO) {
        propertyDao.deleteAll()
    }
}