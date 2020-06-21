package com.meow.rentalz_kotlin.fragments.rentallist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.meow.rentalz_kotlin.R
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.RentalDatabase
import com.meow.rentalz_kotlin.databinding.FragmentRentalListBinding

/**
 * A simple [Fragment] subclass.
 */
class RentalListFragment : Fragment() {

    private val viewModel: RentalListViewModel by viewModels {
        val context = requireContext()
        val dao = RentalDatabase.getDatabase(context).propertyDao
        RentalListViewModelFactory(dao)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentRentalListBinding = FragmentRentalListBinding.inflate(
            inflater, container, false
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerViewContext = binding.rentalListRecyclerView.context
        val decoration =
            DividerItemDecoration(recyclerViewContext, DividerItemDecoration.HORIZONTAL)
        binding.rentalListRecyclerView.addItemDecoration(decoration)

        val adapter = RentalListAdapter(viewModel::onPropertyItemClicked)
        binding.rentalListRecyclerView.adapter = adapter
        addObservers(adapter)

        return binding.root
    }

    private fun addObservers(adapter: RentalListAdapter) {
        val propertyListObserver = { list: List<Property>? ->
            if (list != null) {
                adapter.submitList(list)
            }
        }

        viewModel.allProperties.observe(viewLifecycleOwner, propertyListObserver)
        viewModel.viewModelState.observe(viewLifecycleOwner, ::buttonObserver)
        viewModel.navigateToPropertyDetails.observe(viewLifecycleOwner, ::propertyItemClickedObserver)
    }

    private fun propertyItemClickedObserver(id: Long?) {
        if (id == null || id < 1) {
            return
        }
    }

    private fun buttonObserver(state: ViewModelState?) {
        if (state == null) return
        when (state) {
            ViewModelState.ADD -> {

            }
            ViewModelState.CLEAR_BUTTON_PRESSED -> {
                val activity = requireActivity()
                AlertDialog.Builder(activity)
                    .setMessage("Clear the database?")
                    .setPositiveButton("yes") { dialog, _ ->
                        dialog.dismiss()
                        viewModel.clearDatabase()
                    }
                    .setNegativeButton("no") { dialog, _ ->
                        dialog.cancel()
                    }
                    .show()
            }
            ViewModelState.DB_CLEARED -> {
                val contentView: View = requireActivity().findViewById(android.R.id.content)
                Snackbar.make(contentView, R.string.cleared_message, Snackbar.LENGTH_LONG).show()
            }
        }
        viewModel.resetButtonState()
    }
}