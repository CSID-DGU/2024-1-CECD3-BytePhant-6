package com.bytephant.senior_care.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bytephant.senior_care.service.database.dao.CoefficientDao
import com.bytephant.senior_care.service.database.dao.LocationDao
import com.bytephant.senior_care.service.database.entity.UserLocation

@Database(entities = [UserLocation::class], version = 1)
@TypeConverters(Converters::class)
abstract class SeniorCareDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
    abstract val coefficientDao : CoefficientDao
}