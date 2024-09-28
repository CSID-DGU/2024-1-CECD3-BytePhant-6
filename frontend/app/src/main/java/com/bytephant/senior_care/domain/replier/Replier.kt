package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.data.BaseMessage

interface Replier {
    suspend fun initDialogue() : BaseMessage
    suspend fun reply(message: String) : BaseMessage
}