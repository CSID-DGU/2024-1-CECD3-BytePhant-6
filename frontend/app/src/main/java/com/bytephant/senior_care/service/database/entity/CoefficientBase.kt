package com.bytephant.senior_care.service.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coefficient")
data class CoefficientBase(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val section : Int,
    val arm: Int,
    val version: Int,
)
