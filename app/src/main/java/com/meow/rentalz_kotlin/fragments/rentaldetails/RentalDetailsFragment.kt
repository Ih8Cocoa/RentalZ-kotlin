package com.meow.rentalz_kotlin.fragments.rentaldetails

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.meow.rentalz_kotlin.database.RentalDatabase
import com.meow.rentalz_kotlin.databinding.FragmentRentalDetailsBinding

/**
 * A simple [Fragment] subclass.
 */
class RentalDetailsFragment : Fragment() {
    private val viewModel: RentalDetailsViewModel by viewModels {
        val args: RentalDetailsFragmentArgs by navArgs()
        val applicationContext = requireContext().applicationContext
        val dao = RentalDatabase.getDatabase(applicationContext).propertyDao
        val geoCoder = Geocoder(context)
        RentalDetailsViewModelFactory(dao, args.id, geoCoder)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalDetailsBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        attachObserversToViewModel()
        return binding.root
    }

    private fun attachObserversToViewModel() {
        viewModel.viewModelState.observe(viewLifecycleOwner, ::buttonStateObserver)
        viewModel.geocodeError.observe(viewLifecycleOwner, ::geocodeErrorObserver)
    }

    private fun buttonStateObserver(state: ViewModelState?) {
        if (state == null) return
        when (state) {
            ViewModelState.BACK -> handleBackButtonCase()
            ViewModelState.MAP -> handleMapButtonCase()
            ViewModelState.MODIFY -> handleModifyButtonCase()
        }
        viewModel.resetButtonState()
    }

    private fun geocodeErrorObserver(error: GeocodeErrorTypes?) {
        if (error == null) return
        val contentView: View = requireActivity().findViewById(android.R.id.content)
        val message = when (error) {
            GeocodeErrorTypes.INVALID_ADDRESS -> "The location for this property cannot be found"
            GeocodeErrorTypes.CONNECTION_ERROR -> "Disabled the map because there is no internet connection"
        }
        Snackbar.make(contentView, message, Snackbar.LENGTH_LONG).show()
        viewModel.resetGeocodeError()
    }

    private fun handleBackButtonCase() {
        this.findNavController().navigateUp()
    }

    private fun handleMapButtonCase() {
        val coordinates = requireNotNull(viewModel.coordinates.value)
        val toMapFragment = RentalDetailsFragmentDirections
            .actionRentalDetailsFragmentToRentalMapFragment(coordinates)
        findNavController().navigate(toMapFragment)
    }

    private fun handleModifyButtonCase() {
        val currentProperty = requireNotNull(viewModel.currentProperty.value)
        val toModifyFragment = RentalDetailsFragmentDirections
            .actionRentalDetailsFragmentToRentalModifyFragment(currentProperty)
        findNavController().navigate(toModifyFragment)
    }

}