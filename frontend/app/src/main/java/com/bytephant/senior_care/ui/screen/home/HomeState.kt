package com.bytephant.senior_care.ui.screen.home

import com.bytephant.senior_care.domain.data.AgentStatus
import com.bytephant.senior_care.domain.data.UserLocationStatus

data class HomeState(
    val userLocation : UserLocationStatus = UserLocationStatus.OUT,
    val agentStatus: AgentStatus = AgentStatus.WAITING
)
