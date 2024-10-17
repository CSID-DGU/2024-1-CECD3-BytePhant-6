import coefficient.MemoryCoefficientHolder
import coefficient.RegressionCoefficientHolder
import context.*
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import selector.LinUCBSelector
import selector.SlotSelector
import status.*
import java.util.*
import javax.swing.JFrame

data class DayRes(
    val days: Int,
    val sectionResults: List<SectionRes>,
)

data class SectionRes(
    val slot: Int,
    val expect : Double,
    val actual : Double
);

fun simulate(
    days: Int,
    sectionNum : Int = 5,
    slotNum : Int = 12,
    contextSize: Int = 2,
    alphaEWMA: Double = 0.5,
    ucbAlpha: Double = 0.5
) : List<DayRes>{
    val homeProvider : HomeProvider = FixedDistributionHomeProvider()
    val replyScoreProvider : ReplyScoreProvider = FixedDistributionReplyScoreProvider()

    val contextHolder : ContextRepository = MemoryContextRepository()
    val contextProvider : ContextProvider = EWMAContextProvider(alphaEWMA, contextHolder)
    val coefficientHolder : RegressionCoefficientHolder = MemoryCoefficientHolder(sectionNum, slotNum, contextSize)
    val slotSelector : SlotSelector = LinUCBSelector(ucbAlpha, coefficientHolder)

    /*
    * day of days:
    *   section of day:
    *       {expect, actual}
    * */
    val simulateResult = mutableListOf<DayRes>()
    for (day in 0 until days) {
        val dayResult = mutableListOf<SectionRes>()
        var hit = 0;
        for (section in 0 until sectionNum) {
            val context = contextProvider.provide(section)
            val selectedSlot = slotSelector.select(context, section)
            for (slot in 0 until slotNum) {
                val home = homeProvider.provide(section, slot)
                var reply = 0.0;
                if (slot == selectedSlot && home) {
                    reply = replyScoreProvider.provide(section, slot)
                    dayResult.add(SectionRes(slot, 1.0, reply))
                    if (reply >= 0.5) {
                        hit += 1;
                    }
                    slotSelector.update(reply, slot, context, section)
                }
                contextHolder.appendRecord(section, slot, ContextData(home, reply, Date()))
            }
        }
        simulateResult.add(DayRes(day, dayResult))
    }
    return simulateResult
}

fun main() {
    val iterday = 30
    val bucketSize = 2
    val result = simulate(iterday, alphaEWMA = 0.2, ucbAlpha = 1.0);

    val xValues = (0 until iterday step bucketSize).toList()
    val yValues = (0 until iterday step bucketSize)
        .map { index ->
            val sum = (index until index + bucketSize)
                .map { day ->
                    val dayRes = result[day]
                    val result = dayRes.sectionResults
                        .map { if (it.expect == 1.0 && it.actual == 1.0) 1 else 0 }
                        .sum()
                    dayRes.sectionResults
                        .map { if (it.expect == 1.0 && it.actual == 1.0) 1 else 0 }
                        .sum()
                }
                .sum()
            sum.toDouble() / (bucketSize * 5)
        }
    val categoryDataset = DefaultCategoryDataset()
    for (i in 0 until yValues.size) {
        categoryDataset.addValue(yValues[i], "value",i * bucketSize );
    }
//    (0 until yValues.size).forEach {index ->
//        categoryDataset.addValue(index * bucketSize, "value", yValues[index])
//    }

    val barChart = ChartFactory.createBarChart(
        "X vs Y Plot", // 차트 제목
        "X", // X축 레이블
        "Y", // Y축 레이블
        categoryDataset,
        PlotOrientation.VERTICAL,
        false,
        true,
        false
    )

//    // XYSeriesCollection에 시리즈 추가
//    val dataset = XYSeriesCollection()
//    dataset.addSeries(series)
//    // XYSeries를 생성하여 데이터 추가
//    val series = XYSeries("x vs y")
//    for (i in xValues.indices) {
//        series.add(xValues[i].toDouble(), yValues[i])
//    }
//    // 차트 생성
//    val chart: JFreeChart = ChartFactory.createScatterPlot(
//        "X vs Y Plot", // 차트 제목
//        "X", // X축 레이블
//        "Y", // Y축 레이블
//        dataset, // 데이터셋
//        PlotOrientation.VERTICAL,
//        true, // 범례 표시
//        true,
//        false
//    )

    // 차트를 패널에 추가하여 JFrame에 표시
    val chartPanel = ChartPanel(barChart)
    chartPanel.preferredSize = java.awt.Dimension(1200, 800)

    val frame = JFrame("시뮬레이션 결과")
    frame.contentPane = chartPanel
    frame.pack()
    frame.isVisible = true
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
}