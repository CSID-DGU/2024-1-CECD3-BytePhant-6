package com.bytephant.senior_care.domain.data

enum class TopicSource {
    QUESTION, INTEREST
}

data class Topic (
    val source : TopicSource,
    val sourceId : Int
)