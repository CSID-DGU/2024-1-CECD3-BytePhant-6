package com.bytephant.senior_care.application

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import com.bytephant.senior_care.MainActivity
import com.bytephant.senior_care.ui.screen.chat.ChatViewModel

class ADBCustomReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        if(intent?.action == "com.example.action.INIT_TALKING") {
            val activity = context as? MainActivity
            activity?.let {
                val chatViewModel: ChatViewModel = ViewModelProvider(it).get(ChatViewModel::class.java)
                chatViewModel.initMessage()
            }
        }
    }
}