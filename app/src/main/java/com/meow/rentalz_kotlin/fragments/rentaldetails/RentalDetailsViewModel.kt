package com.meow.rentalz_kotlin.fragments.rentaldetails

import android.location.Geocoder
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.meow.rentalz_kotlin.database.Property
import com.meow.rentalz_kotlin.database.PropertyDao
import com.meow.rentalz_kotlin.utils.Utils.fromAddress
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.IOException

class RentalDetailsViewModel(propertyId: Long, dao: PropertyDao, private val geocoder: Geocoder) :
    ViewModel() {
    private val _currentProperty: LiveData<Property> = liveData {
        dao.findPropertyById(propertyId)
    }
    private val _coordinates = _currentProperty.switchMap(::getEventualCoordinates)

    private val _viewModelState = MutableLiveData<ViewModelState>()
    private val _geocodeError = MutableLiveData<GeocodeErrorTypes>()

    // getters
    val currentProperty: LiveData<Property>
        get() = _currentProperty
    val viewModelState: LiveData<ViewModelState>
        get() = _viewModelState
    val coordinates: LiveData<LatLng>
        get() = _coordinates
    val geocodeError: LiveData<GeocodeErrorTypes>
        get() = _geocodeError

    // update view model data
    fun onClose() {
        _viewModelState.value = ViewModelState.BACK
    }

    fun onModifyButtonPressed() {
        _viewModelState.value = ViewModelState.MODIFY
    }

    fun onMapButtonPressed() {
        _viewModelState.value = ViewModelState.MAP
    }

    fun resetButtonState() {
        _viewModelState.value = null
    }

    fun resetGeocodeError() {
        _geocodeError.value = null
    }

    private fun getEventualCoordinates(property: Property?): LiveData<LatLng> {
        val rtn = MutableLiveData<LatLng>()
        viewModelScope.launch {
            if (property == null) return@launch
            val address = property.address
            if (address.isNotBlank()) {
                try {
                    val latLngResult = geocoder.fromAddress(address)
                    Timber.i("Coordinates retrieved: %s", latLngResult)
                    if (latLngResult == null) {
                        _geocodeError.postValue(GeocodeErrorTypes.INVALID_ADDRESS)
                    } else {
                        rtn.postValue(latLngResult)
                    }
                } catch (e: IOException) {
                    Timber.e("Encountered I/O problems")
                    _geocodeError.postValue(GeocodeErrorTypes.CONNECTION_ERROR)
                    rtn.postValue(null)
                }
            }
        }
        return rtn
    }
}
