package com.bytephant.senior_care.domain.replier

import com.bytephant.senior_care.domain.data.BaseMessage

class MockReplier : Replier {
    override suspend fun initDialogue(): BaseMessage {
        return BaseMessage("시작", false)
    }

    override suspend fun reply(message: String): BaseMessage {
        return BaseMessage("대답\n대답\n대답\n", false)
    }
}