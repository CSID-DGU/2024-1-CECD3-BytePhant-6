package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.Topic
import com.bytephant.senior_care.domain.data.TopicSource
import com.bytephant.senior_care.domain.replier.dto.InitReplyDTO
import com.bytephant.senior_care.domain.replier.dto.ReplyDTO
import com.bytephant.senior_care.service.network.api.InitMessageReq
import com.bytephant.senior_care.service.network.api.InterestConfirmReq
import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.service.network.api.QuestionConfirmReq
import com.bytephant.senior_care.service.network.api.ReplyReq

class NetworkReplier(
    private val messageAPI : MessageAPI
) : Replier {
    override suspend fun initDialogue(): InitReplyDTO {
        val response = messageAPI.getInitMessage(
            InitMessageReq("abcdef")
        )
        val topic = if (response.interest_id != null) {
            Topic(TopicSource.INTEREST, response.interest_id)
        } else if (response.question_id != null){
            Topic(TopicSource.QUESTION, response.question_id)
        } else null
        return InitReplyDTO(response.message, topic)
    }

    override suspend fun reply(message: String): ReplyDTO {
        val response = messageAPI.getReply(ReplyReq("abcdef", message))
        return ReplyDTO(response.message, response.score)
    }

    override suspend fun confirmReply(topic: Topic) {
        when (topic.source) {
            TopicSource.INTEREST -> {
                messageAPI.confirmInterest(InterestConfirmReq(topic.sourceId.toString()))
            }
            TopicSource.QUESTION -> {
                messageAPI.confirmQuestion(QuestionConfirmReq(topic.sourceId.toString(), "abcdef"))
            }
        }
    }
}
