package com.meow.rentalz_kotlin.fragments.rentalmodify

import androidx.lifecycle.*
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.PropertyDao
import com.meow.rentalz_kotlin.utils.ErrorType
import com.meow.rentalz_kotlin.utils.Utils
import kotlinx.coroutines.launch
import timber.log.Timber

class RentalModifyViewModel(private val dao: PropertyDao, property: Property) : ViewModel() {
    val id = property.id
    val propertyType = MutableLiveData(property.propertyType)
    val bedroomType = MutableLiveData(property.bedroomType)
    val monthlyPrice = MutableLiveData(property.monthlyPrice.toPlainString())
    val furnitureType = MutableLiveData(property.furnitureType)
    val notes = MutableLiveData(property.notes)
    val reporterName = MutableLiveData(property.reporterName)
    val address = MutableLiveData(property.address)
    val creationDate = MutableLiveData(
        property.creationDate?.format(Utils.DATE_TIME_FORMATTER) ?: ""
    )

    // Validation LiveData
    val propertyTypeValid: LiveData<ErrorType> = propertyType.map(Utils::validateString)
    val bedroomTypeValid: LiveData<ErrorType> = bedroomType.map(Utils::validateString)
    val reporterNameValid: LiveData<ErrorType> = reporterName.map(Utils::validateString)
    val monthlyPriceValid: LiveData<ErrorType> = monthlyPrice.map(Utils::validatePrice)
    val creationDateValid: LiveData<ErrorType> = creationDate.map(Utils::validateDate)

    private val _validationStatuses = MediatorLiveData<BooleanArray>()
    private val _buttonState = MutableLiveData<ButtonState>()

    // abstracted getters
    val validationStatuses: LiveData<BooleanArray>
        get() = _validationStatuses
    val buttonState: LiveData<ButtonState>
        get() = _buttonState

    init {
        _validationStatuses.value = BooleanArray(5)
        _validationStatuses.addSource(propertyTypeValid, updateValidationStatusLambdaFor(0))
        _validationStatuses.addSource(bedroomTypeValid, updateValidationStatusLambdaFor(1))
        _validationStatuses.addSource(monthlyPriceValid, updateValidationStatusLambdaFor(2))
        _validationStatuses.addSource(creationDateValid, updateValidationStatusLambdaFor(3))
        _validationStatuses.addSource(reporterNameValid, updateValidationStatusLambdaFor(4))
    }

    // click listeners
    fun onEditButtonClicked() {
        val p = Property(
            propertyType, bedroomType, creationDate, address,
            monthlyPrice, furnitureType, notes, reporterName
        )
        p.id = id
        viewModelScope.launch {
            dao.update(p)
            _buttonState.postValue(ButtonState.EDIT)
        }
    }

    fun onDeleteButtonClicked() {
        _buttonState.value = ButtonState.DELETE_BUTTON_CLICKED
    }

    fun onBackButtonClicked() {
        _buttonState.value = ButtonState.BACK
    }

    fun resetButtonState() {
        _buttonState.value = null
    }

    fun onDeletionConfirmed() {
        viewModelScope.launch {
            dao.deletePropertyById(id)
            _buttonState.postValue(ButtonState.DELETION_COMPLETED)
        }
    }

    private fun updateValidationStatusLambdaFor(position: Int): (ErrorType) -> Unit {
        fun rtnFunction(errorType: ErrorType) {
            val currentArray = _validationStatuses.value
            if (currentArray == null) {
                Timber.e("Current array is null")
                return
            }
            // out-of-bound check
            if (currentArray.size < position) {
                Timber.e("Out-of-bound access detected")
                return
            }

            val oldValue = currentArray[position]
            val newValue = errorType == ErrorType.NONE

            if (oldValue == newValue) return;
            currentArray[position] = newValue
            // Inform LiveData that the value was changed
            _validationStatuses.value = currentArray
        }

        return ::rtnFunction
    }
}