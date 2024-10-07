package com.bytephant.senior_care.application

import android.Manifest
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Configuration

class SeniorCareApplication : Application(),  Configuration.Provider {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = Container(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE) // 로그 레벨 설정
            .build()
}