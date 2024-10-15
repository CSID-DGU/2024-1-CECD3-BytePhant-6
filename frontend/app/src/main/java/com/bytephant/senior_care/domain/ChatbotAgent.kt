package com.bytephant.senior_care.domain

import androidx.compose.runtime.collectAsState
import com.bytephant.senior_care.domain.data.AgentStatus
import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.replier.Replier
import com.bytephant.senior_care.service.textToSpeech.Speaker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.internal.wait

class ChatbotAgent(
    private val dialogueHolder: DialogueHolder,
    private val replier: Replier,
    private val speaker: Speaker,
) {
    private val _agentStatus = MutableStateFlow(AgentStatus.WAITING)
    val agentStatus = _agentStatus.asStateFlow()

    suspend fun initTalking() : BaseMessage{
        _agentStatus.update { AgentStatus.THINKING }
        val message = replier.initDialogue()
        _agentStatus.update { AgentStatus.TALKING }
        dialogueHolder.appendMessage(message)
        speaker.speak(message.message)
        _agentStatus.update { AgentStatus.WAITING }
        return message
    }

    suspend fun reply(userMessage: BaseMessage) : BaseMessage {
        _agentStatus.update { AgentStatus.THINKING }
        val reply = replier.reply(userMessage.message)
        speaker.speak(reply.message)
        _agentStatus.update { AgentStatus.TALKING }
        dialogueHolder.appendMessage(reply)
        _agentStatus.update { AgentStatus.WAITING }
        return reply
    }

    fun listenStart() {
        _agentStatus.update { AgentStatus.LISTENING }
    }
}