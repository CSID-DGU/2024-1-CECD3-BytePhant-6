package coefficient

import Coefficient
import org.apache.commons.math3.linear.RealMatrix

interface RegressionCoefficientHolder {
    /**
     * 주어진 매개변수번째의 section에 있는
     * 학습 파라미터인 [arm, context size] 차원의 2차원 행렬을 가져옴
     */
    fun getCoefficient(sectionIndex: Int) : Coefficient;
    /**
     * 주어진 마라미터로 현재 학습 파라미터 행렬을 업데이트
     */
    fun updateCoefficient(sectionIndex: Int, arm: Int, A: RealMatrix, b: RealMatrix);
}