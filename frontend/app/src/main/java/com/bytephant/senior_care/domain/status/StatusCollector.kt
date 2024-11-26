package com.bytephant.senior_care.domain.status

import com.bytephant.senior_care.domain.data.GeoLocation
import com.bytephant.senior_care.domain.data.UserLocationStatus

interface StatusCollector {
    suspend fun updateHomeLocation()
    suspend fun getCurrentLocationStatus(currentGeoLocation: GeoLocation):UserLocationStatus
}