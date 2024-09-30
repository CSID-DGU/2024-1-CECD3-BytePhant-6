package com.bytephant.senior_care.service.database

import androidx.room.TypeConverter
import com.bytephant.senior_care.domain.data.UserLocationStatus
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset


class Converters {
    private val KST_ZONE = ZoneId.of("Asia/Seoul")
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { milliSecond ->
            Instant.ofEpochMilli(milliSecond).let {
                LocalDateTime.ofInstant(it, KST_ZONE)
            }
        }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toUserLocationStatus(value: Int?): UserLocationStatus? {
        return value?.let { UserLocationStatus.entries[it] }
    }

    @TypeConverter
    fun fromUserLocationStatusToOrdinal(value: UserLocationStatus?): Int? {
        return value?.ordinal
    }
}