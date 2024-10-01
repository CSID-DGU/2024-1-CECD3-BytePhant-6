package status

interface ReplyScoreProvider {
    fun provide(section : Int, slot : Int) : Double
}