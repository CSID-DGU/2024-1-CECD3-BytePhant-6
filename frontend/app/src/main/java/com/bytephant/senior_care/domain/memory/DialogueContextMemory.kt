package com.bytephant.senior_care.domain.memory

interface DialogueContextMemory {
    fun saveTopicId(topicId : Int)
    fun getTopicId() : Int?
}