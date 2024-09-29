package com.bytephant.senior_care.service.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bytephant.senior_care.domain.data.UserLocationStatus
import java.time.LocalDateTime

@Entity
data class UserLocation(
    val longitude: Double,
    val latitude: Double,
    val dateTime: LocalDateTime,
    val status: UserLocationStatus,

    @PrimaryKey(autoGenerate = true)
    val id : Long?
)
