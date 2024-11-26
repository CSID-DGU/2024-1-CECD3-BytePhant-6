package com.bytephant.senior_care.domain.user.loader

import com.bytephant.senior_care.ui.screen.history.DailyHistory
import java.time.LocalDate

class MockUserMetaLoader : UserMetaLoader {
    override suspend fun loadHistory(): List<DailyHistory> {
        return listOf(
            DailyHistory(
                LocalDate.now(),
                listOf(
                    "abcabcabcabcabcabcabcabcabc",
                    "abcabcabcabcabcabcabcabcabc",
                )
            ),
            DailyHistory(
                LocalDate.now(),
                listOf(
                    "abcabcabcabcabcabcabcabcabc",
                    "abcabcabcabcabcabcabcabcabc",
                )
            )
        )
    }
}