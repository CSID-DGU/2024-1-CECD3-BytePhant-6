package com.bytephant.senior_care.domain

import com.bytephant.senior_care.domain.data.AgentStatus
import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.memory.DialogueContextMemory
import com.bytephant.senior_care.domain.replier.Replier
import com.bytephant.senior_care.service.textToSpeech.Speaker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatbotAgent(
    private val dialogueHolder: DialogueHolder,
    private val replier: Replier,
    private val speaker: Speaker,
    private val contextMemory: DialogueContextMemory
) {
    private val _agentStatus = MutableStateFlow(AgentStatus.WAITING)
    private val _agentLastSentence = MutableStateFlow("")
    val agentStatus = _agentStatus.asStateFlow()
    val agentLastSentence = _agentLastSentence.asStateFlow()

    suspend fun initTalking() : BaseMessage{
        _agentStatus.update { AgentStatus.THINKING }
        val message = replier.initDialogue()
        val res = BaseMessage(message.message, false)
        if (message.topic != null) contextMemory.saveTopic(message.topic)
        _agentStatus.update { AgentStatus.TALKING }
        dialogueHolder.appendMessage(res)
        _agentLastSentence.update { message.message }
        speaker.speak(message.message)
        _agentStatus.update { AgentStatus.WAITING }
        return res
    }

    suspend fun reply(userMessage: BaseMessage) : BaseMessage {
        _agentStatus.update { AgentStatus.THINKING }
        val reply = replier.reply(userMessage.message)
        val res = BaseMessage(reply.message, false, reply.score <= 0)
        val topic = contextMemory.getTopic()
        if (!res.isFinish && topic != null) {
            replier.confirmReply(topic)
            contextMemory.reset()
        }
        dialogueHolder.appendMessage(res)
        _agentStatus.update { AgentStatus.TALKING }
        _agentLastSentence.update { reply.message }
        speaker.speak(reply.message)
        _agentStatus.update { AgentStatus.WAITING }
        return res
    }

    fun listenStart() {
        _agentStatus.update { AgentStatus.LISTENING }
    }

    fun listenEnd() {
        _agentStatus.update { AgentStatus.WAITING }
    }
}