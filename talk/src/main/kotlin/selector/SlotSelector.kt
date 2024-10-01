package selector

interface SlotSelector {
    fun select(context:Array<DoubleArray>, sectionIndex:Int): Int
    fun update(reward:Double, arm: Int, context:Array<DoubleArray>, sectionIndex:Int)
}