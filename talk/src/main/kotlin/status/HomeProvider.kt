package status

interface HomeProvider {
    fun provide(section : Int, slot: Int) : Boolean
}