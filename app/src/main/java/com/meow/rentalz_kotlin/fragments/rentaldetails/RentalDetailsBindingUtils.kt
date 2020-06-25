package com.meow.rentalz_kotlin.fragments.rentaldetails

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.meow.rentalz_kotlin.database.Property

object RentalDetailsBindingUtils {
    @BindingAdapter("shouldHideView")
    @JvmStatic
    fun TextView.shouldHideView(data: String?) {
        visibility = if (data?.isBlank() == true) View.VISIBLE else View.GONE
    }

    @BindingAdapter("disableModifyButton")
    @JvmStatic
    fun Button.disableModifyButtonIfNull(p: Property?) {
        isEnabled = p != null
    }
}