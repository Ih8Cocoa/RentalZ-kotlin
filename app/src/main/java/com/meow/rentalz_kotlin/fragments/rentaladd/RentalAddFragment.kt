package com.meow.rentalz_kotlin.fragments.rentaladd

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.meow.rentalz_kotlin.R
import com.meow.rentalz_kotlin.databinding.FragmentRentalAddBinding
import com.meow.rentalz_kotlin.utils.Utils
import com.meow.rentalz_kotlin.utils.Utils.hideSoftKeyboard
import java.time.LocalDate

/**
 * A simple [Fragment] subclass.
 * Use the [RentalAddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalAddFragment : Fragment() {
    private val viewModel: RentalAddViewModel by viewModels {
        RentalAddViewModelFactory()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRentalAddBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_rental_add, container, false
        )
        binding.editDate.setOnFocusChangeListener(this::datePickerFocusChangeListener)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        attachObservers()

        return binding.root
    }

    private fun attachObservers() {
        viewModel.buttonState.observe(viewLifecycleOwner, ::buttonStateObserver)
    }

    private fun buttonStateObserver(state: ButtonState?) {
        if (state == null) return
        activity.hideSoftKeyboard()
        when (state) {
            ButtonState.NEXT -> {
                val property = viewModel.property

            }
            ButtonState.CANCEL -> findNavController().navigateUp()
        }
        viewModel.resetButtonState()
    }

    private fun datePickerFocusChangeListener(view: View, hasFocus: Boolean) {
        if (!hasFocus) return
        val context = requireContext()
        val today = LocalDate.now()
        DatePickerDialog(context, ::datePickerListener, today.year, today.monthValue, today.dayOfMonth)
            .show()
    }

    private fun datePickerListener(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, month + 1, dayOfMonth).format(Utils.DATE_TIME_FORMATTER)
        viewModel.creationDate.value = date
    }
}