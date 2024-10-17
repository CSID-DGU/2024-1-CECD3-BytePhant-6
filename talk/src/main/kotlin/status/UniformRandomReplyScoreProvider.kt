package status

import kotlin.random.Random

class UniformRandomReplyScoreProvider : ReplyScoreProvider {
    override fun provide(section: Int, slot: Int): Double {
        return Random.nextDouble(0.0,1.0)
    }
}