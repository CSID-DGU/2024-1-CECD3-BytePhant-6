package com.bytephant.senior_care.application

import android.app.Application

class SeniorCareApplication : Application() {
    lateinit var container: Container
    override fun onCreate() {
        super.onCreate()
        container = Container(this)
    }
}