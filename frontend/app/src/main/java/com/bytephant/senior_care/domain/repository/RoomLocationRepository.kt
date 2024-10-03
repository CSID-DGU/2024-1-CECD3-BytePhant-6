package com.bytephant.senior_care.domain.repository

import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.service.database.dao.LocationDao
import com.bytephant.senior_care.service.database.entity.UserLocation
import java.time.LocalDateTime

class RoomLocationRepository(
    val locationDao: LocationDao
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
}