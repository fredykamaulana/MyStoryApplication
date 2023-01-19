package com.miniapp.mystoryapplication.presentation.ui.storiesmap

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.miniapp.mystoryapplication.presentation.responseui.UserLocationData

class StoriesMapFragment : SupportMapFragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private val boundsBuilder = LatLngBounds.builder()
    private val navArg: StoriesMapFragmentArgs by navArgs()
    private val userLocationData by lazy { navArg.userLocationData }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        addMapMarkers(userLocationData)
        addMapBoundView()
    }

    private fun addMapMarkers(userLocationData: UserLocationData) {
        userLocationData.userLatLng.forEachIndexed { i, latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(userLocationData.userInfo[i])
            )?.showInfoWindow()
            boundsBuilder.include(latLng)
        }
    }

    private fun addMapBoundView() {
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                boundsBuilder.build(),
                resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                300
            )
        )
    }
}