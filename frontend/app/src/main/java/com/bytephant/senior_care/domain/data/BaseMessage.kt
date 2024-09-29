package com.bytephant.senior_care.domain.data

data class BaseMessage(
    val message: String,
    val isUser: Boolean = true
)
