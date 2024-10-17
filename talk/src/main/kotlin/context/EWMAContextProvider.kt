package context

import java.util.Arrays

class EWMAContextProvider(
    private val alpha: Double,
    private val contextRepository: ContextRepository
) : ContextProvider{
    private fun toScore(b : Boolean) = if (b) 1.0 else 0.0
    private fun calculate(data: Array<ContextData>) : DoubleArray {
        if (data.isEmpty()) return DoubleArray(2) { 0.0 }
        var homeEWMA : Double = toScore(data[0].home)
        var talkEWMA : Double = data[0].replyScore

        for (index in 1 until data.size) {
            homeEWMA = alpha * toScore(data[index].home) + (1 - alpha) * homeEWMA
            talkEWMA = alpha * data[index].replyScore + (1 - alpha) * talkEWMA
        }
        return DoubleArray(2) {index ->
            when (index) {
                0 -> homeEWMA
                1 -> talkEWMA
                else -> 0.0
            }
        }
    }

    override fun provide(sectionIndex : Int): Array<DoubleArray> {
        val sectionContextData = contextRepository.getLatestRecord(sectionIndex)
        return Array(sectionContextData.size) {index ->
            calculate(sectionContextData[index])
        }
    }
}