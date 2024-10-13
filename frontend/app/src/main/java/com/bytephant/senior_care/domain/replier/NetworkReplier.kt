package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.service.network.api.InitMessageReq
import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.service.network.api.ReplyReq
import java.time.LocalDateTime

class NetworkReplier(
    private val messageAPI : MessageAPI
) : Replier {
    override suspend fun initDialogue(): BaseMessage {
        val response = messageAPI.getInitMessage(
            InitMessageReq("abcdef")
        )
        return BaseMessage(response.message, false)
    }

    override suspend fun reply(message: String): BaseMessage {
        val response = messageAPI.getReply(ReplyReq("abcdef", message))
        return BaseMessage(response.message, false)
    }
}
