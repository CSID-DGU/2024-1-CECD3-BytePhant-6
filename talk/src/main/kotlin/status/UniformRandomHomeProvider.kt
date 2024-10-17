package status

import kotlin.random.Random

class UniformRandomHomeProvider(
    private val samplingUnitMinute: Int
) : HomeProvider{
    private val intervalUnit = samplingUnitMinute / 15;
    private var result : Boolean = false
    /*
    * 집 집 집 집 집 밖 밖 밖 밖
    * 밖 밖 밖 밖 밖 밖 밖 밖 밖
    * */
    override fun provide(section: Int, slot: Int): Boolean {
        if (slot % intervalUnit == 0) {
            result = Random.nextBoolean()
        }
        return result
    }
}
