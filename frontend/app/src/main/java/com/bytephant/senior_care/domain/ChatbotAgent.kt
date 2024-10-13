package com.bytephant.senior_care.domain

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.data.DialogueHolder
import com.bytephant.senior_care.domain.replier.Replier
import com.bytephant.senior_care.service.textToSpeech.Speaker

class ChatbotAgent(
    private val dialogueHolder: DialogueHolder,
    private val replier: Replier,
    private val speaker: Speaker,
) {
    suspend fun initTalking() : BaseMessage{
        val message = replier.initDialogue()
        dialogueHolder.appendMessage(message)
        speaker.speak(message.message)
        return message
    }

    suspend fun reply(userMessage: BaseMessage) : BaseMessage {
        val message = replier.reply(userMessage.message)
        speaker.speak(message.message)
        dialogueHolder.appendMessage(message)
        return message
    }
}