package com.bytephant.senior_care.service.network.api

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.POST

@Serializable
data class InitMessageRes(
    val status: String,
    val message: String,
    val question_id: Int?,
    val interest_id: Int?
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

@Serializable
data class QuestionConfirmReq(
    val question_id: String,
    val user_id: String
)

@Serializable
data class InterestConfirmReq(
    val interest_id: String
)

interface MessageAPI {
    @POST("conversation/first")
    suspend fun getInitMessage(@Body req: InitMessageReq) : InitMessageRes
    @POST("conversation/second")
    suspend fun getReply(@Body req: ReplyReq) : ReplyRes
    @POST("mark/question")
    suspend fun confirmQuestion(@Body req: QuestionConfirmReq)
    @POST("mark/interest")
    suspend fun confirmInterest(@Body req: InterestConfirmReq)
}