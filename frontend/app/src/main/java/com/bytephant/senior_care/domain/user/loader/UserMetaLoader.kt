package com.bytephant.senior_care.domain.user.loader

import com.bytephant.senior_care.ui.screen.history.DailyHistory

interface UserMetaLoader {
    suspend fun loadHistory() : List<DailyHistory>
}