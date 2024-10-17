package com.bytephant.senior_care.application

import android.content.Context
import android.speech.SpeechRecognizer
import androidx.room.Room
import com.bytephant.senior_care.domain.ChatbotAgent
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.listener.android.AndroidVoiceRecognizer
import com.bytephant.senior_care.domain.receiver.MessageReceiver
import com.bytephant.senior_care.domain.receiver.MessageReceiverImpl
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
import com.bytephant.senior_care.service.textToSpeech.Speaker
import com.bytephant.senior_care.service.textToSpeech.android.AndroidSpeaker
import com.google.android.gms.location.LocationServices

class Container (
    private val context : Context
) {
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
        RoomLocationRepository(database.locationDao, database.homeLocationDao)
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
    private val speaker : Speaker by lazy { AndroidSpeaker.getInstance(context) };

    val messageReceiver : MessageReceiver by lazy {
        MessageReceiverImpl(voiceRecognizer)
    }

    val dialogueHolder : DialogueHolder by lazy {
        DialogueHolder()
    }

    val chatBotAgent : ChatbotAgent by lazy {
        ChatbotAgent(dialogueHolder, replier, speaker)
    }
}