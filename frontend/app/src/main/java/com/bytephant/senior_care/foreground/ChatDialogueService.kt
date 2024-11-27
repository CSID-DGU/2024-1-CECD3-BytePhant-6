package com.bytephant.senior_care.foreground

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.bytephant.senior_care.R
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.ChatbotAgent
import com.bytephant.senior_care.domain.receiver.MessageReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


class ChatDialogueService: Service() {
    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var chatbotAgent: ChatbotAgent
    private lateinit var messageReceiver: MessageReceiver

    override fun onCreate() {
        super.onCreate()
        val app = application as SeniorCareApplication
        chatbotAgent = app.container.chatBotAgent
        messageReceiver = app.container.messageReceiver
        startForeground(NOTIFICATION_ID, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "dami-dialogue",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }
    private fun createNotification() : Notification {
        return Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("다미")
            .setContentText("다미와 대화 중입니다.")
            .setSmallIcon(R.drawable.ai_icon)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, createNotification())

        serviceScope.launch {
            try {
                chatbotAgent.initTalking()
                var isFinish = false

                messageReceiver.listen().catch { e ->
                    Log.e("ChatDialogueService", "Listen failed: ${e.message}")
                    isFinish = true
                }.collect { userMessage ->
                    val botMessage = chatbotAgent.reply(userMessage)
                    isFinish = botMessage.isFinish
                }

                while (!isFinish) {
                    messageReceiver.listen().catch { e->
                        Log.e("ChatDialogueService", "Listen failed: ${e.message}")
                        isFinish = true
                    }.collect { user ->
                        isFinish = chatbotAgent.reply(user).isFinish
                    }
                }

                stopSelf()
            } catch (e: Exception) {
                Log.e("ChatDialogueService", e.message?:"unknown error")
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel() // 코루틴 스코프 취소
    }
    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "chatbot_channel"
    }
}