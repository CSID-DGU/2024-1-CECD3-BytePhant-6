package context

interface ContextProvider {
    fun provide(sectionIndex : Int) : Array<DoubleArray>
}