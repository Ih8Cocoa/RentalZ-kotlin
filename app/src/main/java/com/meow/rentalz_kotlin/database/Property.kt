package com.meow.rentalz_kotlin.database

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.*
import com.meow.rentalz_kotlin.utils.Utils
import kotlinx.android.parcel.Parcelize
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Entity(tableName = "property_table")
@Fts4
@Parcelize
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rowid")
    var id: Long = 0,

    var propertyType: String = "",
    var bedroomType: String = "",
    var creationDate: LocalDate? = null,
    var address: String = "",
    var monthlyPrice: BigDecimal = BigDecimal.ZERO,
    var furnitureType: String = "",
    var notes: String = "",
    var reporterName: String = ""
) : Parcelable {
    @Ignore
    constructor(
        propertyType: LiveData<String>,
        bedroomType: LiveData<String>,
        localDateStr: LiveData<String>,
        address: LiveData<String>,
        monthlyPrice: LiveData<String>,
        furnitureType: LiveData<String>,
        notes: LiveData<String>,
        reporterName: LiveData<String>
    ) : this() {
        val formatter: DateTimeFormatter = Utils.DATE_TIME_FORMATTER
        val creationDate = LocalDate.parse(requireNotNull(localDateStr.value), formatter)
        val priceDecimal = BigDecimal(requireNotNull(monthlyPrice.value))
        this.propertyType = requireNotNull(propertyType.value)
        this.bedroomType = requireNotNull(bedroomType.value)
        this.creationDate = creationDate
        this.address = requireNotNull(address.value)
        this.monthlyPrice = priceDecimal
        this.furnitureType = requireNotNull(furnitureType.value)
        this.reporterName = requireNotNull(reporterName.value)
        this.notes = requireNotNull(notes.value)
    }
}