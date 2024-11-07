package com.bytephant.senior_care.service.textToSpeech

interface Speaker {
    suspend fun speak(message: String)
}