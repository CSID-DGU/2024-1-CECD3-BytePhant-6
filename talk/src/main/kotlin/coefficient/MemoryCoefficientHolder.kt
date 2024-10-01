package coefficient

import Coefficient
import org.apache.commons.math3.linear.MatrixUtils
import org.apache.commons.math3.linear.RealMatrix

class MemoryCoefficientHolder(
    private val sectionNum : Int,
    private val armNum : Int,
    private val contextSize : Int,
) : RegressionCoefficientHolder {
    private val A = Array(sectionNum) {
        Array(armNum) {
            MatrixUtils.createRealIdentityMatrix(contextSize)
        }
    }
    private val b = Array(sectionNum) {
        Array(armNum) {
            MatrixUtils.createColumnRealMatrix(DoubleArray(contextSize) { 0.0 } )
        }
    }

    override fun getCoefficient(sectionIndex: Int): Coefficient {
        return Coefficient(
            A = A[sectionIndex],
            b = b[sectionIndex],
        )
    }

    override fun updateCoefficient(sectionIndex: Int, arm: Int, A: RealMatrix, b: RealMatrix) {
        this.A[sectionIndex][arm] = A
        this.b[sectionIndex][arm] = b
    }
}