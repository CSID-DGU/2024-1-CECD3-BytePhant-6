package com.bytephant.senior_care.domain.data

data class Dialogue (
    val messageList : List<BaseMessage> = listOf(),
    val isStarted: Boolean = false
)