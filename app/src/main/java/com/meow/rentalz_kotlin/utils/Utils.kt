package com.meow.rentalz_kotlin.utils

import android.app.Activity
import android.location.Geocoder
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle

object Utils {
    val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    private val FULL_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)

    suspend fun Geocoder.fromAddress(address: String): LatLng? {
        if (address.isBlank()) return null
        return withContext(Dispatchers.IO) {
            val matchLocations = getFromLocationName(address, 1)
            if (matchLocations.isEmpty()) {
                null
            } else {
                val topResult = matchLocations[0]
                LatLng(topResult.latitude, topResult.longitude)
            }

        }
    }

    @JvmStatic
    fun convertToFullString(date: LocalDate?): String {
        if (date == null) return ""
        return date.format(FULL_FORMATTER)
    }

    @JvmStatic
    fun hasNoErrors(arr: BooleanArray) = arr.all { true }

    @JvmStatic
    fun Activity?.hideSoftKeyboard() {
        if (this == null) return

        val imm = getSystemService(InputMethodManager::class.java)
        if (imm != null && currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    fun validateString(str: String): ErrorType {
        val isEmpty = str.trim().isEmpty()
        return if (isEmpty) ErrorType.ILLEGAL_DATA else ErrorType.NONE
    }

    fun validatePrice(str: String): ErrorType {
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

    fun validateDate(str: String): ErrorType {
        return try {
            LocalDate.parse(str, Utils.DATE_TIME_FORMATTER)
            ErrorType.NONE
        } catch (e: DateTimeParseException) {
            ErrorType.INVALID_FORMAT
        }
    }
}