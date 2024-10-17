package context

import java.util.*

class MemoryContextRepository : ContextRepository {
    private val SECTION_SIZE = 5
    private val SLOT_NUM = 12
    private val LATEST_THRESHOLD = 30

    private val store : Array<Array<Deque<ContextData>>> = Array(SECTION_SIZE) {
        Array (SLOT_NUM) {
            ArrayDeque()
        }
    }

    override fun appendRecord(sectionIndex: Int, armIndex: Int, contextData: ContextData) {
        val queue = store[sectionIndex][armIndex]
        if (queue.size > LATEST_THRESHOLD) {
            queue.removeFirst()
        }
        queue.addLast(contextData)
    }


    override fun getLatestRecord(sectionIndex : Int): Array<Array<ContextData>> {
        return Array(SLOT_NUM) { index ->
            store[sectionIndex][index].toTypedArray()
        }
    }
}