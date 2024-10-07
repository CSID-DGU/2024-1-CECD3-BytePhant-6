package com.bytephant.senior_care.service.database

import androidx.room.TypeConverter
import com.bytephant.senior_care.domain.data.UserLocationStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    @TypeConverter
    fun fromDoubleMatrixArray(doubleArray: Array<Array<Double>>?): String? {
        return if (doubleArray == null) null else Gson().toJson(doubleArray)
    }

    @TypeConverter
    fun toDoubleMatrixArray(doubleArrayString: String?): Array<Array<Double>>? {
        if (doubleArrayString == null) return null
        val type = object : TypeToken<Array<Array<Double>>>() {}.type
        return Gson().fromJson(doubleArrayString, type)
    }

    @TypeConverter
    fun fromDoubleArray(doubleArray: Array<Double>?): String? {
        return if (doubleArray == null) null else Gson().toJson(doubleArray)
    }

    @TypeConverter
    fun toDoubleArray(doubleArrayString: String?): Array<Double>? {
        if (doubleArrayString == null) return null
        val listType = object : TypeToken<Array<Double>>() {}.type
        return Gson().fromJson(doubleArrayString, listType)
    }
}