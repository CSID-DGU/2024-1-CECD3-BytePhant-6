package com.bytephant.senior_care.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.ChatbotAgent
import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.receiver.MessageReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val messageReceiver: MessageReceiver,
    private val chatbotAgent: ChatbotAgent,
    private val dialogueHolder: DialogueHolder
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()
    val dialogueState = dialogueHolder.dialogue
    val agentState = chatbotAgent.agentStatus

    fun sendQuestion(sentence: String) {
        if (dialogueState.value.isStarted) {
            val userMessage = messageReceiver.read(sentence);
            dialogueHolder.appendMessage(userMessage)
            _uiState.update { currentState ->
                currentState.copy(
                    inputText = "",
                )
            }
            viewModelScope.launch {
                chatbotAgent.reply(userMessage)
            }
        }
    }

    fun updateInputText(text: String) {
        _uiState.update { current ->
            current.copy(
                inputText = text
            )
        }
    }

    companion object {
        val Factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val container = (this[APPLICATION_KEY] as SeniorCareApplication).container
                val chatBotAgent = container.chatBotAgent
                val messageReceiver = container.messageReceiver
                val holder = container.dialogueHolder
                ChatViewModel(messageReceiver, chatBotAgent, holder)
            }
        }
    }
}