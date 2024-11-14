package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.Topic
import com.bytephant.senior_care.domain.replier.dto.InitReplyDTO
import com.bytephant.senior_care.domain.replier.dto.ReplyDTO

interface Replier {
    suspend fun initDialogue() : InitReplyDTO
    suspend fun reply(message: String) : ReplyDTO
    suspend fun confirmReply(topic: Topic)
}