package homepunk.github.com.presentation.feature.detail.event.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.google.android.gms.maps.model.LatLng

/**Created by Homepunk on 08.05.2019. **/
data class VenueModel(var displayName: String? = "",
                      var lat: Double? = null,
                      var lng: Double? = null,
                      var phone: String? = "",
                      var website: String? = "",
                      private var street: String? = "",
                      private var city: String? = null) : BaseObservable() {

    @Bindable
    fun getAddress() = "$street${if (street.isNullOrEmpty()) "" else ", "}$city"

    @Bindable
    fun getLocation() = if (lat != null && lng != null) {
        LatLng(lat!!, lng!!)
    } else {
        null
    }

    @Bindable
    fun getCity() = if (city != null) city!! else ""
}