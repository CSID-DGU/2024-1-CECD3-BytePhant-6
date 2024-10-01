package status

import kotlin.random.Random

class FixedDistributionReplyScoreProvider : ReplyScoreProvider {
    private val distribution = arrayOf(
        arrayOf(3,6,8,6,1,0,1,0,1,1,7,7),
        arrayOf(3,6,8,8,6,3,8,1,0,0,1,1),
        arrayOf(1,1,1,1,1,1,3,6,8,8,6,3),
        arrayOf(8,8,4,1,1,1,1,1,1,1,1,1),
        arrayOf(8,8,1,1,1,0,0,1,1,1,1,1),
    )

    /*      slot : 0 1 2 3 4 5 6 7 8 9 10 11
    *  section 0 : 0 0 0 1 2 3 4 3 2 1 0  0
    *  section 1 : 0 0 1 2 3 4 3 2 1 0 0  0
    *  section 2 : 0 0 1 2 3 4 3 2 1 0 0  0
    *  section 3 : 0 0 1 2 3 4 3 2 1 1 2  3
    *  section 4 : 4 3 2 1 0 0 0 0 0 0 0  0
    * */
    override fun provide(section: Int, slot: Int): Double {
        val slotDistribution = distribution[section][slot];
        val random = Random.nextInt(8);
        return if (random <= slotDistribution) 1.0 else 0.0;
    }

}