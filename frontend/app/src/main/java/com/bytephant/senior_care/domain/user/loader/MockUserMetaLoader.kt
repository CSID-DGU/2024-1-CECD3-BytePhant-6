package com.bytephant.senior_care.domain.user.loader

import com.bytephant.senior_care.domain.data.FavoriteKeyword
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

    override suspend fun loadKeyword(): List<FavoriteKeyword> {
        return listOf(
            FavoriteKeyword("바둑", 10),
            FavoriteKeyword("트로트", 3),
        )
    }
}