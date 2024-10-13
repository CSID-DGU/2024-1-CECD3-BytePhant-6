package com.bytephant.senior_care.domain.receiver

import com.bytephant.senior_care.domain.data.BaseMessage
import kotlinx.coroutines.flow.Flow

interface MessageReceiver {
    suspend fun listen() : Flow<BaseMessage>
    fun read(message: String) : BaseMessage
}