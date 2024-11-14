package com.bytephant.senior_care.domain.memory

import com.bytephant.senior_care.domain.data.Topic

class TempDialogueContextMemory : DialogueContextMemory{
    private var topic : Topic? = null

    override fun saveTopic(topic: Topic) {
        this.topic = topic;
    }

    override fun getTopic(): Topic? {
        return topic
    }

    override fun reset() {
        this.topic = null
    }
}