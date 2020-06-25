package com.meow.rentalz_kotlin.fragments.rentalconfirm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.PropertyDao
import kotlinx.coroutines.launch

class RentalConfirmViewModel(private val dao: PropertyDao, val property: Property) :
    ViewModel() {
    private val _buttonClicked = MutableLiveData<ButtonState>()

    val buttonClicked: LiveData<ButtonState>
        get() = _buttonClicked

    fun onSubmitButtonClicked() {
        viewModelScope.launch {
            dao.insert(property)
            _buttonClicked.postValue(ButtonState.SUBMIT)
        }
    }

    fun onBackButtonClicked() {
        _buttonClicked.value = ButtonState.BACK
    }

    fun resetButtonState() {
        _buttonClicked.value = null
    }
}