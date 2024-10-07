package com.bytephant.senior_care.background.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.service.database.entity.UserLocation
import com.bytephant.senior_care.service.location.LocationClient
import java.time.LocalDateTime

class LocationSaver(
    val context : Context,
    val workerParams : WorkerParameters
) : CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        val container = (context.applicationContext as SeniorCareApplication).container
        val locationClient = container.locationClient
        val locationRepository = container.locationRepository
        val dataBuilder = Data.Builder()
            .putString("init", "init")
        try {
            if (!LocationClient.checkPermission(context)) {
                throw LocationClient.LocationException("no permissions")
            }
            dataBuilder.putString("permissions", "permissions: pass")
            locationClient.getCurrentLocation().collect { location ->
                locationRepository.saveLocation(
                    location.longitude,
                    location.latitude,
                    UserLocationStatus.HOME
                )
            }
            dataBuilder.putString("location", "location: pass")
        } catch (e: Exception) {
            return Result.failure(
                dataBuilder
                    .putString("ERROR_MESSAGE", e.message)
                    .build()
            )
        }
        return Result.success(dataBuilder.build())
    }
}