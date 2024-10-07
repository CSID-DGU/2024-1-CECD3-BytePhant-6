package com.bytephant.senior_care.service.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getCurrentLocation(): Flow<Location>
    fun getLocationUpdates(interval: Long): Flow<Location>
    class LocationException(message: String) : Exception(message)
    companion object {
        fun checkPermission(context: Context) : Boolean {
            return (
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
    }
}