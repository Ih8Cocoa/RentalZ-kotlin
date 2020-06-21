package com.meow.rentalz_kotlin.utils

import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object Utils {
    val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy")
    val FULL_FORMATTER = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
}