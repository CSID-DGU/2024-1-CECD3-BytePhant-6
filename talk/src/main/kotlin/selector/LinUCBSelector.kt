package selector

import Coefficient
import coefficient.RegressionCoefficientHolder
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealVector
import kotlin.math.sqrt

class LinUCBSelector(
    private val alpha: Double,
    private val coefficientHolder: RegressionCoefficientHolder
) : SlotSelector{

    /*
    * section 개수 (5)개 *
    *   slot 개수 (12) *
    *   [2, 2] 행렬 곱 3번
    * */

    /**
     * for each arm
     *  context : column matrix sized of contextSize [집 여부(0, 1), 대답 점수(0, 1)]
     *  A : matrix sized of context size * context size
     *  b : column matrix of contextSize
     */
    override fun select(contextArray:Array<DoubleArray>, index: Int): Int {
        val coefficient : Coefficient = coefficientHolder.getCoefficient(index)
        val A = coefficient.A
        val b = coefficient.b
        val context = contextArray.map { info ->
            MatrixUtils.createColumnRealMatrix(info)
        }

        var maxValue : Double = Double.MIN_VALUE
        var maxSlot : Int = 0

        for (arm in 0 until context.size) {
            val invA = MatrixUtils.inverse(A[arm]) // [c, c]
            val theta = invA.multiply(b[arm]) // [c, c] * [c, 1] -> [c, 1]

            // [1, c] * [c, 1] -> [c] @ [c]
            val transposedTheta : RealVector = theta.getColumnVector(0)
            val transposedContext : RealVector = context[arm].getColumnVector(0)

            val expectReward = transposedTheta.dotProduct(transposedContext)

            val postfix = invA.multiply(context[arm]).getColumnVector(0) // [c, c] * [c, 1] -> [c, 1] -> [c]
            val confidenceInterval = alpha * sqrt(transposedContext.dotProduct(postfix))
            val ucbValue = expectReward + confidenceInterval

            if (maxValue < ucbValue) {
                maxSlot = arm
                maxValue = ucbValue
            }
        }
        return maxSlot
    }

    override fun update(reward:Double, arm:Int, context: Array<DoubleArray>, sectionIndex: Int) {
        val coefficient = coefficientHolder.getCoefficient(sectionIndex)
        val x = MatrixUtils.createColumnRealMatrix(context[arm]) // [c, 1]
        val A = coefficient.A[sectionIndex]
        val b = coefficient.b[sectionIndex]

        val updateA = x.multiply(x.transpose()) // [c, 1] * [1, c] => [c, c]
        val updateB = x.scalarMultiply(reward) // [c, 1] * reward => [c, 1]
        coefficientHolder.updateCoefficient(sectionIndex, arm, A.add(updateA), b.add(updateB))
    }
}