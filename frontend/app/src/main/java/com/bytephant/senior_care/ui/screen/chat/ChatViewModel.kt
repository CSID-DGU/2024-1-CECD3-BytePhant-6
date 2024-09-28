package com.bytephant.senior_care.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bytephant.senior_care.data.BaseMessage
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

    fun sendQuestion(sentence: String) {
        val userMessage = BaseMessage(sentence)

        _uiState.update { currentState ->

            currentState.copy(
                messages = currentState.messages + userMessage,
                inputText = ""
            )
        }
        viewModelScope.launch {
            val replyMessage : BaseMessage = replier.reply(sentence)
            withContext(Dispatchers.Main) {
                _uiState.update { currentState ->
                    currentState.copy(
                        messages = currentState.messages + replyMessage,
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
}