package com.meow.rentalz_kotlin.fragments.rentaldetails

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.meow.rentalz_kotlin.database.Property

object RentalDetailsBindingUtils {
    @BindingAdapter("shouldHideView")
    fun TextView.shouldHideView(data: String) {
        visibility = if (data.isBlank()) View.VISIBLE else View.GONE
    }

    @BindingAdapter("disableModifyButton")
    fun Button.disableModifyButtonIfNull(p: Property?) {
        isEnabled = p != null
    }
}