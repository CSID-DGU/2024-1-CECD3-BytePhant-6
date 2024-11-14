package com.bytephant.senior_care.domain.memory

class TempDialogueContextMemory : DialogueContextMemory{
    private var lastTopicId : Int = -1

    override fun saveTopicId(topicId: Int) {
        this.lastTopicId = topicId
    }

    override fun getTopicId(): Int? {
        return if (this.lastTopicId == -1 ) null else this.lastTopicId
    }
}