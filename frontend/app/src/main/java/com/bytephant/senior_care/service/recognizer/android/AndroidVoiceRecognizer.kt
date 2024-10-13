package com.bytephant.senior_care.domain.listener.android

import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import com.bytephant.senior_care.service.recognizer.VoiceRecognizer
import com.bytephant.senior_care.service.recognizer.android.CustomRecognitionListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class AndroidVoiceRecognizer(
    private val context : Context,
    private val speechRecognizer: SpeechRecognizer
) : VoiceRecognizer {

    private val recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
    }

    override fun getRecognizedDialogue(): Flow<String> {
        return callbackFlow {
            if (!VoiceRecognizer.checkPermissions(context)) {
                throw VoiceRecognizer.VoiceRecognizeException("voice record not allowed");
            }
            speechRecognizer.setRecognitionListener(
                CustomRecognitionListener { result ->
                    launch { send(result) }
                }
            )
            speechRecognizer.startListening(recognizerIntent)
            awaitClose {
                speechRecognizer.stopListening()
            }
        }
    }
}