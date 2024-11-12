package com.bytephant.senior_care.ui.screen.history

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier : Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()


}