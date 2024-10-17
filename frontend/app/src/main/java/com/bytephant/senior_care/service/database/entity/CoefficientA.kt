package com.bytephant.senior_care.service.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "coefficient_a",
    foreignKeys = [
        ForeignKey(
            entity = CoefficientBase::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("coefficient_id")
        )
    ]
)
data class CoefficientA (
    val value: Double,
    val row : Int,
    val col : Int,

    @PrimaryKey(autoGenerate = true)
    val aId: Long = 0,
    @ColumnInfo(name = "coefficient_id")
    val coefficientId: Long
)