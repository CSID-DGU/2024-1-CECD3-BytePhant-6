package com.bytephant.senior_care.domain.repository

import android.location.Location
import com.bytephant.senior_care.domain.data.UserLocationStatus

interface LocationRepository {
    suspend fun saveLocation(
        latitude: Double,
        longitude: Double,
        userLocationStatus: UserLocationStatus
    )
}