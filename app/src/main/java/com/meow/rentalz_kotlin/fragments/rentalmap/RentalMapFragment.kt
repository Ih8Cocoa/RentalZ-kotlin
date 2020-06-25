package com.meow.rentalz_kotlin.fragments.rentalmap

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*

import com.google.android.gms.maps.model.MarkerOptions
import com.meow.rentalz_kotlin.databinding.FragmentRentalMapBinding

class RentalMapFragment : Fragment() {
    private lateinit var mapView: MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val args: RentalMapFragmentArgs by navArgs()
        val binding = FragmentRentalMapBinding.inflate(inflater, container, false)
        mapView = binding.rentalMap
        // propagate the call to the view
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { gmap ->
            val zoomer = CameraUpdateFactory.newLatLngZoom(args.coordinates,
                ZOOM_LEVEL
            )
            val marker = MarkerOptions().position(args.coordinates)
            gmap.moveCamera(zoomer)
            gmap.addMarker(marker)
            // set the map type to hybrid
            gmap.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val ZOOM_LEVEL = 15f
    }
}