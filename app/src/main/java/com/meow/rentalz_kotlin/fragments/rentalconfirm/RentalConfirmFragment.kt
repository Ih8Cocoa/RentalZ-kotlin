package com.meow.rentalz_kotlin.fragments.rentalconfirm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.meow.rentalz_kotlin.database.RentalDatabase
import com.meow.rentalz_kotlin.databinding.FragmentRentalConfirmBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RentalConfirmFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RentalConfirmFragment : Fragment() {
    private val viewModel: RentalConfirmViewModel by viewModels {
        val args: RentalConfirmFragmentArgs by navArgs()
        val context = requireContext()
        val dao = RentalDatabase.getDatabase(context).propertyDao
        RentalConfirmViewModelFactory(dao, args.property)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRentalConfirmBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        attachObservers()

        return binding.root
    }

    private fun attachObservers() {
        viewModel.buttonClicked.observe(viewLifecycleOwner, ::buttonStateObservers)
    }

    private fun buttonStateObservers(state: ButtonState?) {
        if (state == null) return
        when (state) {
            ButtonState.SUBMIT -> {
                val backHome = RentalConfirmFragmentDirections
                    .actionRentalConfirmFragmentToRentalListFragment()
                findNavController().navigate(backHome)
            }
            ButtonState.BACK -> findNavController().navigateUp()
        }
        viewModel.resetButtonState()
    }
}