package com.bytephant.senior_care.domain.user.loader

import com.bytephant.senior_care.service.network.api.MessageAPI
import com.bytephant.senior_care.ui.screen.history.DailyHistory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class NetworkMetaLoader(
    private val messageAPI : MessageAPI
) : UserMetaLoader {
    val formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd")

    override suspend fun loadHistory(): List<DailyHistory> {
        val memory = messageAPI.getDemo("abcdef").memory
        return memory.map { it->
            val localDate = LocalDate.parse(it.date, formatter)
            DailyHistory(localDate, it.diary)
        }
    }
}