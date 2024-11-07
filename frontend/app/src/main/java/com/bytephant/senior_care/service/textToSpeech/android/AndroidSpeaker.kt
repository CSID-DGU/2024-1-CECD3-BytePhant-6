package com.bytephant.senior_care.service.textToSpeech.android

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import com.bytephant.senior_care.service.textToSpeech.Speaker
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Locale
import java.util.UUID
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AndroidSpeaker(
    private val context: Context
) : Speaker, TextToSpeech.OnInitListener{
    private var textToSpeech: TextToSpeech = TextToSpeech(context, this)
    private var isInitialized = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "한국어 미지원")
            }
            isInitialized = true
        } else {
            Log.e("TTS", "TTS 초기화 실패");
        }
    }

    override suspend fun speak(message: String) {
        return suspendCancellableCoroutine { continuation ->
            textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {}
                override fun onDone(utteranceId: String?) {
                    continuation.resume(Unit)
                }
                override fun onError(utteranceId: String?) {
                    continuation.resumeWithException(Exception("TTS Error"))
                }
            })
            if (isInitialized) {
                textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
            } else {
                Log.e("TTS","TextToSpeech is not initialized yet.")
            }
        }
    }

    fun shutdown() {
        textToSpeech.shutdown()
    }

    companion object {
        @Volatile
        private var instance: AndroidSpeaker? = null

        fun getInstance(context: Context): AndroidSpeaker {
            return instance ?: synchronized(this) {
                instance ?: AndroidSpeaker(context).also { instance = it }
            }
        }
    }
}