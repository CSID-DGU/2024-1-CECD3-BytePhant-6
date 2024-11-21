package com.bytephant.senior_care.domain.replier.dto

import com.bytephant.senior_care.domain.data.Topic

data class InitReplyDTO(
    val message: String,
    val topic: Topic?
)
