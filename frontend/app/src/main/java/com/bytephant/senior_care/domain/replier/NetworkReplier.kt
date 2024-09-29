package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.service.network.api.ReplyReq
import java.time.LocalDateTime

class NetworkReplier(
    private val messageAPI : MessageAPI
) : Replier {
    override suspend fun initDialogue(): BaseMessage {
        val response = messageAPI.getInitMessage()
        return BaseMessage(response.message, false)
    }

    override suspend fun reply(message: String): BaseMessage {
        val response = messageAPI.getReply(ReplyReq(message, LocalDateTime.now()))
        return BaseMessage(response.message)
    }
}