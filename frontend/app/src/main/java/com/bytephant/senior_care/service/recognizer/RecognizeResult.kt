package com.bytephant.senior_care.service.recognizer

enum class RecognizeStatus {
    FAILURE,
    SUCCESS,
}

data class RecognizeResult(
    val status: RecognizeStatus,
    val answer: String? = null
)