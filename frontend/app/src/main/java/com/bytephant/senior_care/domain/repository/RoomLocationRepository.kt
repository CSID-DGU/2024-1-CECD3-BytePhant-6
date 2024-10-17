package com.bytephant.senior_care.domain.repository

import com.bytephant.senior_care.domain.data.GeoLocation
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.service.database.dao.HomeDao
import com.bytephant.senior_care.service.database.dao.LocationDao
import com.bytephant.senior_care.service.database.entity.HomeLocation
import com.bytephant.senior_care.service.database.entity.UserLocation
import java.time.LocalDateTime

class RoomLocationRepository(
    val locationDao: LocationDao,
    val homeDao: HomeDao
) : LocationRepository {

    override suspend fun saveLocation(
        latitude: Double,
        longitude: Double,
        userLocationStatus: UserLocationStatus
    ) {
        locationDao.insertLocation(
            UserLocation(
                longitude, latitude,
                LocalDateTime.now(),
                userLocationStatus
            ),
        )
    }

    override suspend fun getHomeLocation() : GeoLocation? {
        val homeLocation = homeDao.getHomeLocation()
        return if (homeLocation != null)
            GeoLocation(homeLocation.longitude, homeLocation.latitude)
        else null
    }

    override suspend fun updateHomeLocation(geoLocation: GeoLocation) {
        homeDao.updateHomeLocation(
            HomeLocation(geoLocation.latitude,geoLocation.longitude)
        )
    }
}