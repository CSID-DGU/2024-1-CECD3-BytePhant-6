package com.bytephant.senior_care.service.recognizer.android

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer

class CustomRecognitionListener(
    private val onResult: (String) -> Unit
) : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}
    override fun onError(error: Int) {}
    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val finalResult = matches?.firstOrNull() ?: ""
        onResult(finalResult)
    }
    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
}
