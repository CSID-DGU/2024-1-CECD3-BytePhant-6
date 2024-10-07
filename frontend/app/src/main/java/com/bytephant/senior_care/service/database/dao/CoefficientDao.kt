package com.bytephant.senior_care.service.database.dao

import androidx.room.Query
import androidx.room.Upsert
import com.bytephant.senior_care.service.database.entity.Coefficient

interface CoefficientDao {
    @Upsert
    suspend fun insertCoefficient(coefficient: Coefficient)

    @Query("SELECT * FROM Coefficient WHERE section = :section and version = :version")
    suspend fun getCoefficient(section : Int, version: Int)
}