package com.bytephant.senior_care.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bytephant.senior_care.application.SeniorCareApplication
import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.replier.Replier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatViewModel(
    private val replier : Replier
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState = _uiState.asStateFlow()

    fun initMessage() {
        _uiState.update { current ->
            current.copy(
                isSending = true
            )
        }
        viewModelScope.launch {
            val reply = replier.initDialogue()
            _uiState.update { currentState->
                currentState.copy(
                    messages = currentState.messages + reply,
                    isSending = false,
                )
            }
        }
    }

    fun sendQuestion(sentence: String) {
        val userMessage = BaseMessage(sentence)

        _uiState.update { currentState ->

            currentState.copy(
                messages = currentState.messages + userMessage,
                inputText = "",
                isSending = true
            )
        }
        viewModelScope.launch {
            val replyMessage : BaseMessage = replier.reply(sentence)
            withContext(Dispatchers.Main) {
                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + replyMessage,
                        isSending = false
                    )
                }
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
                val application = (this[APPLICATION_KEY] as SeniorCareApplication)
                val replier = application.container.replier
                ChatViewModel(
                    replier = replier
                )
            }
        }
    }
}