package com.bytephant.senior_care.domain.receiver

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.service.recognizer.RecognizeStatus
import com.bytephant.senior_care.service.recognizer.VoiceRecognizer
import com.bytephant.senior_care.service.textToSpeech.Speaker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first

class MessageReceiverImpl(
    private val voiceRecognizer: VoiceRecognizer,
    private val speaker: Speaker
) : MessageReceiver {
    override suspend fun listen(): Flow<BaseMessage> {
        return callbackFlow {
            val firstTry = voiceRecognizer.getRecognizedDialogue().first()
            when (firstTry.status) {
                RecognizeStatus.SUCCESS -> send((BaseMessage(firstTry.answer!!)))
                RecognizeStatus.FAILURE -> speaker.speak("주위에 안 계시나요? 한번만 더 들어볼게요")
            }
            val secondTry = voiceRecognizer.getRecognizedDialogue().first()
            if (secondTry.status == RecognizeStatus.SUCCESS) {
                send((BaseMessage(secondTry.answer!!)))
            }
            awaitClose()
        }
    }

    override fun read(message: String): BaseMessage {
        return BaseMessage(message)
    }
}