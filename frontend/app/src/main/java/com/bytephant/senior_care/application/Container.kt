package com.bytephant.senior_care.application

import android.content.Context
import com.bytephant.senior_care.domain.replier.NetworkReplier
import com.bytephant.senior_care.domain.replier.Replier
import com.bytephant.senior_care.service.network.RetrofitConfig
import com.bytephant.senior_care.service.network.api.MessageAPI

class Container(
    private val context : Context
) {
    private val messageAPI : MessageAPI by lazy {
        RetrofitConfig.retrofit.create(MessageAPI::class.java)
    }
    val replier : Replier by lazy {
        NetworkReplier(messageAPI)
    }

}