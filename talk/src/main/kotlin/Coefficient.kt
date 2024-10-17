import org.apache.commons.math3.linear.RealMatrix

data class Coefficient(
    val A: Array<RealMatrix>,
    val b: Array<RealMatrix>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Coefficient

        if (!A.contentEquals(other.A)) return false
        if (!b.contentEquals(other.b)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = A.contentHashCode()
        result = 31 * result + b.contentHashCode()
        return result
    }
}
