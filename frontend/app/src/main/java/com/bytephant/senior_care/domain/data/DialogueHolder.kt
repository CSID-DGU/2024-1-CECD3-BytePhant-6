package com.bytephant.senior_care.domain.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DialogueHolder {
    private val _dialogue = MutableStateFlow(Dialogue())
    val dialogue = _dialogue.asStateFlow()

    fun appendMessage(message: BaseMessage) {
        _dialogue.update { current ->
            current.copy(
                messageList = current.messageList + message
            )
        }
    }

    fun getLatestMessage() : BaseMessage {
        return dialogue.value.messageList.last()
    }
}