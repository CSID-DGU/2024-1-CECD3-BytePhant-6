package com.bytephant.senior_care.service.network.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import java.time.LocalDateTime

@Serializable
data class InitMessageRes(
    val message : String
)

@Serializable
data class InitMessageReq(
    val question : String
)

@Serializable
data class ReplyReq(
    val message : String,
    val datetime: LocalDateTime
)

@Serializable
data class ReplyRes(
    val message: String,
    val score: Int
)

interface MessageAPI {
    @POST("conversation-first")
    suspend fun getInitMessage(@Body req: InitMessageReq) : InitMessageRes
    @POST("conversation-second")
    suspend fun getReply(@Body req: ReplyReq) : ReplyRes
}