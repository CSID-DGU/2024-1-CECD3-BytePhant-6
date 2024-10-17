package com.bytephant.senior_care.domain.repository

import android.location.Location
import com.bytephant.senior_care.domain.data.GeoLocation
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.service.database.entity.HomeLocation

interface LocationRepository {
    suspend fun saveLocation(
        latitude: Double,
        longitude: Double,
        userLocationStatus: UserLocationStatus
    )

    suspend fun getHomeLocation() : GeoLocation?
    suspend fun updateHomeLocation(geoLocation: GeoLocation)
}