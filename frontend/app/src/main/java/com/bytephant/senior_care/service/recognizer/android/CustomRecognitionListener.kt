package com.bytephant.senior_care.service.recognizer.android

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer
import android.util.Log
import com.bytephant.senior_care.service.recognizer.RecognizeResult
import com.bytephant.senior_care.service.recognizer.RecognizeStatus

class CustomRecognitionListener(
    private val onResult: (RecognizeResult) -> Unit,
) : RecognitionListener {
    override fun onReadyForSpeech(params: Bundle?) {}
    override fun onBeginningOfSpeech() {}
    override fun onRmsChanged(rmsdB: Float) {}
    override fun onBufferReceived(buffer: ByteArray?) {}
    override fun onEndOfSpeech() {}
    override fun onError(error: Int) {
        if ((error == SpeechRecognizer.ERROR_NO_MATCH || error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT)) {
            onResult(RecognizeResult(RecognizeStatus.FAILURE))
        } else {
            Log.e("CustomRecognitionListener", "Error: $error")
        }
    }

    override fun onResults(results: Bundle?) {
        val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        val finalResult = matches?.firstOrNull() ?: ""

        if (finalResult.isEmpty()) {
            onResult(RecognizeResult(RecognizeStatus.FAILURE))
        } else {
            onResult(RecognizeResult(RecognizeStatus.SUCCESS, finalResult))
        }
    }
    override fun onPartialResults(partialResults: Bundle?) {}
    override fun onEvent(eventType: Int, params: Bundle?) {}
}
