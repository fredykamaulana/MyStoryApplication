package com.miniapp.mystoryapplication.presentation.responseui

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserLocationData(
    val userInfo: List<String>,
    val userLatLng: List<LatLng>
) : Parcelable
