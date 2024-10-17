package com.bytephant.senior_care.service.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bytephant.senior_care.service.database.entity.HomeLocation

@Dao
interface HomeDao {
    @Upsert
    suspend fun updateHomeLocation(homeLocation: HomeLocation)

    @Query("SELECT * FROM HomeLocation")
    suspend fun getHomeLocation(): HomeLocation?
}