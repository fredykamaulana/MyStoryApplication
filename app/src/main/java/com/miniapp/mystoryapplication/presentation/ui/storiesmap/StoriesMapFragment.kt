package com.miniapp.mystoryapplication.presentation.ui.storiesmap

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.miniapp.mystoryapplication.R
import com.miniapp.mystoryapplication.data.utils.ResultState
import com.miniapp.mystoryapplication.domain.responsedomain.StoriesResponseDomainModel
import com.miniapp.mystoryapplication.presentation.responseui.UserLocationData
import com.miniapp.mystoryapplication.presentation.ui.storieslist.StoriesListViewModel
import com.miniapp.mystoryapplication.presentation.utils.showLoadStatusSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class StoriesMapFragment : SupportMapFragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var userLocationData: UserLocationData

    private val boundsBuilder = LatLngBounds.builder()
    private val vm: StoriesListViewModel by viewModel()

    private var isMapReady = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        getMapAsync(this)
    }

    override fun onResume() {
        super.onResume()
        vm.getStoriesList()
    }

    private fun setupObservers() {
        vm.getStoriesList.observe(viewLifecycleOwner) { data ->
            when (data) {
                is ResultState.Loading -> {
                    showLoadStatusSnackBar(
                        getString(R.string.stories_map_loading_fetch_data), true
                    )
                }
                is ResultState.Success -> {
                    showLoadStatusSnackBar(
                        getString(R.string.stories_map_success_fetch_data), false
                    )
                    userLocationDataMapping(data.data?.listStory)
                }
                is ResultState.Error -> {
                    showLoadStatusSnackBar(data.message.orEmpty(), false) {
                        vm.getStoriesList()
                    }
                }
            }
        }
    }

    private fun userLocationDataMapping(storiesList: List<StoriesResponseDomainModel.ListStoryItem?>?) {
        val userInfo = mutableListOf<String>()
        val userLatLng = mutableListOf<LatLng>()

        storiesList?.forEach {
            userInfo.add("${it?.name}: ${it?.description}")
            userLatLng.add(
                LatLng(
                    it?.lat?.toDouble() ?: 0.0, it?.lon?.toDouble() ?: 0.0
                )
            )
        }

        userLocationData = UserLocationData(
            userInfo = userInfo, userLatLng = userLatLng
        )

        if (isMapReady) addMapMarkers(userLocationData)
    }

    override fun onMapReady(map: GoogleMap) {
        mMap = map
        isMapReady = true
        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
        }
    }

    private fun addMapMarkers(userLocationData: UserLocationData) {
        userLocationData.userLatLng.forEachIndexed { i, latLng ->
            mMap.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(userLocationData.userInfo[i])
            )?.showInfoWindow()

        }
        //just add indonesia country, from sabang to merauke lat long bound
        //because some time there is any story that use other continent coordinates so the maps show weired area bound
        boundsBuilder.include(LatLng(5.88969,95.31644))
        boundsBuilder.include(LatLng(-8.4666648,140.333332))
        addMapBoundView()
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