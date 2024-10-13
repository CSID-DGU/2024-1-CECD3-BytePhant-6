package com.bytephant.senior_care.service.textToSpeech.android

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.bytephant.senior_care.service.textToSpeech.Speaker
import java.util.Locale

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
        } else {
            Log.e("TTS", "TTS 초기화 실패");
        }
    }

    override fun speak(message: String) {
        if (isInitialized) {
            textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            Log.e("TTS","TextToSpeech is not initialized yet.")
        }
    }

    fun shutdown() {
        textToSpeech.shutdown()
    }

    companion object {
        @Volatile
        private lateinit var instance: AndroidSpeaker

        fun getInstance(context: Context): AndroidSpeaker {
            return instance ?: synchronized(this) {
                instance ?: AndroidSpeaker(context).also { instance = it }
            }
        }
    }
}