package com.bytephant.senior_care.service.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "coefficient_b",
    foreignKeys = [
        ForeignKey(
            entity = CoefficientBase::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("coefficient_id")
        )
    ]
)data class CoefficientB(
    val value: Double,
    val col: Int,

    @PrimaryKey(autoGenerate = true)
    val bId: Long = 0,
    @ColumnInfo(name = "coefficient_id")
    val coefficientId: Long
)
