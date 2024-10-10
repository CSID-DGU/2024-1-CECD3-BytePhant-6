package com.bytephant.senior_care.service.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bytephant.senior_care.service.database.entity.Coefficient

@Dao
interface CoefficientDao {
    @Upsert
    suspend fun insertCoefficient(coefficient: Coefficient)

    @Query("SELECT * FROM Coefficient WHERE section = :section and version = :version")
    suspend fun getCoefficient(section : Int, version: Int) : List<Coefficient>
}