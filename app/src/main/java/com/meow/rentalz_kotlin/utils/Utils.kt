package com.meow.rentalz_kotlin.utils

import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
}