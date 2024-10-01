package status

import kotlin.random.Random

class FixedDistributionHomeProvider : HomeProvider {
    private val distribution = arrayOf(
        arrayOf(8,7,6,5,1,1,0,0,0,1,8,8),
        arrayOf(6,8,8,8,6,1,0,0,0,0,1,1),
        arrayOf(1,1,1,1,1,1,0,6,7,8,7,6),
        arrayOf(8,8,8,8,8,1,0,1,0,1,1,1),
        arrayOf(8,8,8,8,8,1,0,0,1,1,1,1),
    )
    override fun provide(section: Int, slot: Int): Boolean {
        val slotDistribution = distribution[section][slot];
        val random = Random.nextInt(8);
        return random <= slotDistribution;
    }
}