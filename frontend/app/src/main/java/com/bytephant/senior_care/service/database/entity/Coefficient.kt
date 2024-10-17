package com.bytephant.senior_care.service.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class Coefficient(
    @Embedded val base: CoefficientBase,
    @Relation (
        parentColumn = "id",
        entityColumn = "aId"
    )
    val A : List<CoefficientA>,
    @Relation (
        parentColumn = "id",
        entityColumn = "bId"
    )
    val b : List<CoefficientB>
)