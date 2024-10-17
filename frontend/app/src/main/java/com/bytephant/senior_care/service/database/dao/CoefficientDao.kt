package com.bytephant.senior_care.service.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bytephant.senior_care.service.database.entity.Coefficient
import com.bytephant.senior_care.service.database.entity.CoefficientA
import com.bytephant.senior_care.service.database.entity.CoefficientB
import com.bytephant.senior_care.service.database.entity.CoefficientBase
import java.util.Optional

@Dao
interface CoefficientDao {
    @Transaction
    @Query("""
        SELECT * FROM coefficient WHERE coefficient.section = :section AND coefficient.version = :version
    """)
    suspend fun getCoefficient(section : Int, version: Int) : Coefficient?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoefficientBase(coefficientBase: CoefficientBase): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoefficientA(coefficientA: CoefficientA)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCoefficientB(coefficientB: CoefficientB)

    @Transaction
    suspend fun upsertCoefficient(coefficient: Coefficient) {
        val id = insertCoefficientBase(coefficientBase = coefficient.base)

        coefficient.A.forEach { it ->
            insertCoefficientA(it.copy(coefficientId = id))
        }
        coefficient.b.forEach { it ->
            insertCoefficientB(it.copy(coefficientId = id))
        }
    }
}