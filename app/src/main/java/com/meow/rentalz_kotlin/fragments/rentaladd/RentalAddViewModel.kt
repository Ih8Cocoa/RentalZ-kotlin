package com.meow.rentalz_kotlin.fragments.rentaladd

import androidx.lifecycle.*
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.utils.ErrorType
import com.meow.rentalz_kotlin.utils.Utils
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeParseException

class RentalAddViewModel : ViewModel() {
    val propertyType = MutableLiveData("")
    val bedroomType = MutableLiveData("")
    val furnitureType = MutableLiveData("")
    val notes = MutableLiveData("")
    val reporterName = MutableLiveData("")
    val address = MutableLiveData("")
    val monthlyPrice = MutableLiveData("")
    val creationDate = MutableLiveData("")

    // Error reporting
    val propertyTypeValid: LiveData<ErrorType> = propertyType.map(::validateString)
    val bedroomTypeValid: LiveData<ErrorType> = bedroomType.map(::validateString)
    val reporterNameValid: LiveData<ErrorType> = reporterName.map(::validateString)
    val monthlyPriceValid: LiveData<ErrorType> = monthlyPrice.map(::validatePrice)
    val creationDateValid: LiveData<ErrorType> = creationDate.map(::validateDate)

    private val _validationStatuses = MediatorLiveData<BooleanArray>()
    private val _buttonState = MutableLiveData<ButtonState>()

    // getters
    val validationStatuses: LiveData<BooleanArray>
        get() = _validationStatuses
    val buttonState: LiveData<ButtonState>
        get() = _buttonState
    val property: Property
        get() = Property(
            propertyType, bedroomType, creationDate, address,
            monthlyPrice, furnitureType, notes, reporterName
        )

    init {
        // Decide whether or not the submit button is available
        // requires a set to mark the invalid values
        val initialValidationStatuses = BooleanArray(5)
        _validationStatuses.value = initialValidationStatuses
        _validationStatuses.addSource(propertyTypeValid, updateValidationStatusLambdaFor(0))
        _validationStatuses.addSource(bedroomTypeValid, updateValidationStatusLambdaFor(1))
        _validationStatuses.addSource(reporterNameValid, updateValidationStatusLambdaFor(2))
        _validationStatuses.addSource(monthlyPriceValid, updateValidationStatusLambdaFor(0))
        _validationStatuses.addSource(creationDateValid, updateValidationStatusLambdaFor(0))
    }

    fun onNextButtonClicked() {
        _buttonState.value = ButtonState.NEXT
    }

    fun onCancelButtonClicked() {
        _buttonState.value = ButtonState.CANCEL
    }

    fun resetButtonState() {
        _buttonState.value = null
    }

    private fun validateString(str: String): ErrorType {
        val isEmpty = str.trim().isEmpty()
        return if (isEmpty) ErrorType.ILLEGAL_DATA else ErrorType.NONE
    }

    private fun validatePrice(str: String): ErrorType {
        val d = str.toDoubleOrNull()
        return when {
            d == null -> {
                ErrorType.INVALID_FORMAT
            }
            d > 0 -> {
                ErrorType.NONE
            }
            else -> {
                ErrorType.ILLEGAL_DATA
            }
        }
    }

    private fun validateDate(str: String): ErrorType {
        return try {
            LocalDate.parse(str, Utils.DATE_TIME_FORMATTER)
            ErrorType.NONE
        } catch (e: DateTimeParseException) {
            ErrorType.INVALID_FORMAT
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