package com.meow.rentalz_kotlin.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.time.LocalDate

@Entity(tableName = "property_table")
@Fts4
@Parcelize
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    val id: Long = 0,

    val propertyType: String = "",
    val bedroomType: String = "",
    val creationDate: LocalDate? = null,
    val address: String = "",
    val monthlyPriceUsd: BigDecimal = BigDecimal.ZERO,
    val furnitureType: String = "",
    val notes: String = "",
    val reporterName: String = ""
) : Parcelable