package com.bytephant.senior_care.service.network.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class InitMessageRes(
    val status: String,
    val message: String,
    val question: Int,
)

@Serializable
data class InitMessageReq(
    val user_id : String
)

@Serializable
data class ReplyReq(
    val user_id : String,
    val answer : String
)

@Serializable
data class ReplyRes(
    val status: String,
    val message: String,
    val score: Int
)

interface MessageAPI {
    @POST("conversation/first")
    suspend fun getInitMessage(@Body req: InitMessageReq) : InitMessageRes
    @POST("conversation/second")
    suspend fun getReply(@Body req: ReplyReq) : ReplyRes
}