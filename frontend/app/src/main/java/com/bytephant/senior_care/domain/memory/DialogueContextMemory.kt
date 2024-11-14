package com.bytephant.senior_care.domain.memory

import com.bytephant.senior_care.domain.data.Topic

interface DialogueContextMemory {
    fun saveTopic(topic: Topic)
    fun getTopic() : Topic?
    fun reset()
}