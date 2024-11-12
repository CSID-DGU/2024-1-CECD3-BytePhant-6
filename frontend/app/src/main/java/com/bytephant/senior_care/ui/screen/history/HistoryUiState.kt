package com.bytephant.senior_care.ui.screen.history

import java.time.LocalDate

data class DailyHistory(
    val date: LocalDate,
    val logs: List<String>
)

data class HistoryUiState(
    val history : List<DailyHistory> = listOf()
)
