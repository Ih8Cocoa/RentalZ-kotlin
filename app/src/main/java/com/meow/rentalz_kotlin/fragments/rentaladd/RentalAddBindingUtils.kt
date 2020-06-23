package com.meow.rentalz_kotlin.fragments.rentaladd

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import com.meow.rentalz_kotlin.utils.ErrorType
import timber.log.Timber

object RentalAddBindingUtils {
    @BindingAdapter("propertyTypeValidation")
    fun TextInputLayout.validatePropertyType(errorType: ErrorType) {
        val errorMessage = when(errorType) {
            ErrorType.NONE -> null
            ErrorType.ILLEGAL_DATA -> "The property type is empty"
            else -> {
                Timber.e("Error occurred in AddRentalBindingUtils#validatePropertyType(). Reached the 'default' case, for some reasons")
                throw IllegalStateException("Why are you running?");
            }
        }
        error = errorMessage
        isErrorEnabled = errorMessage != null
    }

    @BindingAdapter("bedroomTypeValidation")
    fun TextInputLayout.validateBedroomType(errorType: ErrorType) {
        val errorMessage = when(errorType) {
            ErrorType.NONE -> null
            ErrorType.ILLEGAL_DATA -> "The bedroom type is empty"
            else -> {
                Timber.e("Error occurred in AddRentalBindingUtils#validateBedroomType(). Reached the 'default' case, for some reasons")
                throw IllegalStateException("Why are you running?");
            }
        }
        error = errorMessage
        isErrorEnabled = errorMessage != null
    }

    @BindingAdapter("priceValidation")
    fun TextInputLayout.validatePrice(errorType: ErrorType) {
        val errorMessage = when(errorType) {
            ErrorType.NONE -> null
            ErrorType.INVALID_FORMAT -> "The property price must be a valid decimal number"
            ErrorType.ILLEGAL_DATA -> "The property price must be bigger than 0"
        }
        error = errorMessage
        isErrorEnabled = errorMessage != null
    }

    @BindingAdapter("dateValidation")
    fun TextInputLayout.validateLocalDate(errorType: ErrorType) {
        val errorMessage = when(errorType) {
            ErrorType.NONE -> null
            ErrorType.INVALID_FORMAT -> "The built date must be a valid date in d/M/yyyy format"
            else -> {
                Timber.e("Error occurred in AddRentalBindingUtils#validateLocalDate. Reached the 'default' case")
                throw IllegalStateException("Why are you running?");
            }
        }
        error = errorMessage
        isErrorEnabled = errorMessage != null
    }

    @BindingAdapter("reporterNameValidation")
    fun TextInputLayout.validateReporterName(errorType: ErrorType) {
        val errorMessage = when(errorType) {
            ErrorType.NONE -> null
            ErrorType.ILLEGAL_DATA -> "The reporter name is empty"
            else -> {
                Timber.e("Error occurred in AddRentalBindingUtils#validateReporterName(). Reached the 'default' case, for some reasons")
                throw IllegalStateException("Why are you running?");
            }
        }
        error = errorMessage
        isErrorEnabled = errorMessage != null
    }
}