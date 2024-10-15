package com.bytephant.senior_care.application

import android.content.Context
import android.content.Intent
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ADBCustomReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if(intent?.action == "com.example.action.INIT_TALKING") {
            val application = context.applicationContext as? SeniorCareApplication
            MainScope().launch {
                val message = application?.container?.chatBotAgent?.initTalking()
                application?.container?.dialogueHolder?.initDialogue()
            }
        }
    }
}