package com.bytephant.senior_care.service.recognizer

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import kotlinx.coroutines.flow.Flow

interface VoiceRecognizer {
    fun getRecognizedDialogue() : Flow<String>
    class VoiceRecognizeException(message: String) : Exception(message) {}
    companion object {
        fun checkPermissions(context: Context) : Boolean {
            return (
                ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED
            )
        }
    }
}