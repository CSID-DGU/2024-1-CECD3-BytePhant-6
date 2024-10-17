package com.bytephant.senior_care.service.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeLocation(
    val latitude: Double,
    val longitude: Double,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
)
