package com.bytephant.senior_care.ui.screen.chat

import com.bytephant.senior_care.domain.data.BaseMessage

data class ChatUiState(
    val messages: List<BaseMessage> = listOf(),
    var inputText : String = "",
    val isSending : Boolean = false
)
