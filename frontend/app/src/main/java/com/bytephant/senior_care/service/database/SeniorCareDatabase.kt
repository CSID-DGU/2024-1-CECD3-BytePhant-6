package com.bytephant.senior_care.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bytephant.senior_care.service.database.dao.CoefficientDao
import com.bytephant.senior_care.service.database.dao.HomeDao
import com.bytephant.senior_care.service.database.dao.LocationDao
import com.bytephant.senior_care.service.database.entity.Coefficient
import com.bytephant.senior_care.service.database.entity.HomeLocation
import com.bytephant.senior_care.service.database.entity.UserLocation

@Database(entities = [UserLocation::class, Coefficient::class, HomeLocation::class], version = 1)
@TypeConverters(Converters::class)
abstract class SeniorCareDatabase : RoomDatabase() {
    abstract val locationDao: LocationDao
    abstract val coefficientDao : CoefficientDao
    abstract val homeLocationDao : HomeDao
}