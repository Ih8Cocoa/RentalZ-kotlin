package com.meow.rentalz_kotlin.converters

import androidx.room.TypeConverter
import com.meow.rentalz_kotlin.utils.Utils
import java.math.BigDecimal
import java.time.LocalDate

object RoomTypeConverters {
    @TypeConverter
    @JvmStatic
    fun toDate(str: String?) = str?.let {
        LocalDate.parse(str, Utils.DATE_TIME_FORMATTER)
    }

    @TypeConverter
    @JvmStatic
    fun toDateString(date: LocalDate?) = date?.format(Utils.DATE_TIME_FORMATTER)

    @TypeConverter
    @JvmStatic
    fun currencyToString(d: BigDecimal?) = d?.toPlainString()

    @TypeConverter
    @JvmStatic
    fun strToCurrency(str: String?) = str?.let {
        BigDecimal(str)
    }
}