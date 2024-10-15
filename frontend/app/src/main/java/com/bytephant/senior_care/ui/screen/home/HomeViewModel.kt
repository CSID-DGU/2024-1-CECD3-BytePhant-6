package com.bytephant.senior_care.ui.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.ChatbotAgent
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.receiver.MessageReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val chatbotAgent: ChatbotAgent,
    private val messageReceiver: MessageReceiver,
    private val dialogueHolder: DialogueHolder
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState =_uiState.asStateFlow()
    val dialogueStatus = dialogueHolder.dialogue
    val agentState = chatbotAgent.agentStatus

    fun replyWithVoice() {
        chatbotAgent.listenStart()
        if (dialogueHolder.dialogue.value.isStarted) {
            viewModelScope.launch {
                messageReceiver.listen().collect { message ->
                    dialogueHolder.appendMessage(message)
                    chatbotAgent.reply(message)
                }
            }
        }else {
            Log.i("HomeViewModel", "not started")
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as SeniorCareApplication).container
                val chatbotAgent = container.chatBotAgent
                val dialogueHolder = container.dialogueHolder
                val messageReceiver = container.messageReceiver
                HomeViewModel(chatbotAgent, messageReceiver, dialogueHolder)
            }
        }
    }
}