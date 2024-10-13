package com.bytephant.senior_care.application

import android.content.Context
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.room.Room
import com.bytephant.senior_care.domain.listener.android.AndroidVoiceRecognizer
import com.bytephant.senior_care.domain.receiver.MessageReceiver
import com.bytephant.senior_care.domain.receiver.MessageReceiverImpl
import com.bytephant.senior_care.domain.replier.MockReplier
import com.bytephant.senior_care.domain.replier.NetworkReplier
import com.bytephant.senior_care.domain.replier.Replier
import com.bytephant.senior_care.domain.repository.LocationRepository
import com.bytephant.senior_care.domain.repository.RoomLocationRepository
import com.bytephant.senior_care.service.database.SeniorCareDatabase
import com.bytephant.senior_care.service.location.GmsLocationClient
import com.bytephant.senior_care.service.location.LocationClient
import com.bytephant.senior_care.service.network.RetrofitConfig
import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.service.recognizer.VoiceRecognizer
import com.google.android.gms.location.LocationServices
import java.util.Locale

class Container (
    private val context : Context
) : TextToSpeech.OnInitListener {
    private val messageAPI : MessageAPI by lazy {
        RetrofitConfig.retrofit.create(MessageAPI::class.java)
    }
    val replier : Replier by lazy {
        NetworkReplier(messageAPI)
//        MockReplier()
    }
    val database = Room.databaseBuilder(
        context = context,
        SeniorCareDatabase::class.java,
        "senior_care.db"
    ).build()

    val locationRepository : LocationRepository by lazy {
        RoomLocationRepository(database.locationDao)
    }

    val locationClient : LocationClient by lazy {
        GmsLocationClient(
            context = context,
            LocationServices.getFusedLocationProviderClient(context)
        )
    }

    private val voiceRecognizer : VoiceRecognizer by lazy {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        AndroidVoiceRecognizer(context, speechRecognizer)
    }

    val messageReceiver : MessageReceiver by lazy {
        MessageReceiverImpl(voiceRecognizer)
    }

    val ttsService : TextToSpeech = TextToSpeech(context, this);

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = ttsService.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "한국어 미지원")
            }
        } else {
            Log.e("TTS", "TTS 초기화 실패");
        }
    }

}