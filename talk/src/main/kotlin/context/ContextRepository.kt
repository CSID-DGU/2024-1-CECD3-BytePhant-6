package context

interface ContextRepository {
    fun appendRecord(sectionIndex : Int, armIndex: Int, contextData: ContextData)
    fun getLatestRecord(sectionIndex : Int): Array<Array<ContextData>>
}