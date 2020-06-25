package com.meow.rentalz_kotlin.fragments.rentalmodify

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.meow.rentalz_kotlin.R
import com.meow.rentalz_kotlin.database.RentalDatabase
import com.meow.rentalz_kotlin.databinding.FragmentRentalModifyBinding
import com.meow.rentalz_kotlin.utils.Utils
import com.meow.rentalz_kotlin.utils.Utils.hideSoftKeyboard
import java.time.LocalDate

/**
 * A simple [Fragment] subclass.
 * Use the [RentalModifyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalModifyFragment : Fragment() {
    private val viewModel: RentalModifyViewModel by viewModels {
        val args: RentalModifyFragmentArgs by navArgs()
        val context = requireContext()
        val dao = RentalDatabase.getDatabase(context).propertyDao
        RentalModifyViewModelFactory(dao, args.property)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalModifyBinding.inflate(inflater, container, false)

        binding.editDate.setOnFocusChangeListener(::datePickerFocusChangeListener)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        attachObservers()

        return binding.root
    }

    private fun attachObservers() {
        viewModel.buttonState.observe(viewLifecycleOwner, ::buttonStateObservers)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun datePickerFocusChangeListener(view: View, hasFocus: Boolean) {
        if (!hasFocus) return
        val context = requireContext()
        val today = LocalDate.now()
        DatePickerDialog(
            context,
            ::datePickerListener,
            today.year,
            today.monthValue,
            today.dayOfMonth
        )
            .show()
    }

    @Suppress("UNUSED_PARAMETER")
    private fun datePickerListener(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val date = LocalDate.of(year, month + 1, dayOfMonth).format(Utils.DATE_TIME_FORMATTER)
        viewModel.creationDate.value = date
    }

    private fun buttonStateObservers(state: ButtonState?) {
        if (state == null) return
        activity.hideSoftKeyboard()
        when (state) {
            ButtonState.BACK, ButtonState.EDIT -> findNavController().navigateUp()
            ButtonState.DELETE_BUTTON_CLICKED -> {
                val activity = requireActivity()
                AlertDialog.Builder(activity)
                    .setMessage("Delete this rental?")
                    .setPositiveButton("yes") { dialog, _ ->
                        dialog.dismiss()
                        viewModel.onDeletionConfirmed()
                    }
                    .setNegativeButton("no") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
            ButtonState.DELETION_COMPLETED -> {
                val backHome = RentalModifyFragmentDirections
                    .actionRentalModifyFragmentToRentalListFragment()
                findNavController().navigate(backHome)
            }
        }
        viewModel.resetButtonState()
    }
}