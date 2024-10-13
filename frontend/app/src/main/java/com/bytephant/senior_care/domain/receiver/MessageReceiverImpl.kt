package com.bytephant.senior_care.domain.receiver

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.service.recognizer.VoiceRecognizer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MessageReceiverImpl(
    private val voiceRecognizer: VoiceRecognizer
) : MessageReceiver {
    override suspend fun listen(): Flow<BaseMessage> {
        return voiceRecognizer.getRecognizedDialogue().map { message ->
            BaseMessage(message);
        }
    }

    override fun read(message: String): BaseMessage {
        return BaseMessage(message)
    }
}