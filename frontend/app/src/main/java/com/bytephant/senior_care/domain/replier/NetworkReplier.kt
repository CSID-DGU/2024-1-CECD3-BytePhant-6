package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.BaseMessage
import com.bytephant.senior_care.domain.replier.dto.InitReplyDTO
import com.bytephant.senior_care.domain.replier.dto.ReplyDTO
import com.bytephant.senior_care.service.network.api.InitMessageReq
import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.service.network.api.ReplyReq

class NetworkReplier(
    private val messageAPI : MessageAPI
) : Replier {
    override suspend fun initDialogue(): InitReplyDTO {
        val response = messageAPI.getInitMessage(
            InitMessageReq("abcdef")
        )
        val topicId = response.interest_id ?: response.question_id
        return InitReplyDTO(response.message, topicId)
    }

    override suspend fun reply(message: String): ReplyDTO {
        val response = messageAPI.getReply(ReplyReq("abcdef", message))
        return ReplyDTO(response.message, response.score)
    }
}
