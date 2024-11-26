package com.bytephant.senior_care.domain.status

import android.location.Location
import com.bytephant.senior_care.domain.data.GeoLocation
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.domain.repository.LocationRepository
import com.bytephant.senior_care.service.location.LocationClient

class StatusCollectorImpl(
    val homeRadiusMeter: Double,
    val locationClient: LocationClient,
    val locationRepository: LocationRepository
): StatusCollector {
    
    override suspend fun updateHomeLocation() {
        locationClient.getCurrentLocation().collect { location ->
            locationRepository.updateHomeLocation(
                GeoLocation(location.latitude, location.longitude)
            )
        }
    }

    override suspend fun getCurrentLocationStatus(currentGeoLocation: GeoLocation): UserLocationStatus {
        val homeGeoLocation = locationRepository.getHomeLocation()
            ?: throw IllegalStateException("아직 집 위치가 초기화 되지 않았습니다.")

        // Location 객체 생성
        val homeLocation = Location("home").apply {
            latitude = homeGeoLocation.latitude
            longitude = homeGeoLocation.longitude
        }

        val currentLocation = Location("current").apply {
            latitude = currentGeoLocation.latitude
            longitude = currentGeoLocation.longitude
        }

        // 거리 계산
        val distance = homeLocation.distanceTo(currentLocation) // 거리 (미터)
        return if (distance < homeRadiusMeter) {
            UserLocationStatus.HOME
        } else {
            UserLocationStatus.OUT
        }
    }
}