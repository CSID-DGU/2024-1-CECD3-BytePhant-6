package com.bytephant.senior_care.service.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Coefficient(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val section : Int,
    val arm: Int,
    val version: Int,

    val A: Array<Array<Double>>,
    val b: Array<Double>
)
