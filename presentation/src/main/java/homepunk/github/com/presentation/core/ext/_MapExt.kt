package homepunk.github.com.presentation.core.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import homepunk.github.com.presentation.R
import timber.log.Timber




/**Created by Homepunk on 07.05.2019. **/

@BindingAdapter(
        "pin", "map_style",
        requireAll = false)
fun MapView.bindPinToMap(latLong: LatLng?, styleResId: Int) {
    if (latLong != null) {
        visibility = VISIBLE
        Timber.w("bindPinToMap $latLong")
        getMapAsync {
            it.mapType = GoogleMap.MAP_TYPE_NORMAL
            it.uiSettings.isZoomControlsEnabled = true

            if (styleResId != 0) {
                val style = MapStyleOptions.loadRawResourceStyle(context, styleResId);
                it.setMapStyle(style)
            }
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(latLong, 15f))
            it.addMarker(MarkerOptions()
                    .icon(bitmapDescriptorFromVector(context, R.drawable.ic_location_pin))
                    .position(latLong))
        }
    } else {
        visibility = GONE
    }
}

private fun bitmapDescriptorFromVector(context: Context, @DrawableRes vectorDrawableResourceId: Int): BitmapDescriptor {
    val background = ContextCompat.getDrawable(context, vectorDrawableResourceId)!!
    background.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
    val bitmap = Bitmap.createBitmap(background.intrinsicWidth, background.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}