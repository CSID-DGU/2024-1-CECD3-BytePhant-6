package com.bytephant.senior_care.service.database.dao

import android.location.Location
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.bytephant.senior_care.service.database.entity.UserLocation
import java.time.LocalDateTime

@Dao
interface LocationDao {
    @Insert
    suspend fun insertLocation(userLocation: UserLocation)

    @Query("SELECT * FROM UserLocation WHERE dateTime >= :since")
    suspend fun getUserLocation(since: LocalDateTime): List<UserLocation>
}