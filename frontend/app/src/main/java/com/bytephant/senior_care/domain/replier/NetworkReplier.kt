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
            InitMessageReq("날씨가 참 좋네요. 오늘은 외출 하실 계획이 있나요?")
        )
        return BaseMessage(response.message, false)
    }

    override suspend fun reply(message: String): BaseMessage {
        val response = messageAPI.getReply(ReplyReq(message, LocalDateTime.now()))
        return BaseMessage(response.message, false)
    }
}
