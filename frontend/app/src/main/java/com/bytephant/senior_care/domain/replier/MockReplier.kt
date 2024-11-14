package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.Topic
import com.bytephant.senior_care.domain.data.TopicSource
import com.bytephant.senior_care.domain.replier.dto.InitReplyDTO
import com.bytephant.senior_care.domain.replier.dto.ReplyDTO

class MockReplier : Replier {
    override suspend fun initDialogue(): InitReplyDTO {
        return InitReplyDTO("시작", Topic(TopicSource.INTEREST, 1))
    }

    override suspend fun reply(message: String): ReplyDTO {
        return ReplyDTO("대답\n대답\n대답\n", 5)
    }

    override suspend fun confirmReply(topic: Topic) {
    }
}