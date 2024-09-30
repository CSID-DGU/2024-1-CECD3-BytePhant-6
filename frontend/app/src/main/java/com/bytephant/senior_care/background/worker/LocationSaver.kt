package com.bytephant.senior_care.background.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.bytephant.senior_care.service.database.entity.UserLocation
import java.time.LocalDateTime

class LocationSaver(
    val context : Context,
    val workerParams : WorkerParameters
) : CoroutineWorker(context, workerParams){

    override suspend fun doWork(): Result {
        val container = (context.applicationContext as SeniorCareApplication).container
        val locationClient = container.locationClient
        val database = container.database
        try {
            val location = locationClient.getCurrentLocation().collect { location ->
                database.locationDao.insertLocation(
                    UserLocation(
                        location.longitude,
                        location.latitude,
                        LocalDateTime.now(),
                        UserLocationStatus.HOME,
                    )
                )
            }
        } catch (e: Exception) {
            return Result.failure()
        }
        return Result.success()
    }
}