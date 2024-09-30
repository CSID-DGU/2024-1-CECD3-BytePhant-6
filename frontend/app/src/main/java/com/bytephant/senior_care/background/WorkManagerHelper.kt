package com.bytephant.senior_care.background

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.bytephant.senior_care.background.worker.LocationSaver
import java.util.concurrent.TimeUnit

fun enrollLocationSaver(context: Context) {
    val workRequest = PeriodicWorkRequest
        .Builder(LocationSaver::class.java, 15, TimeUnit.MINUTES)
        .build()
    WorkManager
        .getInstance(context)
        .enqueueUniquePeriodicWork(
            "location_saver",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
}

fun enrollWorks(context: Context) {
    enrollLocationSaver(context)
}